package com.ofweek.live.web.api.common;

/**
 * http接口基本响应对象
 *
 * @author tangqian
 */
public class HttpBaseResponse {

    private CodeEnum code;

    private String message;

    public HttpBaseResponse() {
        code = CodeEnum.SUCCESS;
    }

    public int getCode() {
        return code.code();
    }

    public void setCode(CodeEnum status) {
        this.code = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
