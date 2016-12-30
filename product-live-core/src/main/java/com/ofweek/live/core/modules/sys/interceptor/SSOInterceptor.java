package com.ofweek.live.core.modules.sys.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.Servlets;
import com.ofweek.live.core.modules.audience.service.AudienceService;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.security.AccountToken;
import com.ofweek.live.core.modules.sys.utils.AccessKeyUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import com.ofweek.live.core.modules.sys.utils.WebUtils;

public class SSOInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SSOInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getServletPath();
		if (Servlets.isStaticFile(uri)){
	        return true;
		}
		
        User currentUser = UserUtils.getUser();
        if(currentUser != null){
        	return true;
        }
        
        String account = WebUtils.getAccountFromCookie(request);
        if (StringUtils.isNotBlank(account)) {
            try {
                UserUtils.getSubject().login(new AccountToken(account));
            } catch (Exception e) {
                logger.error("自动单点登录失败,account={}", account);
                logger.error("详细信息如下：", e);
            }
        }
        return true;
    }

}
