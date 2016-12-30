package com.ofweek.live.web.api.common;

/**
 * Created by tangqian on 2016/9/5.
 */
public class HttpCommonResponse<T> extends HttpBaseResponse {

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private T data;
}
