package com.busfine.home.common.service;

import com.busfine.home.common.dao.LoginDAO;
import com.busfine.home.common.vo.GbfUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginDAO loginDAO;

    public GbfUserDetails getUserInfo(String userId) {
        return loginDAO.getUserInfo(userId);
    }

    public int findFailCount(String userId) {
        return loginDAO.findFailCount(userId);
    }

    public void updateLoginFailCountToZero(String userId) {
        loginDAO.updateLoginFailCountToZero(userId);
    }

    public void updateLoginFailCount(String userId) {
        loginDAO.updateLoginFailCount(userId);
    }
}
