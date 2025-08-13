package com.busfine.home.common.service;

import com.busfine.home.common.dao.LoginDao;
import com.busfine.home.common.vo.GbfUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginDao loginDao;

    public GbfUserDetails getUserInfo(String userId) {
        return loginDao.getUserInfo(userId);
    }

    public int findFailCount(String userId) {
        return loginDao.findFailCount(userId);
    }

    public void updateLoginFailCountToZero(String userId) {
        loginDao.updateLoginFailCountToZero(userId);
    }

    public void updateLoginFailCount(String userId) {
        loginDao.updateLoginFailCount(userId);
    }
}
