<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/parts/taglibs.jsp"%>
<div class="container">
	<div class="contents-header">
		<p class="breadcrumb">
			마이페이지  <i class="bi bi-chevron-double-right"></i> 홈
		</p>
	</div>
	<div class="content-wrap">
	    <div id='calendar'></div>
	</div>
	<div class="bottom-wrap">
	    <div class="notice"></div>
	    <div class="count"></div>
	</div>
</div>
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.18/index.global.min.js'></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
      var calendarEl = document.getElementById('calendar');

      var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth', // 기본은 월간 보기
        locale: 'ko', // 언어 한글
        height: 'auto',
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        buttonText: {
          today: '오늘',
          month: '월',
          week: '주',
          day: '일'
        },
        views: {
          dayGridMonth: {
            // 월간 뷰
          },
          timeGridWeek: {
            // 주간 뷰 (시간 포함)
          },
          timeGridDay: {
            // 일간 뷰 (시간 포함)
          }
        },
          initialView: 'dayGridMonth',
          selectable: true,
          selectMirror: true,
          editable: true, // 이벤트 수정 허용

          select: function(info) {
            const title = prompt('새 일정 제목을 입력하세요');
            if (title) {
              calendar.addEvent({
                title: title,
                start: info.startStr,
                end: info.endStr,
                allDay: info.allDay
              });
            }
            calendar.unselect();
          },

          eventClick: function(info) {
            // 이벤트 클릭하면 편집/삭제 선택지 보여주기
            const newTitle = prompt('일정 제목을 수정하세요', info.event.title);
            if (newTitle === null) return; // 취소하면 종료
            const newColor = prompt('변경할 색상코드 입력 (#rrggbb)');
            if(newColor) {
              info.event.setProp('color', newColor);
            }
            if (newTitle === '') {
              // 제목을 빈값으로 하면 삭제 처리
              if (confirm('정말 일정을 삭제하시겠습니까?')) {
                info.event.remove();
              }
            } else {
              // 제목 수정
              info.event.setProp('title', newTitle);
            }
          },

          eventDrop: function(info) {
            alert(`${info.event.title} 일정이 ${info.event.start.toISOString()} 로 변경됨`);
            // 서버에 변경사항 저장 ajax 호출 위치
          },

          eventResize: function(info) {
            alert(`${info.event.title} 일정 기간이 ${info.event.start.toISOString()} ~ ${info.event.end.toISOString()} 로 변경됨`);
            // 서버 저장 ajax 호출 위치
          },

      });

      calendar.render();
    });
</script>