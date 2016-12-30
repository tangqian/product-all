package com.ofweek.live.core.modules.sys.entity;

/**
 * 直播公司用户信息接口，包括公司和客服
 * Created by Administrator on 2016/8/25.
 */
public interface EnterpriseUser {

    /**
     * 获取当前用户所属公司id
     * @return
     */
    String getSpeakerId();

    /**
     * 获取当前公司名称
     * @return
     */
    String getCompany();
}
