package com.busfine.home.approve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/approve/route")
public class GBFM020102Controller {
    @GetMapping("/GBFM020102.gbf")
    public String view(Principal principal) {
        //System.out.println(principal);
        return "approve/GBFM020102.gbf";
    }
}
