package com.ofweek.live.web.api.action.dto;

import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.User;

/**
 * Created by tangqian on 2016/12/21.
 */
public class CommonUserDto {

    private String account;

    private String name;

    private Integer userType;

    public CommonUserDto(GeneralUser generalUser) {
        this.account = generalUser.getAccount();
        this.name = generalUser.getName();
        this.userType = generalUser.getType();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
