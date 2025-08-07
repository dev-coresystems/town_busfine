package com.busfine.home.common.handler;

import com.busfine.home.common.vo.GbfUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler  extends SimpleUrlAuthenticationSuccessHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("*********************** onAuthenticationSuccess ***********************");
        GbfUserDetails userInfo = (GbfUserDetails) ((Authentication) authentication).getPrincipal();

        Map<String, Object> responseMap = new HashMap<>();

        if ("ROLE_ADMIN".equals(userInfo.getAuthority())){
            //임시 데이터
            List<Map<String, Object>> orgList = new ArrayList<>();
            for(int i = 0; i < 10; i++) {
                Map<String, Object> orgMap = new HashMap<>();
                orgMap.put("orgCd", "111111");
                orgMap.put("orgNm", "경기도 용인시");
                orgList.add(orgMap);
            }
            responseMap.put("select", orgList); //공사 시군구 코드 전달
            //선택하기전에 새로고침을 통해 메인페이지 갈영향 있으니 디폴트값 세션에 넣기.
        }
        responseMap.put("status", HttpStatus.OK.value());
        responseMap.put("message", "로그인에 성공했습니다.");
        responseMap.put("redirectUrl", getDefaultTargetUrl());

        String json = objectMapper.writeValueAsString(responseMap);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        //super.onAuthenticationSuccess(request, response, authentication);
    }
}
