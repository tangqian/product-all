package com.ofweek.live.core.modules.room.dao;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomSpeech;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface RoomSpeechPublicDao extends CrudDao<RoomSpeech> {
	
	void deleteBySourceId(@Param("speechId") String speechId);
	
	RoomSpeech getRoomSpeechPub(RoomSpeech roomSpeech);
	
	void deleteRoomSpeechPub(RoomSpeech roomSpeech);
	
}