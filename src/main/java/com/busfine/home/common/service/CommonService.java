package com.busfine.home.common.service;

import com.busfine.home.common.dao.CommonDAO;
import com.busfine.home.common.vo.MnuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final CommonDAO commonDAO;

    public List<MnuVo> getMnuList(String auth) {
        return commonDAO.getMnuList(auth);
    }

    public List<Map<String,Object>> getMenuList(String auth) {
        return commonDAO.getMenuList(auth);
    }
}
