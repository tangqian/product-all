package com.ofweek.live.core.modules.audience.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.audience.entity.AudienceRegister;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface AudienceRegisterDao extends CrudDao<AudienceRegister> {
	
	List<AudienceRegister> findSendEmailAudienceRegister();

    AudienceRegister getByUserRoomId(@Param("userId")String userId, @Param("roomId")String roomId);
	
}