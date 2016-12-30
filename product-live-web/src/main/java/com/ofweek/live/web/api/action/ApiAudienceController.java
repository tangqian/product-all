package com.ofweek.live.web.api.action;

import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.audience.service.AudienceService;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import com.ofweek.live.web.api.common.CodeEnum;
import com.ofweek.live.web.api.common.HttpBaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/audience")
public class ApiAudienceController extends ApiBaseController {

    @Resource
    private AudienceService audienceService;

    @RequestMapping(value = {"update"})
    public HttpBaseResponse update(Audience audience) {
        HttpBaseResponse baseResponse = new HttpBaseResponse();

        User user = UserUtils.getUser();
        if (user == null || !UserTypeEnum.isAudience(user.getType())) {
            baseResponse.setCode(CodeEnum.CLIENT_PARAM_ILLEGAL);
            baseResponse.setMessage("信息保存失败,请先登录!");
            return baseResponse;
        }

        audience.setId(user.getId());
        audienceService.save(audience);
        return baseResponse;
    }

}
