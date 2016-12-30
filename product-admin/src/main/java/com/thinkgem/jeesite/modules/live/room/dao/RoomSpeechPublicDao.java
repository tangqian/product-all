package com.thinkgem.jeesite.modules.live.room.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomSpeechPublic;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface RoomSpeechPublicDao extends CrudDao<RoomSpeechPublic> {
	
	RoomSpeechPublic findRoomSpeakerSpeechForConditions(@Param("id") String id, @Param("roomId") String roomId);
	
	RoomSpeechPublic findRoomSpeechPublicForConditions(@Param("sourceId") String sourceId, @Param("roomId") String roomId);

	
}