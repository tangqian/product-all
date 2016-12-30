package com.ofweek.live.core.modules.room.dao;

import java.util.Date;
import java.util.List;


import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.dto.RoomDto;
import com.ofweek.live.core.modules.room.entity.Room;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface RoomDao extends CrudDao<Room> {

	List<Room> findUnendIndexList(Room room);

	List<Room> findEndIndexList(Room room);

	List<Room> findAllTopList(Room room);

	List<Room> findTrailerList();

	List<Room> findReviewList(Room room);

	List<Room> findToCloseList(@Param("limitTime") Date limitTime);

	List<Room> findBySpeakerDataIds(@Param("speakerDataIds") String[] SpeakerDataIds);

	List<Room> findBySpeakerVideoIds(@Param("speakerVideoIds") String[] speakerVideoIds);

    List<RoomDto> findIndexLiveList(@Param("status") Integer status, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    List<Room> findHeaderLiveList();

	void updatePv(Room room);
}