package com.thinkgem.jeesite.modules.live.room.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomDataPublic;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface RoomDataPublicDao extends CrudDao<RoomDataPublic> {

	RoomDataPublic findRoomSpeakerDataForConditions(@Param("id") String id, @Param("roomId") String roomId);

	RoomDataPublic findRoomDataPublicForConditions(@Param("sourceId") String sourceId, @Param("roomId") String roomId);

}