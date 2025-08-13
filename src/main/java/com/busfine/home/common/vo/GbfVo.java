package com.busfine.home.common.vo;

import lombok.Data;

@Data
public class GbfVo {
    private String orgCd;
    private String orgNm;
    private String compId;
    private String compNm;
    private String routeId;
    private String routeNm;

    private Integer totalCnt;
}
