package com.ofweek.live.web.api.action;

import com.ofweek.live.core.common.service.ServiceException;
import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.audience.service.AudienceService;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.security.AccountAuthorizingRealm;
import com.ofweek.live.core.modules.sys.security.AccountToken;
import com.ofweek.live.core.modules.sys.utils.AccessKeyUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import com.ofweek.live.core.modules.sys.utils.WebUtils;
import com.ofweek.live.web.api.common.CodeEnum;
import com.ofweek.live.web.api.common.HttpBaseResponse;
import org.omg.CORBA.UserException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Service;

@Controller
@RequestMapping(value = "/api/admin")
public class ApiAdminLoginController extends ApiBaseController {

    @RequestMapping("login")
    public String login(@RequestParam(required = true) String redirectUrl,
                                @RequestParam(required = true) String ak, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isAdminLogined()) {
            return "redirect:" + redirectUrl;
        }

        String exceptedAk = AccessKeyUtils.encode("admin", redirectUrl, "jeesite");
        if (!ak.equals(exceptedAk)) {
            logger.error("自动登录admin账号时ak验证错误");
            throw new ServiceException("验证不通过");
        }
        //WebUtils.logout(response);
        try {
            UserUtils.getSubject().login(new AccountToken(AccountAuthorizingRealm.ADMIN_LOGIN_FLAG));
            return "redirect:" + redirectUrl;
        } catch (Exception e) {
            logger.error("自动登录admin账号失败", e);
        }
        return "redirect:/";
    }

    private boolean isAdminLogined() {
        User user = UserUtils.getUser();
        return user != null && user.getId().equals("1");
    }

}
