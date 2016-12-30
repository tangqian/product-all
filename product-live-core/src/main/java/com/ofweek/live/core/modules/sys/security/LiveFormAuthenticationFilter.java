package com.ofweek.live.core.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 表单验证（包含验证码）过滤类
 * 
 * @author tangqian
 * 
 */
public class LiveFormAuthenticationFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";
	public static final String DEFAULT_TYPE_PARAM = "type";

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
		String captcha = getCaptcha(request);
		Integer type = getType(request);
		boolean mobile = isMobileLogin(request);
		return new LiveUsernamePasswordToken(username, type, password.toCharArray(), rememberMe, host, captcha, mobile, response);
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, DEFAULT_CAPTCHA_PARAM);
	}

	public static Integer getType(ServletRequest request) {
		String type = WebUtils.getCleanParam(request, DEFAULT_TYPE_PARAM);
		return type == null ? UserTypeEnum.AUDIENCE.getCode() : Integer.valueOf(type);
	}

	protected boolean isMobileLogin(ServletRequest request) {
		return WebUtils.isTrue(request, DEFAULT_MOBILE_PARAM);
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName(), message = "";
		if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")) {
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		} else if (e instanceof IncorrectCredentialsException || e instanceof UnknownAccountException || e instanceof AuthenticationException) {
			message = "用户或密码错误, 请重试.";
		} else {
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}
		request.setAttribute(getFailureKeyAttribute(), className);
		request.setAttribute(DEFAULT_MESSAGE_PARAM, message);
		return true;
	}

}