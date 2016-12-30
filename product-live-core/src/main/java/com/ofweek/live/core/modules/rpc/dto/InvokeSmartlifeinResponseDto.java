package com.ofweek.live.core.modules.rpc.dto;

/**
 * Created by Administrator on 2016/11/14.
 */
public class InvokeSmartlifeinResponseDto {

    //{"status":1,"msg":"成功"}
    //{"status":0,"msg":"失败"}
    private int status;

    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
