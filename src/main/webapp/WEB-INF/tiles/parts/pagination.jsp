<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/parts/taglibs.jsp"%>
<div class="pagination">
    <!-- 첫 페이지, 이전 페이지 버튼 -->
    <button type="button"
            onclick="buttonEvent.selectPageNo(${pagination.firstPageNo})"
            <c:if test="${pagination.pageNo <= pagination.firstPageNo}">disabled</c:if>>
        <i class="bi bi-chevron-double-left"></i>
    </button>
    <button type="button"
            onclick="buttonEvent.selectPageNo(${pagination.prevPageNo})"
            <c:if test="${pagination.pageNo <= pagination.firstPageNo}">disabled</c:if>>
        <i class="bi bi-chevron-left"></i>
    </button>

    <!-- 페이지 번호 -->
    <c:forEach var="page" begin="${pagination.startPageNo}" end="${pagination.endPageNo}">
        <c:choose>
            <c:when test="${page == pagination.pageNo}">
                <button type="button" class="current" disabled>${page}</button>
            </c:when>
            <c:otherwise>
                <button type="button" onclick="buttonEvent.selectPageNo(${page})">${page}</button>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <!-- 다음 페이지, 마지막 페이지 버튼 -->
    <button type="button"
            onclick="buttonEvent.selectPageNo(${pagination.nextPageNo})"
            <c:if test="${pagination.pageNo >= pagination.finalPageNo}">disabled</c:if>>
        <i class="bi bi-chevron-right"></i>
    </button>
    <button type="button"
            onclick="buttonEvent.selectPageNo(${pagination.finalPageNo})"
            <c:if test="${pagination.pageNo >= pagination.finalPageNo}">disabled</c:if>>
        <i class="bi bi-chevron-double-right"></i>
    </button>
</div>