package com.busfine.home.approve.controller;

import com.busfine.home.approve.param.GBFM020101ParamVo;
import com.busfine.home.approve.service.GBFM020101Service;
import com.busfine.home.approve.vo.GBFM020101Vo;
import com.busfine.home.common.util.Pagination;
import com.busfine.home.common.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/approve/company")
@RequiredArgsConstructor
public class GBFM020101Controller {
    private final GBFM020101Service service;

    @RequestMapping(value = "/GBFM020101.gbf", method = {RequestMethod.GET, RequestMethod.POST})
    public String view(Authentication auth, Model model, @ModelAttribute("paramVo") GBFM020101ParamVo paramVo) {
        try{

            log.info("param : {}", Util.AllFields(paramVo));

            if(paramVo.getPageNo() == 0) {
                paramVo.setPageNo(1);
            }

            Pagination pagination = new Pagination(paramVo.getPageNo(), paramVo.getPageSize());
            paramVo.setOffset(pagination.getOffset());
            paramVo.setLimit(pagination.getLimit());

            List<GBFM020101Vo> list = service.selectList(paramVo);

            pagination.setTotalCount(list != null && !list.isEmpty() ? list.get(0).getTotalCnt() : 0);

            model.addAttribute("list", list);
            model.addAttribute("pagination", pagination);
            return "approve/GBFM020101.gbf";
        } catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }
}
