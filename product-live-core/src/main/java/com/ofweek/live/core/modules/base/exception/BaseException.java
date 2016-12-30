package com.ofweek.live.core.modules.base.exception;

import com.ofweek.live.core.common.service.ServiceException;
import com.ofweek.live.core.common.utils.StringUtils;

/**
 * 直播系统基础Exception,其它异常请继承这个基础Exception.
 *
 * @author tangqian
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public static final String getSecurityMessage(Exception ex) {
        String errorMsg = null;
        if (ex instanceof ServiceException || ex instanceof BaseException)
            errorMsg = ex.getMessage();
        return StringUtils.defaultIfEmpty(errorMsg, "服务器出错了!");
    }
}
