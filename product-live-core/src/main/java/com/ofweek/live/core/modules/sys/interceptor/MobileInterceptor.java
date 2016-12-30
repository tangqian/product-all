/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.ofweek.live.core.modules.sys.interceptor;

import com.ofweek.live.core.common.utils.UserAgentUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 手机端访问拦截器
 *
 * @author ThinkGem
 * @version 2014-9-1
 */
public class MobileInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (UserAgentUtils.isMobileOrTablet(request)) {
            String requestUri = request.getRequestURI();
            String redirectUri = null;
            String paramter = null;

            if (request.getRequestURL().toString().startsWith("http://live.smartlifein.com")) {
                if (requestUri.equals("/")) {
                    redirectUri = "http://m.live.smartlifein.com";
                } else if (requestUri.matches("/live/[0-9]+")) {
                    redirectUri = "http://m.live.smartlifein.com" + requestUri;
                }
            }

            if (redirectUri != null) {
                paramter = request.getQueryString();
                if (paramter != null) {
                    redirectUri += "?" + paramter;
                }
                response.sendRedirect(redirectUri);
                return false;
            }
        }
        return true;
    }
}
