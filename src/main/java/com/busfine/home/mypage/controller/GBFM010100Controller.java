package com.busfine.home.mypage.controller;

import com.busfine.home.common.vo.GbfUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("mypage/home")
public class GBFM010100Controller {
    @GetMapping("/GBFM010100.gbf")
    public String view(Authentication auth) {
        GbfUserDetails userInfo = (GbfUserDetails)auth.getPrincipal();
        return "mypage/GBFM010100.gbf";
    }
}
