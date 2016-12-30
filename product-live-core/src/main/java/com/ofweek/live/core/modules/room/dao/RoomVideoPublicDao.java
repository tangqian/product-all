package com.ofweek.live.core.modules.room.dao;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomVideo;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface RoomVideoPublicDao extends CrudDao<RoomVideo> {

    void deleteDatas(@Param("srcIds") String[] srcIds);
    
	RoomVideo getRoomVideoPub(RoomVideo roomVideo);
	
	void deleteRoomVideoPub(RoomVideo roomVideo);
}