package com.ofweek.live.nio.handlers.user.dto;

import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.Visitor;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;

public class NioUserDto {

    private String userId;
    private Integer userType;
    private String name;
    private Integer sex;
    private String job;
    private String company;
    private String account;

    public NioUserDto() {
    }

    public NioUserDto(GeneralUser user) {
        userId = user.getId();
        userType = user.getType();
        name = user.getName();
        sex = user.getSex();
        job = user.getJob();
        company = user.getCompany();
        account = user.getAccount();
    }

    public NioUserDto(Visitor visitor) {
        this.userId = visitor.getId();
        this.name = visitor.getName();
        this.userType = UserTypeEnum.VISITOR.getCode();
        this.account = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
