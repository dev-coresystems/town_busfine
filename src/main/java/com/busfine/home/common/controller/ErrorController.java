package com.busfine.home.common.controller;

import com.busfine.home.common.vo.GbfUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/handle")
    public Object handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        //예외가 명시적으로 처리되지 않으면 서블릿 스펙에 따라서 500 으로 처리될수 있음.
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

        String message = "알 수 없는 오류가 발생했습니다.";

        if(throwable instanceof RequestRejectedException) {//잘못된 요청인 경우
            statusCode = HttpStatus.BAD_REQUEST.value();
        }
        
        if (statusCode != null) {
            switch (statusCode) {
                case 400: message = "잘못된 요청입니다."; break;
                case 403: message = "접근 권한이 없습니다."; break;
                case 404: message = "요청하신 페이지를 찾을 수 없습니다."; break;
                case 500: message = "서버 내부 오류가 발생했습니다."; break;
                default: message = "오류 코드 : " + statusCode; break;
            }
        }

        log.error("*********************** ERROR ***********************");
        log.error("URI: {}", errorUri);
        log.error("EXCEPTION: {}", (throwable != null ? throwable : "예외 정보 없음"));

        String userId = Optional.ofNullable(request.getSession().getAttribute("userId")).orElse("").toString();

        if( !"".equals(userId)) {
            log.error("USER_ID : {}", userId);
        }

        log.error("STATUS_CODE : {}", statusCode);

        if(statusCode != null && "".equals(userId) && statusCode == 403) {
            log.error("MESSAGE : {}", "세션만료");
            return "/session-expired";
        }

        log.error("MESSAGE : {}", message);

        String xhrHeader = request.getHeader("X-Requested-With");
        String acceptHeader = request.getHeader("Accept");

        if("XMLHttpRequest".equals(xhrHeader) || (acceptHeader != null && acceptHeader.contains("application/json"))) {
            Map<String, Object> body = new HashMap<>();
            body.put("code", statusCode);
            body.put("message", message);
            return new ResponseEntity<>(body, HttpStatus.valueOf(statusCode));
        }

        model.addAttribute("code", statusCode);
        model.addAttribute("message", message);
        model.addAttribute("exception", throwable);

        return "error/error";
    }
}
