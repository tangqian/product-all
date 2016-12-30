package com.ofweek.live.core.modules.sys.security;

import javax.servlet.ServletResponse;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户和密码及用户类型（包含验证码）令牌类
 * 
 * @author tangqian
 * 
 * 
 */
public class LiveUsernamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private Integer type;

	private String captcha;

	private boolean mobileLogin;

	private ServletResponse response;

	public LiveUsernamePasswordToken() {
		super();
	}

	public LiveUsernamePasswordToken(String username, Integer type, char[] password, boolean rememberMe, String host, String captcha,
			boolean mobileLogin, ServletResponse response) {
		super(username, password, rememberMe, host);
		this.type = type;
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
		this.response = response;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	public ServletResponse getResponse() {
		return response;
	}

	public Integer getType() {
		return type;
	}
}