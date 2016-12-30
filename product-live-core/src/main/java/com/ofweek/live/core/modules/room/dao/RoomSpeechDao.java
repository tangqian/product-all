package com.ofweek.live.core.modules.room.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomSpeech;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface RoomSpeechDao extends CrudDao<RoomSpeech> {
	
	List<RoomSpeech> findRoomSpeechForRoomId(@Param("roomId") String roomId, @Param("speakerId") String speakerId);
	
	void deleteBySourceId(@Param("speechId") String speechId);
	
	Integer countOngoing(@Param("status") Integer status, @Param("speakerId") String speakerId, @Param("speechId") String speechId);
	
}