package com.ofweek.live.core.modules.sys.dto;

import com.ofweek.live.core.modules.room.enums.NioLoginEnum;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.entity.Visitor;
import com.ofweek.live.core.modules.sys.utils.AccessKeyUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by tangqian on 2016/9/28.
 */
public class AccessVo {

    private String userId;

    private String nonce;

    private int loginType;

    private String ak;

    public AccessVo(User user) {
        userId = user.getId();
        nonce = RandomStringUtils.randomAlphanumeric(6);
        ak = AccessKeyUtils.encode(userId, nonce, "ofweek");
        loginType = NioLoginEnum.NORMAL.getCode();
    }

    public AccessVo(Visitor user) {
        userId = user.getId();
        nonce = RandomStringUtils.randomAlphanumeric(6);
        ak = AccessKeyUtils.encode(userId, nonce, "ofweek");
        loginType = NioLoginEnum.VISITOR.getCode();
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

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }
}
