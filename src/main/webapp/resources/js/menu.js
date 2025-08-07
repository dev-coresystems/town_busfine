document.addEventListener("DOMContentLoaded", function () {
    //초기 선택 메뉴 활성화
    menu.activeMnu();
    //초기 선택 LEFT 메뉴 활성화
    menu.activeLeftMnu();

    //메뉴 hover시
    document.querySelectorAll(".header .top-mnu-wrap .nav > ul > li").forEach(function (item) {
        item.addEventListener("mouseover", function () {
            document.querySelectorAll(".depth2").forEach(function (subItem) {
                subItem.style.display = "none";
            });

            document.querySelectorAll(".header .top-mnu-wrap .nav > ul > li > a.on").forEach(function (aItem) {
                aItem.classList.remove("on");
            });

            const submenu = item.querySelector(".depth2");
            if (submenu) {
                submenu.style.display = "block";
                const aTag = item.querySelector("a");
                if (aTag) {
                    aTag.classList.add("on");
                }
            }

        });
    });
    //메뉴 leave시
    document.querySelector(".top-mnu-wrap").addEventListener("mouseleave", function () {
        document.querySelectorAll(".depth2").forEach(function (item) {
            item.style.display = "none";
        });

        menu.activeMnu();
    });

    //left 메뉴 클릭시
    document.querySelectorAll(".left-nav-li").forEach(function (item) {
        item.addEventListener("click", function (e) {
            if (e.target.closest('.depth3-item')) return;

            const subMenu = item.querySelector('.left-nav-sub');
            if (!subMenu) return;

            e.preventDefault();

            const isOpen = subMenu.classList.contains('open');

            // 모든 메뉴 닫기
            document.querySelectorAll('.left-nav-sub.open').forEach(function (ul) {
                ul.style.height = ul.scrollHeight + 'px'; // 먼저 실제 높이 설정 → 트리거
                requestAnimationFrame(() => {
                    ul.style.height = '0px';
                    ul.classList.remove('open');

                    const li = ul.closest('.left-nav-li');
                    if (li) {
                        li.classList.remove('on');

                        // 아이콘 닫힘 상태로 변경
                        const icon = li.querySelector('.depth3-icon i');
                        if (icon) {
                            icon.className = 'bi bi-caret-down-fill';
                        }
                    }
                });
            });

            if (!isOpen) {
                // 열기 전 초기 높이 0
                subMenu.style.height = '0px';
                subMenu.classList.add('open');
                item.classList.add('on');

                // 아이콘 열림 상태로 변경
                const icon = item.querySelector('.depth3-icon i');
                if (icon) {
                    icon.className = 'bi bi-caret-up-fill';
                }

                // 다음 프레임에서 실제 높이 설정 → transition 동작 유도
                requestAnimationFrame(() => {
                    subMenu.style.height = subMenu.scrollHeight + 'px';
                });
            }
        });
    });
});

const menu = {
    currentPath: window.location.pathname,
    currentFile: '',
    currentBase: '',
    init() {
        menu.currentFile = menu.currentPath.substring(menu.currentPath.lastIndexOf('/') + 1);
        menu.currentBase = menu.currentFile.replace(/\.gbf$/i, '').split('_')[0];
    },
    activeMnu(){
        menu.init();
        // 기존에 on 클래스 제거 (중복 방지)
        document.querySelectorAll('a[data-depth1].on').forEach(function (el) {
            el.classList.remove('on');
        });

        document.querySelectorAll('a[data-depth1]').forEach(function (item) {
            const href = item.getAttribute('href');

            if (!href || !href.endsWith('.gbf')) return;

            const hrefFile = href.substring(href.lastIndexOf('/') + 1);
            const hrefBase = hrefFile.replace(/\.gbf$/i, '').split('_')[0];

            if (hrefBase === menu.currentBase) {
                const depth1Val = item.getAttribute('data-depth1');

                // 같은 data-depth1 값을 가진 a 태그 중 첫 번째에 on 추가
                const depth1Tag = document.querySelector(`a[data-depth1="${depth1Val}"]`);
                if (depth1Tag) {
                    depth1Tag.classList.add('on');
                }

                // 한 번만 실행하고 종료
                return;
            }
        });
    },
    activeLeftMnu() {
        menu.init();
        document.querySelectorAll(".left-nav-li a[data-depth2]").forEach(function (anchor) {
            const href = anchor.getAttribute("href");
            if (!href) return;

            const hrefFile = href.substring(href.lastIndexOf('/') + 1);
            const hrefBase = hrefFile.replace(/\.gbf$/i, '').split('_')[0];

            if (hrefBase === menu.currentBase) {
                const depth2 = anchor.getAttribute("data-depth2");

                // 가장 가까운 .left-nav-li를 찾는다
                const parentLi = anchor.closest(".left-nav-li");
                if (parentLi) {
                    parentLi.classList.add("on");
                }

                // 하위 서브 메뉴가 있으면 open 클래스 추가
                const parentUl = anchor.closest(".left-nav-sub");
                if (parentUl) {
                    parentUl.classList.add("open");
                    parentUl.style.height = parentUl.scrollHeight + "px";
                }

                const icon = parentLi.querySelector('.depth3-icon i');
                if (icon) {
                    icon.className = 'bi bi-caret-up-fill'; // on 상태면 위 화살표로 변경
                }
                anchor.classList.add("selected");
            }
        });
    }
}