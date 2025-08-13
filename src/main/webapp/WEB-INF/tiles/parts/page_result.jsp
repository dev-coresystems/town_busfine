<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/parts/taglibs.jsp"%>
<div class="page-result">
    총 ${pagination.totalCount}건
    <input id="directPageNo" type="text" maxlength="6" class="default-s-item" value="${pagination.pageNo}"
        oninput="this.value = conversion.conNumber(this.value);if (Number(this.value) > ${pagination.finalPageNo}) {this.value = ${pagination.finalPageNo};}">
    / ${pagination.finalPageNo}쪽 <button id="btnDirectPage" type="button" class="default-s-btn bg-1">GO</button>
    <c:set var="pageSizes" value="${fn:split('5,10,20,30,40,50,100', ',')}" />
    <select id="selectPageSize" class="default-ms-item">
        <c:forEach var="size" items="${pageSizes}">
            <option value="${size}" <c:if test="${pagination.pageSize == size}">selected</c:if>>${size}</option>
      </c:forEach>
    </select> 건
</div>