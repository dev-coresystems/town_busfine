$(document).ready(function() {
    let clickTimer;

    dataTable.initSort();
    dataTable.thResize();

    $('.data-table thead').on('click', 'th', function() {
        dataTable.sort($(this));
    });

    $('.data-table tbody').on('click', 'tr', function(e) {
        if ($(e.target).is('input, button, select, textarea')) {
            return;
        }
        dataTable.click($(this), clickTimer);
    });

    $('.data-table tbody').on('dblclick', 'tr', function(e) {
        dataTable.doubleClick($(this), clickTimer);
    });

    $('.data-table tbody').on('click', ' #btnRowUpdate', function(e){
        e.preventDefault();
        dataTable.edit($(this));
    });

    $('.data-table tbody').on('click', ' #btnRowCancel', function(e){
        e.preventDefault();
        dataTable.cancel($(this));
    });

    $(document).on('click', '#btnRowAdd', function(){
        dataTable.addRow();
    });

    $(document).on('click', '#btnRowDel', function(){
        dataTable.deleteRow();
    });

    $(document).on('click', '.all-chk-select', function() {
        let isChecked = $(this).prop('checked');
        $('.row-select').prop('checked', isChecked);
    });

    $(document).on('click', '#btnSelfRowDel', function() {
        $(this).closest('tr').remove();
    });
});

const dataTable = {
    initSort() {
        const orderColumn = $('#orderColumn').val();
        const orderDir = $('#orderDir').val();

        $('.data-table thead th').removeClass('sorting_asc sorting_desc');

        if (orderColumn) {
            const th = $('.data-table thead th[data-column="' + orderColumn + '"]');
            if (th.length) {
                th.addClass(orderDir === 'asc' ? 'sorting_asc' : 'sorting_desc');
            }
        }
    },
    click(_element, _timer) {
        clearTimeout(_timer);
        const tr = _element;
        tr.toggleClass('selected');
        clickTimer = setTimeout(() => {
            //console.log('Single click:', _element);
        }, 250); // 250ms 내에 dblclick이 안 오면 single click 처리
    },
    doubleClick(_element, _timer){
        clearTimeout(_timer);
        // 더블 클릭 이벤트 처리
        //console.log('Double click:', _element);
    },
    sort(_element) {
        const column = _element.data('column');

        if (!column) {
            return false;
        }

        let currentOrderCol = $('#orderColumn').val();
        let currentOrderDir = $('#orderDir').val();

        let newOrderDir = 'asc';
        if (currentOrderCol === column && currentOrderDir === 'asc') {
            newOrderDir = 'desc';
        }

        $('#orderColumn').val(column);
        $('#orderDir').val(newOrderDir);
        $('#frmSearch').submit();
    },
    edit(_element){
        let row = _element.closest('tr');

        // 첫 번째 TD를 체크박스로 변경
        let firstCell = row.find('td').first();
        let rowId = firstCell.text().trim(); // 필요하면 번호 저장
        firstCell.data('original', rowId);   // 취소 시 복구용
        firstCell.html('<input type="checkbox" class="row-select" value="' + rowId + '">');

        // 나머지 data-edit만 input으로 변경
        let editableCells = row.find('td.data-edit');
        editableCells.each(function() {
            let cell = $(this);
            let currentText = cell.text().trim();
            cell.data('original', currentText);
            cell.html('<input type="text" value="' + currentText + '" class="form-control edit-input">');
        });

        // 버튼 변경
        _element.text('저장').attr('id', 'btnRowSave');
        _element.after('<button type="button" class="default-ss-btn bg-1" id="btnRowCancel">취소</button>');
    },
    cancel(_element) {
        let row = _element.closest('tr');

        // 첫 번째 칸 복원
        let firstCell = row.find('td').first();
        let originalNumber = firstCell.data('original');

        if (originalNumber !== undefined) {
            // 체크박스가 없을 때만 텍스트 복원
            if (firstCell.find('input.row-select').length === 0) {
                firstCell.text(originalNumber);
            }
            firstCell.removeData('original');
        }

        // 나머지 editable 셀 복원
        let editableCells = row.find('td.data-edit');
        editableCells.each(function() {
            let cell = $(this);
            let originalText = cell.data('original');
            cell.text(originalText);
            cell.removeData('original');
        });

        // 버튼 원복
        row.find('#btnRowCancel').remove();
        row.find('#btnRowSave').text('수정').attr('id', 'btnRowUpdate');
    },
    addRow() {
        let table = $('.data-table tbody');
        let firstRow = table.find('tr').first();

        let newRow = firstRow.clone();

        newRow.find('td').each(function (index) {
            if (index === 0) {
                $(this).html('<input type="checkbox" class="new-row-select">');
            } else if ($(this).hasClass('data-edit')) {
                $(this).html('<input type="text" class="form-control edit-input">');
            } else if ($(this).hasClass('btn-edit')) {
                $(this).html(`
                    <button type="button" class="default-s-btn bg-5" id="btnSelfRowDel">행삭제</button>
                `);
            } else {
                $(this).empty();
            }
        });

        if (firstRow.length > 0) {
            newRow.insertBefore(firstRow);
        } else {
            table.append(newRow);
        }
    },
    deleteRow() {
        $('.data-table tbody').find('input.new-row-select:checked').each(function () {
            $(this).closest('tr').remove();
        });

        // 삭제 후 번호 재정렬
        $('.data-table tbody tr').each(function (index) {
            let firstCell = $(this).find('td').first();
        });
    },
    thResize() {
        const table = $('#dataTable');
        const headers = table.find('thead th');
        let isResizing = false;
        let preventClick = false;  // 드래그 종료 후 다음 click 차단용

        headers.each(function(index) {
            const th = $(this);
            const resizer = $('<div class="resizer"></div>');
            th.css('position', 'relative').append(resizer);

            let startX, startWidth;

            resizer.on('mousedown', function(e) {
                e.preventDefault();
                e.stopPropagation();

                isResizing = true;
                preventClick = false;

                startX = e.pageX;
                const cells = table.find('tr').map(function() {
                    return $(this).children().eq(index)[0];
                });
                startWidth = $(cells[0]).outerWidth();

                function mousemove(e) {
                    const diffX = e.pageX - startX;
                    const newWidth = startWidth + diffX;

                    cells.each(function() {
                        $(this).css({
                            width: newWidth + 'px',
                            minWidth: newWidth + 'px',
                            maxWidth: newWidth + 'px'
                        });
                    });
                }

                function mouseup() {
                    isResizing = false;
                    preventClick = true; // 드래그 종료 후 다음 click 차단 플래그 켜기
                    $(document).off('mousemove', mousemove);
                    $(document).off('mouseup', mouseup);
                }

                $(document).on('mousemove', mousemove);
                $(document).on('mouseup', mouseup);
            });

            th.on('click', function(e) {
                if (preventClick) {
                    e.stopImmediatePropagation();
                    e.preventDefault();
                    preventClick = false;  // 한번만 차단
                    return false;
                }
            });
        });
    }
}