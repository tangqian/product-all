/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.ofweek.live.core.modules.sys.interceptor;

import com.ofweek.live.core.common.service.BaseService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.utils.UserAgentUtils;
import com.ofweek.live.core.common.web.Servlets;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 安全访问拦截器
 * @author tangqian
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		String uri = request.getServletPath();
		if (Servlets.isStaticFile(uri)){
			return true;
		}

		if (UserTypeEnum.SPEAKER.isMyPersonalUri(uri)){
			User currentUser = UserUtils.getUser();
			if(!UserTypeEnum.isSpeaker(currentUser.getType())){
				response.sendRedirect("/");
				return false;
			}
		}

		return true;
	}

}
