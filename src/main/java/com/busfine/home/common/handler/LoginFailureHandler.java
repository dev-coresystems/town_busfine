package com.busfine.home.common.handler;

import com.busfine.home.common.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@RequiredArgsConstructor
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final LoginService loginService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("*********************** onAuthenticationFailure ***********************");
        String userId = request.getParameter("id");
        String errorMsg = "";
        Map<String, Object> responseMap = new HashMap<>();

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        responseMap.put("code", HttpStatus.BAD_REQUEST.value());

        if (exception instanceof BadCredentialsException) {//아이디는 맞지만 비밀번호 실패
            errorMsg = "아이디 또는 비밀번호가 일치하지 않습니다.";
            int cntLoginFail = loginService.findFailCount(userId); //fail cnt 불러옴
            loginService.updateLoginFailCount(userId); //fail cnt 증가
            
            if(cntLoginFail > 0) {
                log.error("{}] 로그인 실패 카운트 : {}", userId, cntLoginFail);
                errorMsg = "id 또는 비밀번호를 잘못입력 했습니다. (5회 중 " + cntLoginFail + "회 실패)";
            }
        } else if (exception instanceof UsernameNotFoundException) { //아이디 비밀번호 둘다 존재하지 않는 사용자
            errorMsg = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if (exception instanceof AccountExpiredException) { //계정 만료
            errorMsg = "계정이 만료되었거나 더이상 사용할 수 없는 계정입니다.";
        } else if (exception instanceof CredentialsExpiredException) { // 비밀번호 만료
            errorMsg = "비밀번호가 만료되었습니다.";
        } else if (exception instanceof DisabledException) { //계정 비활성화
            errorMsg = "이미 접속된 로그인을 해제하시겠습니까?";
            response.setStatus(HttpStatus.CONFLICT.value());
            responseMap.put("code", HttpStatus.CONFLICT.value());
        } else if (exception instanceof LockedException) { // 계정 불일치
            errorMsg = "비밀번호 5회 이상 불일치로 계정이 잠금 상태로 변경되었습니다. 관리자에게 문의하세요.";
        } else if(exception instanceof  InsufficientAuthenticationException){ //인증 정보가 충분하지 않는 경우
            errorMsg = "유효하지 않은 값 입니다.";
        } else if (exception instanceof AuthenticationServiceException) {
            errorMsg = "알수없는 오류";
        }

        log.error("URI: {}", request.getRequestURI());
        log.error("EXCEPTION : {}", exception.getMessage());
        log.error("USER_ID : {}", "");
        log.error("STATUS_CODE : {}", responseMap.get("code"));
        log.error("MESSAGE : {}", errorMsg);
        responseMap.put("message", errorMsg);

        String json = objectMapper.writeValueAsString(responseMap);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        //super.onAuthenticationFailure(request, response, exception);
    }
}
