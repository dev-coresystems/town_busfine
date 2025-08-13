document.addEventListener("DOMContentLoaded", function () {
    document.addEventListener("click", function (e) {
        if(e.target.closest("#btnLogout")) {
            buttonEvent.logout();
        } else if(e.target.closest("#btnPrev")) {
            buttonEvent.prev();
        } else if(e.target.closest("#btnInit")) {
            buttonEvent.initSearch();
        } else if(e.target.closest("#btnDirectPage")) {
            buttonEvent.search();
        } else if(e.target.closest("#btnSearch")) {
            buttonEvent.search();
        } else if (e.target.closest('#btnUpload')) {
            document.getElementById('uploadExcel').click();
        }
    });

    document.addEventListener("input", function(e) {
        if(e.target.closest("#directPageNo")) {
            document.getElementById("pageNo").value = document.getElementById("directPageNo").value;
        }
    });

    document.addEventListener("change", function(e) {
        if(e.target.closest("#selectPageSize")) {
            document.getElementById("pageNo").value = 1;
            document.getElementById("pageSize").value = document.getElementById("selectPageSize").value;
        } else if(e.target.closest('#uploadExcel')) {
            buttonEvent.uploadExcel(e);
        }
    });
});

const buttonEvent = {
    logout() {
        const csrfInput = document.getElementById("csrf");
        const csrfParam = csrfInput.name;
        const csrfToken = csrfInput.value;

        const form = document.createElement("form");
        form.method = "POST";
        form.action = "/logout";

        const csrfField = document.createElement("input");
        csrfField.type = "hidden";
        csrfField.name = csrfParam;
        csrfField.value = csrfToken;

        form.appendChild(csrfField);

        document.body.appendChild(form);
        form.submit();
    },
    prev() {
        window.history.back();
    },
    initSearch(){
        document.getElementById('frmSearch').reset();
    },
    search() {
        const form = document.getElementById('frmSearch');
        form.submit();
    },
    selectPageNo(pageNo){ //page번호 click 시 호출
        document.getElementById('pageNo').value = pageNo;
        buttonEvent.search();
    },
    uploadExcel(e) {
        //sheet.js 라이브러리 필요
        let input = e.target;
        let reader = new FileReader();

        const allowed = ['.xlsx', '.xls'];
        const ext = input.files[0].name.substring(input.files[0].name.lastIndexOf('.')).toLowerCase();

        if (!allowed.includes(ext)) {
            alert('엑셀 파일만 업로드 가능합니다.');
            e.target.value = ''; // 선택 초기화
            return;
        }

        reader.readAsBinaryString(input.files[0]);

        reader.onload = () => {
            let data = reader.result;
            let workBook = XLSX.read(data, { type: 'binary' });

            // 첫 번째 시트만 보여줌 (여러 시트 필요하면 forEach)
            let firstSheetName = workBook.SheetNames[0];
            let sheet = workBook.Sheets[firstSheetName];

            // 범위보정
            let minRow = Infinity, maxRow = -1;
            let minCol = Infinity, maxCol = -1;

            Object.keys(sheet).forEach(cellAddr => {
                if (cellAddr[0] === '!') return;
                const {r, c} = XLSX.utils.decode_cell(cellAddr);
                if (r < minRow) minRow = r;
                if (r > maxRow) maxRow = r;
                if (c < minCol) minCol = c;
                if (c > maxCol) maxCol = c;
            });

            if (minRow !== Infinity) {
                const newRange = XLSX.utils.encode_range(
                    {r: minRow, c: minCol},
                    {r: maxRow, c: maxCol}
                );
                sheet['!ref'] = newRange;
            } else {
                sheet['!ref'] = "A1:A1";
            }

            // HTML 테이블 변환
            let htmlTable = XLSX.utils.sheet_to_html(sheet);

            // 팝업창 열기
            let popup = window.open("", "ExcelPreview", "width=1000,height=700,scrollbars=yes");

            // 팝업에 HTML 삽입
            popup.document.write(`
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>마을버스파인/title>
                    <style>
                        table { border-collapse: collapse; }
                        td, th { border: 1px solid #999; padding: 4px; }
                    </style>
                </head>
                <body>
                    <div class="excel-upload-header">
                        <div>
                            <span class="excel-upload-title">차량별 현금수입내역 엑셀업로드</span>
                            <button class="excel-upload-close">업로드</button>
                        </div>
                    </div>
                    <div class="excel-upload-body">
                        <div>
                            <button id="btnExcelUpload" type="button">엑셀업로드</button>
                        </div>
                        <div>
                            ${htmlTable}
                        </div>
                    </div>
                </body>
                </html>
            `);
            popup.document.close();
        };
    }
}