package com.busfine.home.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("system")
public class GBFS1010Controller {
    @GetMapping("/GBFS1010.gbf")
    public String view(){
        return "system/GBFS1010.gbf";
    }
}
