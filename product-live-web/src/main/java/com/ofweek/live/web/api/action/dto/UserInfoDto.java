package com.ofweek.live.web.api.action.dto;

/**
 * Created by Administrator on 2016/11/29.
 */
public class UserInfoDto {
    private String userId;

    private String nonce;

    private String ak;

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }


}
