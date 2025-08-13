package com.busfine.home.approve.vo;

import com.busfine.home.common.vo.GbfVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GBFM020101Vo extends GbfVo {
    private String compId;
    private String compNm;
    private String addr;
    private String owner;
    private String tel;
    private String corpNo;
    private String bizNo;
    private String note;
}
