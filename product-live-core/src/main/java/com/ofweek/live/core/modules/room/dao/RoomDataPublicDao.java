package com.ofweek.live.core.modules.room.dao;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomData;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface RoomDataPublicDao extends CrudDao<RoomData> {
	
	void updateSort(RoomData roomData);

	void deleteDatas(@Param("srcIds") String[] srcIds);
	
	RoomData getRoomDataPub(RoomData roomData);
	
	void deleteRoomDataPub(RoomData roomData);
}