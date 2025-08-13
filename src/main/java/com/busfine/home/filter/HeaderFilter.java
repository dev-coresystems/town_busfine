package com.busfine.home.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeaderFilter implements Filter  {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        filterNoCache(httpResp);
        filterServer(httpResp);//서버명 헤더
        filterHsts(httpReq, httpResp);
        filterCsp(httpResp);//CSP 헤더
        filterReferrer(httpResp);//Referer 제어 헤더

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

    private void filterServer(HttpServletResponse response){
        response.setHeader("Server", "");
    }

    private void filterNoCache(HttpServletResponse response){
        response.setHeader("Cache-Control", "no-cache");
    }

    /**
     * HTTP Strict-Transport-Security
     * @param response
     */
    private void filterHsts(HttpServletRequest request, HttpServletResponse response){
        if (request.isSecure()) {//앞단에 JEUS8.5가 있어서 isSecure 는 무조건 false로 옴 배포시에 조건문 주석 필요
            // max-age=31536000 1년 동안 HTTPS 강제
            // includeSubDomains 모든 하위 도메인에도 적용
            //response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");

            //5분 동안 HTTPS 강제
            //하위 도메인 적용 안함
            response.setHeader("Strict-Transport-Security", "max-age=300");
        }
    }

    /**
     * CSP 적용 헤더
     * @param response
     */
    private void filterCsp(HttpServletResponse response) {
        /**
         default-src 'self' 내 도메인안의 리소스만 허용
         default-src * 리소스 전부 허용
         unsafe-inline  태그 내부 스타일 다 허용
         unsafe-eval eval() 유사 함수 허용
         data: base64 허용
         blob: blob URL 허용
         **/
        response.setHeader("Content-Security-Policy", "default-src * 'unsafe-inline' 'unsafe-eval' data: blob:;");
    }

    /**
     * Referer 제어 헤더 정보(URL)
     * @param response
     */
    private void filterReferrer(HttpServletResponse response){
        /**
         * strict-origin-when-cross-origin  동일 출처 요청에는 전체 URL, 교차 출처에는 origin만
         */
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
    }

    /**
     * 콘텐츠 타입 스니핑 방지
     * @param response
     */
    private void filterXContentType(HttpServletResponse response){
        /**
         * 콘텐츠 타입을 MIME 스니핑하지 않도록 방지
         */
        response.setHeader("X-Content-Type-Options", "nosniff");
    }

    /**
     * XSS 필터
     * @param response
     */
    private void filterXssProtection(HttpServletResponse response) {
        response.setHeader("X-XSS-Protection", "1; mode=block");
    }
}
