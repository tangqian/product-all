package com.ofweek.live.web.sys.action;

import com.google.common.collect.Maps;
import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.security.shiro.session.SessionDAO;
import com.ofweek.live.core.common.servlet.ValidateCodeServlet;
import com.ofweek.live.core.common.utils.CacheUtils;
import com.ofweek.live.core.common.utils.CookieUtils;
import com.ofweek.live.core.common.utils.IdGen;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.common.web.Servlets;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.security.LiveFormAuthenticationFilter;
import com.ofweek.live.core.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 登录Controller
 *
 * @author tangqian
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 管理登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal = UserUtils.getPrincipal();
        if (logger.isDebugEnabled()) {
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }
        // 如果已经登录，则跳转到管理首页
        if (principal != null) {
            isValidateCodeLogin(principal.getAccount(), principal.getType(), false, true);
            return "redirect:" + getRedirectUrl(request);
        }

        return getLoginPage(request, model);
    }

    private String getLoginPage(HttpServletRequest request, Model model) {
        String type = WebUtils.getCleanParam(request, LiveFormAuthenticationFilter.DEFAULT_TYPE_PARAM);
        if (type == null) {
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if (savedRequest != null) {
                String redirectUrl = savedRequest.getRequestUrl();
                if (UserTypeEnum.SPEAKER.isMyPersonalUri(redirectUrl)) {
                    type = String.valueOf(UserTypeEnum.SPEAKER.getCode());
                }
            }
        }
        if (type == null)
            type = String.valueOf(UserTypeEnum.AUDIENCE.getCode());
        model.addAttribute(LiveFormAuthenticationFilter.DEFAULT_TYPE_PARAM, type);
        model.addAttribute("callback", com.ofweek.live.core.modules.sys.utils.WebUtils.getCallback(request, "callback"));

        if (UserTypeEnum.isSpeaker(Integer.valueOf(type)) || UserTypeEnum.isWaiter(Integer.valueOf(type))) {
            return "modules/sys/sysSpeakerLogin";
        } else {
            return "modules/sys/sysLogin";
        }
    }

    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal = UserUtils.getPrincipal();
        // 如果已经登录，则跳转到管理首页
        if (principal != null) {
            return "redirect:/";
        }

        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        boolean mobile = WebUtils.isTrue(request, LiveFormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        Integer type = LiveFormAuthenticationFilter.getType(request);
        String message = (String) request.getAttribute(LiveFormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
            message = "用户或密码错误, 请重试.";
        }
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
        model.addAttribute(LiveFormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
        model.addAttribute(LiveFormAuthenticationFilter.DEFAULT_TYPE_PARAM, type);
        model.addAttribute(LiveFormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

        if (logger.isDebugEnabled()) {
            logger.debug("login fail, active session size: {}, message: {}, exception: {}", new Object[]{
                    sessionDAO.getActiveSessions(false).size(), message, exception});
        }

        // 非授权异常，登录失败，验证码加1。
        if (!UnauthorizedException.class.getName().equals(exception)) {
            model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, type, true, false));
        }

        // 验证失败清空验证码
        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

        // 如果是手机登录，则返回JSON字符串
        if (mobile || Servlets.isAjaxRequest(request)) {
            return renderString(response, model);
        }

        return getLoginPage(request, model);
    }


    /**
     * 登录成功，进入管理首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "loginSuccess")
    public String loginSuccess(HttpServletRequest request, HttpServletResponse response) {
        if (logger.isDebugEnabled()) {
            logger.debug("login success, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        Principal principal = UserUtils.getPrincipal();
        // 登录成功后，验证码计算器清零
        isValidateCodeLogin(principal.getAccount(), principal.getType(), false, true);
        User user = UserUtils.getUser();
        if (UserTypeEnum.isSpeaker(user.getType()) || UserTypeEnum.isWaiter(user.getType())) {
            Cookie cookie = new Cookie("live_speaker", user.getAccount() + "||" + user.getType());
            cookie.setDomain(".smartlifein.com");
            response.addCookie(cookie);
        }
        if (Servlets.isAjaxRequest(request)) {
            return renderString(response, principal);
        }
        return "redirect:" + getRedirectUrl(request);
    }

    private String getRedirectUrl(ServletRequest request) {
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        String redirectUrl = "/";
        if (savedRequest != null && savedRequest.getMethod().equals("GET")) {
            redirectUrl = savedRequest.getRequestUrl();
        }
        if (redirectUrl.equals("/")) {
            User user = UserUtils.getUser();
            if (user != null) {
                if (UserTypeEnum.isSpeaker(user.getType())) {
                    redirectUrl = "/room";
                }
            }
        }
        return redirectUrl;
    }

    /**
     * 获取主题方案
     */
    @RequestMapping(value = "/theme/{theme}")
    public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(theme)) {
            CookieUtils.setCookie(response, "theme", theme);
        } else {
            theme = CookieUtils.getCookie(request, "theme");
        }
        return "redirect:" + request.getParameter("url");
    }

    /**
     * 是否是验证码登录
     *
     * @param account 用户名
     * @param type    用户类型
     * @param isFail  计数加1
     * @param clean   计数清零
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidateCodeLogin(String account, Integer type, boolean isFail, boolean clean) {
        String failKey = account + "_" + type;
        Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
        if (loginFailMap == null) {
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(failKey);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        if (isFail) {
            loginFailNum++;
            loginFailMap.put(failKey, loginFailNum);
        }
        if (clean) {
            loginFailMap.remove(failKey);
        }
        return loginFailNum >= 3;
    }

    /**
     * 第三方登录
     * QQ, 微博, 微信
     *
     * @param method
     * @param callback
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/thirdPartyLogin")
    public String thirdPartyLogin(String method, String callback, HttpServletRequest request, HttpServletResponse response) throws IOException {

        if ("weixinLogin".equals(method) || "qqLogin".equals(method) || "weiboLogin".equals(method)) {
            String domain = LiveEnv.getWebSite();
            callback = StringUtils.defaultIfBlank(callback, domain);

            String url = "http://www.smartlifein.com/user/login.do?method=" + method + "&returnurl=";
            if (Base64.isBase64(callback)) {
                try {
                    byte[] asBytes = Base64.decodeBase64(callback);
                    callback = new String(asBytes, "utf-8");
                    if (callback.contains("http://")) {
                        String temp = callback.substring(callback.indexOf("//") + 2, callback.length());
                        temp = temp.substring(temp.indexOf('/'));
                        url = url + domain + "/" + temp;
                    } else {
                        url = url + domain;
                    }
                } catch (Exception e) {
                    logger.error("解码跳转callbackUrl异常,callback=" + callback, e);
                    url = url + domain;
                }
            } else {
                url = url + callback;
            }

            response.sendRedirect(url);
        }
        return null;
    }
}
