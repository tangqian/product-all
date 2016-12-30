package com.ofweek.live.core.modules.sys.entity;

/**
 * 默认用户信息，提供给管理员用
 * Created by Administrator on 2016/8/25.
 */
public class DefaultUser implements GeneralUser {

    private User user;

    @Override
    public String getId() {
        return user.getId();
    }

    @Override
    public String getAccount() {
        return user.getAccount();
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public Integer getType() {
        return user.getType();
    }

    @Override
    public String getCompany() {
        return "智慧生活";
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public String getName() {
        return getAccount();
    }

    @Override
    public String getJob() {
        return null;
    }

    @Override
    public String getLogoId() {
        return null;
    }

    @Override
    public Integer getSex() {
        return 0;
    }

    @Override
    public String getDepartment() {
        return null;
    }

    @Override
    public String getMobilePhone() {
        return null;
    }

    @Override
    public String getTelephone() {
        return null;
    }

    @Override
    public String getFax() {
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
