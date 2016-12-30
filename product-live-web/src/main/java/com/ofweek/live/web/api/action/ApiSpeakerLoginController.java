package com.ofweek.live.web.api.action;

import com.ofweek.live.core.modules.base.entity.Result;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.security.SmartlifeinToken;
import com.ofweek.live.core.modules.sys.service.SystemService;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import com.ofweek.live.web.api.action.dto.CommonUserDto;
import com.ofweek.live.web.api.action.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/speaker")
public class ApiSpeakerLoginController extends ApiBaseController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "get")
    @ResponseBody
    public CommonUserDto get(@RequestParam(required = true) String username, @RequestParam(required = true) Integer type) {
        User user = userService.get(username, type);
        if (user != null) {
            GeneralUser generalUser = userService.getGeneralUser(user.getId());
            return new CommonUserDto(generalUser);
        }
        return null;
    }

    @RequestMapping(value = "login")
    @ResponseBody
    public Result<Object> login(@RequestParam(required = true) String username, @RequestParam(required = true) String password,
                                @RequestParam(required = true) Integer type, HttpServletRequest request) throws Exception {
        return resultJson(username, password, type);
    }

    private Result<Object> resultJson(String username, String password, Integer type) {
        Result<Object> result = new Result<>();
        User user = userService.get(username, type);
        if (user != null) {
            if (SystemService.validatePassword(password, user.getPassword())) {
                result.setData(new UserDto(username));
                if (!isLogined(username)) {
                    UserUtils.getSubject().login(new SmartlifeinToken(username, user.getPassword(), type));
                }
            } else {
                result.setStatus(false);
                result.setErrorCode("500");
                result.setErrorMsg("密码错误.");
            }
        } else {
            result.setStatus(false);
            result.setErrorCode("500");
            if (UserTypeEnum.isSpeaker(type)) {
                result.setErrorMsg("企业帐号不存在.");
            } else if (UserTypeEnum.isWaiter(type)) {
                result.setErrorMsg("客服帐号不存在.");
            }
        }
        return result;
    }

    private boolean isLogined(String username) {
        User user = UserUtils.getUser();
        return user != null && user.getAccount().equals(username);
    }
}
