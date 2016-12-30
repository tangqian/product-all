package com.thinkgem.jeesite.modules.live.base.dto;


/**
 * http接口基本响应对象
 *
 * @author tangqian
 */
public class HttpBaseResponse {

	/**
	 * 0表示成功，其它值表示失败
	 */
    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer status) {
        this.code = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
