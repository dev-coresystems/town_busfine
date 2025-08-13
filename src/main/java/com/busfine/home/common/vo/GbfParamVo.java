package com.busfine.home.common.vo;

import lombok.Data;

@Data
public class GbfParamVo {
    private String scOrgCd;
    private String scCompId;
    private String scCompNm;
    private String scRouteId;
    private String scRouteNm;
    private String scYear;
    private String scStartDt;
    private String scEndDt;
    private String wrkType;
    private boolean isExcel = false;

    private int pageNo;
    private int pageSize;
    private int offset;
    private int limit;
    private String orderColumn;
    private String orderDir;
}
