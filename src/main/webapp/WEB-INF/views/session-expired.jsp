<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/parts/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/reset.css" />
<title>용인버스파인</title>
</head>
<body>
    <input type="hidden" id="type" value="${param.type}" />
    <div class="error-wrap">
        <div class="error-message-box">
            <h1>로그아웃된 계정입니다.</h1>
            <p id="reason">세션이 만료되었습니다.<br>계속하려면 로그인 페이지로 이동해 주세요.</p>
            <div class="error-btn-wrap">
                <a href="/login" class="btn-error-home">로그인 페이지로 이동</a>
            </div>
        </div>
    </div>
</body>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const type = document.getElementById('type').value;
        if(type == 'on') {
            document.getElementById('reason').innerHTML = '<strong>중복로그인</strong>으로 인해 로그아웃 되었습니다.<br>계속하려면 로그인 페이지로 이동해 주세요.';
        }
    });
</script>
</html>