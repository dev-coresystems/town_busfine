<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/parts/taglibs.jsp"%>
<div class="header">
    <div class='header-top'>
        <strong><sec:authentication property="principal.userNm" /></strong>반갑습니다.
        <div class="select-org-contents">
            <select>
                <option>경기도 수원시</option>
                <option>경기도 용인시</option>
                <option>경기도 안양시</option>
            </select>
        </div>
        <div class="session-timer-contents">
            <div class="session-timer">60:00</div>
            <div><button id="btnAddSession" type="button">연장</button></div>
        </div>
        <a id="btnLogout">로그아웃</a>
        <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </div>
    <section class="top-mnu-wrap">
        <a href="/mypage/home/GBFM010100.gbf">
            <div class="top-mnu-logo">
                <img src="/resources/images/title_image.png">
                <div class="top-mnu-title">마을버스<br>버스파인</div>
            </div>
        </a>
        <div class="nav">
            <ul>
                <c:forEach var="depth1" items="${mnuList}">
                    <c:if test="${depth1.depth == 1}">
                        <!-- depth2 존재 여부 체크 -->
                        <c:set var="isDepth2" value="false" />
                        <c:forEach var="depth2Check" items="${mnuList}">
                            <c:if test="${depth2Check.depth == 2 && depth2Check.PPgmId == depth1.pgmId}">
                                <c:set var="isDepth2" value="true" />
                            </c:if>
                        </c:forEach>

                        <li>
                            <!-- depth2 존재시 링크 제거 -->
                            <c:choose>
                                <c:when test="${isDepth2}">
                                    <a href="javascript:void(0);" data-depth1="${depth1.pgmId}">${depth1.pgmNm}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${depth1.link}"  data-depth1="${depth1.pgmId}">${depth1.pgmNm}</a>
                                </c:otherwise>
                            </c:choose>

                            <!-- depth2 존재시 depth2 목록 표현 -->
                            <c:if test="${isDepth2}">
                                <div class="depth2">
                                    <div class="depth2-layout">
                                        <div class="menu-list">
                                            <ul>
                                                <c:forEach var="depth2" items="${mnuList}">
                                                    <c:if test="${depth2.depth == 2 && depth2.PPgmId == depth1.pgmId}">
                                                        <!-- depth3 존재 여부 체크 -->
                                                        <c:set var="isDepth3" value="false" />
                                                        <c:forEach var="depth3" items="${mnuList}">
                                                            <c:if test="${depth3.depth == 3 && depth3.PPgmId == depth2.pgmId}">
                                                                <c:set var="isDepth3" value="true" />
                                                            </c:if>
                                                        </c:forEach>

                                                        <c:choose>
                                                            <c:when test="${isDepth3}">
                                                                <li>
                                                                    <!-- depth3 있으면 depth2 링크 제거 -->
                                                                    <a href="javascript:void(0);" data-depth1="${depth1.pgmId}">${depth2.pgmNm}</a>
                                                                    <ul>
                                                                        <c:forEach var="depth3" items="${mnuList}">
                                                                            <c:if test="${depth3.depth == 3 && depth3.PPgmId == depth2.pgmId}">
                                                                                <li><a href="${depth3.link}" data-depth1="${depth1.pgmId}">${depth3.pgmNm}</a></li>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </li>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <li><a href="${depth2.link}" data-depth1="${depth1.pgmId}">${depth2.pgmNm}</a></li>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
    </section>
</div>