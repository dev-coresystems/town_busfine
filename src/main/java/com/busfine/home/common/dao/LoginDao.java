package com.busfine.home.common.dao;

import com.busfine.home.common.vo.GbfUserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LoginDao {
    private final SqlSession sql;

    public GbfUserDetails getUserInfo(String userId) {
        return sql.selectOne("LoginMapper.getUserInfo", userId);
    }

    public int findFailCount(String userId) {
        return sql.selectOne("LoginMapper.findFailCount", userId);
    }

    public void updateLoginFailCountToZero(String userId) {
        sql.update("LoginMapper.updateLoginFailCountToZero", userId);
    }

    public void updateLoginFailCount(String userId) {
        sql.update("LoginMapper.updateLoginFailCount", userId);
    }
}