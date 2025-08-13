<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html lang="ko">
	<head>
	    <meta charset="UTF-8" />
		<title>마을버스파인</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
        <link rel="stylesheet" type="text/css" href="/resources/css/reset.css" />
        <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap/icon.css" />
        <script src="/resources/js/jquery/jquery-3.5.1.min.js"></script>
        <script src="/resources/plugin/excel/xlsx.full.min-0.20.3.js"></script>
        <script src="/resources/js/table.js"></script>
        <script src="/resources/js/menu.js"></script>
        <script src="/resources/js/common.js"></script>
        <script src="/resources/js/util.js"></script>
	</head>
    <body>
        <tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="lnb" />
        <tiles:insertAttribute name="body" />
    </body>
</html>