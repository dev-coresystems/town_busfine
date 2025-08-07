package com.busfine.home.common.handler;

import com.busfine.home.common.service.CommonService;
import com.busfine.home.common.vo.GbfUserDetails;
import com.busfine.home.common.vo.MnuVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class GbfInterceptor implements HandlerInterceptor {
    private final CommonService commonService;

    private Map<String, Object> pgmMnu;
    private List<Map<String, Object>> rootPgmMnu;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("*********************** preHandle start *********************** ");
        log.info("Intercepted URI: {}", request.getRequestURI());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
        log.info("*********************** postHandle start ***********************");

        if(mav != null) {
            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof GbfUserDetails) {
                GbfUserDetails userDetails = (GbfUserDetails) details;
                String uri = request.getRequestURI();
                String pgmId = getUriPgmId(uri);

                //메뉴 VIEW 정보 response
                List<MnuVo> mnuList = setMnuList(userDetails.getAuthority(), pgmId, mav);
                //접근권한 정보 response
                MnuVo mvo = setAuth(mnuList, pgmId, mav);

                //페이지 접근 권한 거부
                if(! "/".equals(uri) && ! mnuSearchFilter(mvo)) {
                    response.sendError(HttpStatus.FORBIDDEN.value());
                    return;
                }
            }
        }

        HandlerInterceptor.super.postHandle(request, response, handler, mav);
    }

    private List<MnuVo> setMnuList(String auth, String pgmId, ModelAndView mav){
        String currentPgmGrp = "";
        List<MnuVo> mnuList = commonService.getMnuList(auth);

        for (MnuVo vo : mnuList) {
            if (pgmId.equals(vo.getPgmId())) {
                currentPgmGrp = vo.getPgmGrp();
                break;
            }
        }

        List<MnuVo> leftMnuList = new ArrayList<>();
        if(!"".equals(currentPgmGrp)) {
            for(MnuVo vo : mnuList) {
                if(currentPgmGrp.equals(vo.getPgmGrp())) {
                    leftMnuList.add(vo);
                }
            }
        }
        if(mav != null) {
            mav.addObject("mnuList", mnuList);
            mav.addObject("leftMnuList", leftMnuList);
        }
        return mnuList;
    }

    private MnuVo setAuth(List<MnuVo> mnuVoList, String pgmId, ModelAndView mav) {
        MnuVo mvo = new MnuVo();
        for(MnuVo vo : mnuVoList) {
            if(pgmId.equals(vo.getPgmId())) {
               mvo = vo;
            }
        }

        if(mav != null) {
            mav.addObject("auth", mvo);
        }

        return mvo;
    }

    private boolean mnuSearchFilter(MnuVo vo) {
        return "Y".equals(vo.getSearchYn());
    }

    private String getUriPgmId(String uri) {
        String pgmId = uri.substring(uri.lastIndexOf("/") + 1);
        pgmId = pgmId.toUpperCase();

        if (pgmId.endsWith("_VIEW.GBF")) {
            pgmId = pgmId.replace("_VIEW.GBF", "");
        } else if (pgmId.endsWith("_CUD.GBF")) {
            pgmId = pgmId.replace("_CUD.GBF", "");
        } else if (pgmId.endsWith(".GBF")) {
            pgmId = pgmId.replace(".GBF", ""); // 예외적으로 _view/_cud 없을 경우 처리
        }

        return pgmId;
    }
}
