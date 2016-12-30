package com.ofweek.live.web.api.action;

import com.ofweek.live.core.common.service.ServiceException;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.base.exception.BaseException;
import com.ofweek.live.web.api.common.CodeEnum;
import com.ofweek.live.web.api.common.HttpCommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tangqian on 2016/9/23.
 */
public class ApiBaseController extends BaseController {

    @ExceptionHandler
    @ResponseBody
    public HttpCommonResponse exp(Exception ex) {
        logger.error("接口发生异常", ex);
        HttpCommonResponse result = new HttpCommonResponse();
        result.setCode(CodeEnum.SERVER_ERROR);
        result.setMessage(BaseException.getSecurityMessage(ex));
        return result;
    }

}
