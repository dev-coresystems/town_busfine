package com.busfine.home.approve.service;

import com.busfine.home.approve.dao.GBFM020101Dao;
import com.busfine.home.approve.param.GBFM020101ParamVo;
import com.busfine.home.approve.vo.GBFM020101Vo;
import com.busfine.home.common.vo.GbfParamVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GBFM020101Service {
    private final GBFM020101Dao dao;

    public List<GBFM020101Vo> selectList(GBFM020101ParamVo paramVo) {

        List<GBFM020101Vo> list = dao.selectList(paramVo);

        return list;
    }

}
