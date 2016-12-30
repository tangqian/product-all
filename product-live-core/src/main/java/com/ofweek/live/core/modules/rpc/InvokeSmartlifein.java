/**
 * @Date 2015年11月18日 下午5:06:31
 * @author tangq
 * @version V1.0
 */
package com.ofweek.live.core.modules.rpc;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.utils.FastJsonUtils;
import com.ofweek.live.core.common.utils.NewHttpClient;
import com.ofweek.live.core.common.utils.NewHttpClient.FullResponse;
import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.rpc.dto.InvokeSmartlifeinResponseDto;
import com.ofweek.live.core.modules.rpc.dto.UserDto;
import org.apache.http.cookie.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用smartlifein相关接口
 */
public class InvokeSmartlifein {

    private static final Logger logger = LoggerFactory.getLogger(InvokeSmartlifein.class);

    public static UserDto login(String username, String pwd, ServletResponse httpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("pwd", pwd);
        params.put("do", "authlogin");

        String url = String.format("%s?op=%s&t=%s", LiveEnv.Smartlifein.getLoginUrl(), "auth", (new Date()).getTime());
        FullResponse response = NewHttpClient.postAll(url, params, true);
        UserDto userDto = null;
        try {
            userDto = FastJsonUtils.parseObject(response.getBody(), UserDto.class);

            List<Cookie> cookies = response.getCookies();
            if (cookies != null && httpResponse != null && httpResponse instanceof HttpServletResponse) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) httpResponse;
                for (Cookie cookie : cookies) {
                    logger.warn("接收cookie: " + cookie);
                    javax.servlet.http.Cookie reponseCookie = new javax.servlet.http.Cookie(cookie.getName(), cookie.getValue());
                    reponseCookie.setDomain(cookie.getDomain());
                    if (cookie.getExpiryDate() == null) {
                        reponseCookie.setMaxAge(-1);
                    } else {
                        reponseCookie.setMaxAge((int) (cookie.getExpiryDate().getTime() - System.currentTimeMillis()));
                    }
                    reponseCookie.setPath(cookie.getPath());
                    httpServletResponse.addCookie(reponseCookie);
                }
            }
        } catch (Exception e) {
            logger.error("登录智慧生活网失败,response={}", response.getBody());
        }
        return userDto;
    }

    public static UserDto getUser(String username) {
        String url = String.format("%s&username=%s&t=%s", LiveEnv.Smartlifein.getQueryUrl(), username, (new Date()).getTime());
        String result = NewHttpClient.get(url, true);
        UserDto userDto = null;
        try {
            boolean flag = FastJsonUtils.parseObject(result).get("status").equals(0);
            if (!flag)
                userDto = FastJsonUtils.parseObject(result, UserDto.class);
        } catch (Exception e) {
            logger.error("查询智慧生活网会员数据失败,response={}", result);
            logger.error("失败原因：", e);
        }
        if(userDto == null){
            logger.error("获取用户信息失败,username={}", username);
        }
        return userDto;
    }

    /**
     * 向smartlifein更新会员信息
     *
     * @param audience
     * @return
     */
    public static InvokeSmartlifeinResponseDto updateUser(Audience audience) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userid", audience.getAccount());
        params.put("name", audience.getName());
        params.put("mobile", audience.getMobilePhone());
        params.put("t", (new Date()).getTime());

        String result = NewHttpClient.post(LiveEnv.Smartlifein.getUpdateUser(), params, true);
        InvokeSmartlifeinResponseDto dto = null;
        boolean flag = FastJsonUtils.parseObject(result).get("status").equals(0);
        if (!flag) {
            logger.error("调用智慧生活网会员信息更新接口失败！");
        } else {
            try {
                dto = FastJsonUtils.parseObject(result.trim(), InvokeSmartlifeinResponseDto.class);
            } catch (Exception e) {
                logger.error("json解析智慧生活网会员信息更新接口返回结果失败, responseText={}", result);
            }
        }
        return dto;
    }

    public static void main(String[] args) {
        System.setProperty("config.path", "E:/IdeaProjects/smartlifein-live/product-live-webapp/src/main/config/");
        getUser("tangqian9140");
    }
}
