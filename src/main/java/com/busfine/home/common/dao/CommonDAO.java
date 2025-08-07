package com.busfine.home.common.dao;

import com.busfine.home.common.vo.MnuVo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CommonDAO {
    private final SqlSession sql;

    public List<MnuVo> getMnuList(String auth) {
        return sql.selectList("commonMapper.getMnuList", auth);
    }

    public List<Map<String,Object>> getMenuList(String auth) {
        return sql.selectList("commonMapper.getMenuList", auth);
    }
}
