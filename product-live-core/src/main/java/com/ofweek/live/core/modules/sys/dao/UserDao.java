package com.ofweek.live.core.modules.sys.dao;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户DAO接口
 *
 * @author tangqian
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {

    /**
     * 查询单个用户
     *
     * @param account 用户名
     * @param type    用户类型
     * @return
     */
    public User getByAccount(@Param("account") String account, @Param("type") Integer type);

    /**
     * 根据账号获取主播或客服
     *
     * @param account
     * @return
     */
    public List<User> getSpeakerOrWaiter(@Param("account") String account);

}
