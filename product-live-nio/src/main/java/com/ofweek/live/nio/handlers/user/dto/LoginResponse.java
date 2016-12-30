package com.ofweek.live.nio.handlers.user.dto;


import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.Visitor;

public class LoginResponse {

    private UserPart user;

    private String appId;

    private int onlineCount;

    private int presentCount;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public UserPart getUser() {
        return user;
    }

    public void setUser(UserPart user) {
        this.user = user;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(int presentCount) {
        this.presentCount = presentCount;
    }

    public static class UserPart extends NioUserDto {
        private boolean employee;
        private Boolean registered;
        private String callback;

        public UserPart(GeneralUser user) {
            super(user);
        }

        public UserPart(Visitor visitor) {
            super(visitor);
        }

        public boolean isEmployee() {
            return employee;
        }

        public UserPart setEmployee(boolean isEmployee) {
            this.employee = isEmployee;
            return this;
        }

        public String getCallback() {
            return callback;
        }

        public void setCallback(String callback) {
            this.callback = callback;
        }

        public Boolean isRegistered() {
            return registered;
        }

        public void setRegistered(Boolean registered) {
            this.registered = registered;
        }


    }

}
