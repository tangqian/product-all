package com.ofweek.live.core.modules.room.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomData;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface RoomDataDao extends CrudDao<RoomData> {
	
	List<RoomData> findRoomDataForRoomId(@Param("roomId") String roomId, @Param("speakerId") String speakerId);

	void deleteDatas(@Param("srcIds") String[] srcIds);
	
}