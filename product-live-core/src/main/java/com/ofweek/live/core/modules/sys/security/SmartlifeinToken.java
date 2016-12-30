package com.ofweek.live.core.modules.sys.security;

import org.apache.shiro.authc.AuthenticationToken;

public class SmartlifeinToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private Integer type;

    public SmartlifeinToken() {
    }

    public SmartlifeinToken(String username, String password, Integer type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return username;
    }

}
