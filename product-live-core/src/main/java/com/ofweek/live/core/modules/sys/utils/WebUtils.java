package com.ofweek.live.core.modules.sys.utils;

import com.ofweek.live.core.common.utils.StringUtils;
import org.apache.shiro.session.SessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class WebUtils {

    private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

    private static String COOKIE_SPEAKER = "live_speaker";

    private static String COOKIE_AUDIENCE = "live_username";

    public static String getAccountFromCookie(HttpServletRequest request) {
        return getCookieValue(request, COOKIE_AUDIENCE);
    }

    public static String getUrl(HttpServletRequest request) {
        StringBuffer sb = request.getRequestURL();
        System.out.println(sb.toString());
        if (request.getQueryString() != null) {
            sb.append("?").append(request.getQueryString());
        }
        return sb.toString();
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("username cookie value decode error, username={}", cookie.getValue());
                    }
                }
            }
        }
        return null;
    }

    /**
     * 用户退出通用方法
     *
     * @param response
     */
    public static void logout(ServletResponse response) {
        try {
            if (response instanceof HttpServletResponse) {
                killCookie((HttpServletResponse) response, COOKIE_SPEAKER);
            }
            UserUtils.getSubject().logout();
        } catch (SessionException ise) {
        }
    }

    private static void killCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain(".smartlifein.com");
        response.addCookie(cookie);
    }

    /**
     * <从request对象中获取用户IP地址>
     *
     * @param request
     * @return IP地址
     */
    public static String getIp(HttpServletRequest request) {
        String forwards = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(forwards) || "unknown".equalsIgnoreCase(forwards)) {
            forwards = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(forwards) || "unknown".equalsIgnoreCase(forwards)) {
            forwards = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(forwards) || "unknown".equalsIgnoreCase(forwards)) {
            forwards = request.getRemoteAddr();
        }
        if (StringUtils.isBlank(forwards) || "unknown".equalsIgnoreCase(forwards)) {
            forwards = request.getHeader("X-Real-IP");
        }
        if (forwards != null && forwards.trim().length() > 0) {
            int index = forwards.indexOf(',');
            return (index != -1) ? forwards.substring(0, index) : forwards;
        }
        return forwards;
    }

    public static String getCallback(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (StringUtils.isBlank(value)) {
            value = (String) request.getAttribute(name);
        }
        return value;
    }
}
