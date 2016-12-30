package com.ofweek.live.core.modules.sys.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 单账号token
 *
 * @author tangqian
 */
public class AccountToken implements AuthenticationToken {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String account;

    public AccountToken() {
    }

    public AccountToken(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public Object getPrincipal() {
        return account;
    }

    @Override
    public Object getCredentials() {
        return account;
    }

}
