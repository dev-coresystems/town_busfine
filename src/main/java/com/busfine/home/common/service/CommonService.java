package com.busfine.home.common.service;

import com.busfine.home.common.dao.CommonDao;
import com.busfine.home.common.vo.MnuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final CommonDao commonDao;

    public List<MnuVo> getMnuList(String auth) {
        return commonDao.getMnuList(auth);
    }

    public List<Map<String,Object>> getMenuList(String auth) {
        return commonDao.getMenuList(auth);
    }
}
