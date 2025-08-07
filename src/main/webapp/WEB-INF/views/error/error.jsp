<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/parts/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>에러 페이지</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
    <link rel="stylesheet" type="text/css" href="/resources/css/reset.css" />
    <script src="/resources/js/common.js"></script>
</head>
<body>
<div class="error-wrap">
    <div class="error-message-box">
        <c:choose>
            <c:when test="${code == 400}">
                <h1>잘못된 요청<h1>
                <p>잘못된 요청입니다.<br>올바른 방식으로 접근해 주시기 바랍니다.</p>
            </c:when>
            <c:when test="${code == 404}">
                <h1>페이지 찾을 수 없음</h1>
                <p>
                    페이지 경로가 올바르지 않습니다. <br>
                    주소가 잘못 입력되었거나 변경 또는 삭제되어 <br>
                    요청하신 페이지를 찾을 수 없습니다. <br>
                    입력하신 주소를 다시 한번 확인해 주시기 바랍니다.
                </p>
            </c:when>
            <c:when test="${code == 403}">
                <h1>권한 없음</h1>
                <p>접근 권한이 없습니다.</p>
            </c:when>
            <c:otherwise>
                <h1>에러</h1>
                <p>
                    해당 서비스를 연결할 수 없습니다.<br>
                    문제가 지속적으로 발생하는 경우,<br>
                    관리자에게 문의 주시기 바랍니다.
                </p>
            </c:otherwise>
        </c:choose>
        <div class="error-btn-wrap">
            <a href="/" class="btn-error-home">홈으로</a>
            <a href="#" id="btnPrev" class="btn-error-prev">이전으로</a>
        </div>
    </div>
</div>
</body>
</html>
