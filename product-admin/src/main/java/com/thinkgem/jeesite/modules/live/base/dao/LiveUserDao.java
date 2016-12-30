package com.thinkgem.jeesite.modules.live.base.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.base.entity.LiveUser;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface LiveUserDao extends CrudDao<LiveUser> {

	LiveUser getLiveUserForAccount(@Param("account") String account, @Param("type") Integer type);

}