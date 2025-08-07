package com.busfine.home.common.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Data
public class GbfUserDetails implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String pwd;
    private String authority;
    private String authNm;
    private String userNm;
    private String compId;
    private String orgCd;
    private String useYn;
    private String pwExpired;
    private String lastLoginDt;
    private String lastPwUpdateDt;
    private int failCount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.getPwd();
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return !(this.getFailCount() > 5);
    }
}
