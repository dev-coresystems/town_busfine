package com.busfine.home.common.handler;

import com.busfine.home.common.service.LoginService;
import com.busfine.home.common.vo.GbfUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class GbfAuthenticationProvider implements AuthenticationProvider {
    private final LoginService loginService;
    private final SessionRegistry sessionRegistry;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("*********************** authenticationProvider ***********************");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String id = (String) authentication.getPrincipal();
        String pwd = (String) authentication.getCredentials();

        if (!id.matches("^[a-zA-Z0-9가-힣_ ()]+$") || id.matches("^\\d+$") || !pwd.matches("^[0-9a-zA-Z~!@#$%^]+$")) {
            throw new InsufficientAuthenticationException("입력값이 유효하지 않습니다.");
        }

        //권한 정보 가져오기
        GbfUserDetails userDetails = loginService.getUserInfo(id);
        if (userDetails == null) {
            throw new UsernameNotFoundException("접속자 정보를 찾을 수 없습니다.");
        } else if (userDetails.getFailCount() > 5) {
            throw new LockedException(id); // 계정 잠김(비밀번호 5회이상 불일치)
        } else if (!(encoder.matches(pwd, userDetails.getPwd()))) { // id 및 패스워드 불일치
            throw new BadCredentialsException(id);
        }

        //로그인 이후 처리할수 있도록 변경됨.
        //if("Y".equals(userDetails.getPwExpired())) {
        //    throw new CredentialsExpiredException("비밀번호가 만료되었습니다. 변경 후 로그인해주세요.");
        //}

        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        boolean force = false;

        if (request.getParameter("force") != null) {
            force = "true".equals(request.getParameter("force"));
        }

        for (Object principal : allPrincipals) {
            if (principal instanceof GbfUserDetails) {
                GbfUserDetails details = (GbfUserDetails) principal;
                if (details.getUserId().equals(id)) {
                    List<SessionInformation> sessions = sessionRegistry.getAllSessions(details, false);
                    if (!sessions.isEmpty()) {
                        if (!force) {
                            throw new DisabledException("이미 로그인 중인 사용자입니다.");
                        } else {
                            for (SessionInformation session : sessions) {
                                session.expireNow();
                            }
                        }
                    }
                }
            }
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(userDetails.getAuthority()));

        //loginService.updateLastLogin(id);
        loginService.updateLoginFailCountToZero(id);
        userDetails.setFailCount(0);

        //로그인정보 + 권한정보를 토큰으로 생성한다.
        UsernamePasswordAuthenticationToken resultToken  = new UsernamePasswordAuthenticationToken(userDetails, null, roles);
        //생성한 토큰을 주입 한다.
        resultToken.setDetails(userDetails);
        sessionRegistry.registerNewSession(request.getSession().getId(), authentication.getName());
        SecurityContextHolder.getContext().setAuthentication(resultToken);
        request.getSession().setAttribute("userId", userDetails.getUserId());
        return resultToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
