<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/parts/taglibs.jsp"%>
<div class="left-nav-layout">
    <div class="left-nav-title">${leftMnuList[0].pgmNm}</div>
    <div class="left-nav-menu">
        <ul>
            <c:forEach var="depth2" items="${leftMnuList}">
                <c:if test="${depth2.depth == 2}">
                    <!-- depth3 존재 여부 확인 -->
                    <c:set var="hasDepth3" value="false" />
                    <c:forEach var="depth3" items="${leftMnuList}">
                        <c:if test="${depth3.depth == 3 && depth3.PPgmId == depth2.pgmId}">
                            <c:set var="hasDepth3" value="true" />
                        </c:if>
                    </c:forEach>

                    <li class="left-nav-li">
                        <a href="<c:choose><c:when test='${hasDepth3}'>javascript:void(0)</c:when><c:otherwise>${depth2.link}</c:otherwise></c:choose>" data-depth2="${depth2.pgmId}">
                            <span class="menu-text">${depth2.pgmNm}</span>
                            <c:if test="${hasDepth3}">
                                <span class="depth3-icon">
                                    <i class="bi bi-caret-down-fill"></i>
                                </span>
                            </c:if>
                        </a>

                        <!-- depth3가 있으면 하위 ul 생성 -->
                        <c:if test="${hasDepth3}">
                            <ul class="left-nav-sub">
                                <c:forEach var="depth3" items="${leftMnuList}">
                                    <c:if test="${depth3.depth == 3 && depth3.PPgmId == depth2.pgmId}">
                                        <li class="depth3-item">
                                            <a href="${depth3.link}" data-depth2="${depth3.PPgmId}">${depth3.pgmNm}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>

