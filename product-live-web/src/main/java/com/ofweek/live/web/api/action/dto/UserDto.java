package com.ofweek.live.web.api.action.dto;

import com.ofweek.live.core.common.config.LiveEnv;

import java.io.Serializable;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String href;

    public UserDto() {

    }

    public UserDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHref() {
        return LiveEnv.getWebSite() + "/room";
    }

    public void setHref(String href) {
        this.href = href;
    }
}
