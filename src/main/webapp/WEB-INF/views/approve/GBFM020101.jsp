<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tiles/parts/taglibs.jsp"%>
<div class="container">
	<div class="contents-header">
		<p class="breadcrumb">
			기초정보  <i class="bi bi-chevron-double-right"></i> 운수회사 <i class="bi bi-chevron-double-right"></i> 운수회사정보
		</p>
		<div class="default-button-layout">
		    <button id="btnInit" class="default-btn bg-0" type="button">초기화</button>
		    <button id="btnSample" class="default-btn bg-1" type="button">엑셀 양식</button>
		    <button id="btnUpload" class="default-btn bg-2" type="button">엑셀 업로드</button>
		    <input id="uploadExcel" class="hide" type="file" accept=".xlsx, .xls">
		    <button id="btnSearch" class="default-btn bg-3" type="button">조회</button>
		    <button id="btnExcelReason" class="default-btn bg-4" type="button">엑셀다운</button>
		</div>
	</div>
	<div class="contents-search">
	    <form id="frmSearch" method="post" action="/approve/company/GBFM020101.gbf">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" name="pageNo" id="pageNo" value="${paramVo.pageNo}" />
            <input type="hidden" name="pageSize" id="pageSize" value="${paramVo.pageSize}" />
            <input type="hidden" name="orderColumn" id="orderColumn" value="${paramVo.orderColumn}" />
            <input type="hidden" name="orderDir" id="orderDir" value="${paramVo.orderDir}" />
            <div class="search-item">
                <label for="compId">업체명</label>
                <div class="input-wrapper">
                    <input id="compId" type="text" maxlength="20">
                    <i class="bi bi-search"></i>
                </div>
            </div>
            <div class="search-item">
                <label for="routeId">노선명</label>
                <div class="input-wrapper">
                    <input id="routeId" type="text" maxlength="20">
                    <i class="bi bi-search"></i>
                </div>
            </div>
            <div class="search-item">
                <label for="startDt">시작일</label>
                <div class="input-wrapper">
                    <input id="startDt" type="text" maxlength="20">
                    <i class="bi bi-search"></i>
                </div>
            </div>
            <div class="search-item">
                <label for="endDt">종료일</label>
                <div class="input-wrapper">
                    <input id="endDt" type="text" maxlength="20">
                    <i class="bi bi-search"></i>
                </div>
            </div>
	    </form>
	</div>
    <div class="contents-sub">
        <c:import url="/WEB-INF/tiles/parts/page_result.jsp" />
        <div class="action-btn-layout">
            <button id="btnRowAdd" class="default-m-btn bg-1">행추가</button>
            <button id="btnRowSave" class="default-m-btn bg-0">행저장</button>
            <button id="btnRowDel" class="default-m-btn bg-2">행삭제</button>
            <button id="btnDel" class="default-m-btn bg-5">선택 삭제</button>
            <button id="btnApprov" class="default-m-btn bg-3">선택 승인</button>
            <button id="btnReject" class="default-m-btn bg-5">선택 미승인</button>
        </div>
    </div>
    <div class="contents-main">
        <table id="dataTable" class="data-table">
            <thead>
                <tr>
                    <th><input class="all-chk-select row-select" type="checkbox"></th>
                    <th>No</th>
                    <th data-column="comp_nm">운수회사명</th>
                    <th data-column="addr">주소</th>
                    <th data-column="owner">대표자</th>
                    <th data-column="tel">전화번호</th>
                    <th data-column="corp_no">법인번호</th>
                    <th data-column="biz_no">사업자번호</th>
                    <th data-column="note">비고</th>
                    <th class="table-btn">확인</th>
                  </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty list}">
                        <tr>
                            <td colspan="9">조회된 데이터가 없습니다.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:set var="startNo" value="${(pagination.pageNo - 1) * pagination.pageSize}" />
                        <c:forEach var="item" items="${list}" varStatus="status">
                            <tr>
                                <td><input class="row-select" type="checkbox"></td>
                                <td>${startNo + status.index + 1}</td>
                                <td class="data-edit">${item.compNm}</td>
                                <td class="data-edit">${item.addr}</td>
                                <td class="data-edit">${item.owner}</td>
                                <td class="data-edit">${item.tel}</td>
                                <td class="data-edit">${item.corpNo}</td>
                                <td class="data-edit">${item.bizNo}</td>
                                <td class="data-edit">${item.note}</td>
                                <td class="btn-edit"><button id="btnRowUpdate" class="default-ss-btn bg-3" type="button">수정</button></td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
    <c:import url="/WEB-INF/tiles/parts/pagination.jsp" />
</div>