package com.busfine.home.common.controller;

import com.busfine.home.common.vo.GbfUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @GetMapping("/")
    public String viewMain(){
        return "redirect:/mypage/home/GBFM010100.gbf";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/session-expired")
    public String sessionExpired() {
        return "session-expired";
    }
}
