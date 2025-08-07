package com.busfine.home.common.handler;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class CsrfRequestMatcher implements RequestMatcher {
    private static final Pattern ALLOWED_METHODS = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    @Override
    public boolean matches(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.equals("/login")) {
            return false;
        }

        return !ALLOWED_METHODS.matcher(request.getMethod()).matches();
    }
}
