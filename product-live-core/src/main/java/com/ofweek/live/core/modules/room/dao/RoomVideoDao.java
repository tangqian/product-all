package com.ofweek.live.core.modules.room.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomVideo;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface RoomVideoDao extends CrudDao<RoomVideo> {
	
	List<RoomVideo> findRoomVideoForRoomId(@Param("roomId") String roomId, @Param("speakerId") String speakerId);

	void deleteDatas(@Param("srcIds") String[] srcIds);
}