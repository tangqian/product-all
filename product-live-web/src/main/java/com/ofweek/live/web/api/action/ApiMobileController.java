package com.ofweek.live.web.api.action;

import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.audience.service.AudienceService;
import com.ofweek.live.core.modules.room.dto.RoomDto;
import com.ofweek.live.core.modules.room.service.RoomService;
import com.ofweek.live.core.modules.rpc.InvokeSmartlifein;
import com.ofweek.live.core.modules.rpc.dto.UserDto;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.entity.Visitor;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.core.modules.sys.service.VisitorService;
import com.ofweek.live.core.modules.sys.utils.AccessKeyUtils;
import com.ofweek.live.web.api.action.dto.UserInfoDto;
import com.ofweek.live.web.api.common.HttpCommonResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/mobile/")
public class ApiMobileController extends ApiBaseController {

    @Resource
    private UserService userService;

    @Resource
    private AudienceService audienceService;

    @Resource
    private VisitorService visitorService;

    @Autowired
    private RoomService roomService;

    @RequestMapping("getUserInfoByAccount")
    public HttpCommonResponse<Object> getUserInfoByAccount(@RequestParam(required = true) String account,
                                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserInfoDto data = new UserInfoDto();
        User user = userService.get(account, 1);
        if(user != null){
            String userId = user.getId();
            buildUserInfoDto(userId, data);
        }else{
            UserDto userDto = InvokeSmartlifein.getUser(account);
            if (userDto != null) {
                Audience audience = audienceService.save(userDto);
                if(audience != null){
                    String userId = audience.getUser().getId();
                    buildUserInfoDto(userId,data);
                }
            }
        }
        HttpCommonResponse<Object> result = new HttpCommonResponse<>();
        result.setData(data);
        return result;
    }

    private void buildUserInfoDto(String userId,UserInfoDto data){
        String nonce = RandomStringUtils.randomAlphanumeric(6);
        String ak = AccessKeyUtils.encode(userId, nonce, "ofweek");
        data.setUserId(userId);
        data.setNonce(nonce);
        data.setAk(ak);
    }

    @RequestMapping("visitorLogin")
    public HttpCommonResponse<Object> visitorLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserInfoDto data = new UserInfoDto();
        Visitor visitor = visitorService.save(request, response);
        String userId = visitor.getId();
        buildUserInfoDto(userId, data);
        HttpCommonResponse<Object> result = new HttpCommonResponse<>();
        result.setData(data);
        return result;
    }

    @RequestMapping(value = {"getIndexLiveList"})
    public HttpCommonResponse<Object> getIndexLiveList(@RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "offset", required = false) Integer offset, HttpServletRequest request) {
        HttpCommonResponse<Object> result = new HttpCommonResponse<>();
        try {
            List<RoomDto> room = roomService.findIndexLiveList(status, offset);
            result.setData(room);
        } catch (Exception e) {
            //setError(result, RoomDto.class);
            e.printStackTrace();
        }
        return result;
    }
}
