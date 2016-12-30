package com.thinkgem.jeesite.modules.live.room.utils;

import java.util.List;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.live.room.dao.RoomDao;
import com.thinkgem.jeesite.modules.live.room.entity.Room;

public class RoomUtils {

	private static RoomDao roomDao = SpringContextHolder.getBean(RoomDao.class);

	public static List<Room> getRoomList() {
		List<Room> notOvers = Lists.newArrayList();
		for (Room room : roomDao.findList(new Room())) {
			notOvers.add(room);
		}
		return notOvers;
	}
	
	public static Room getRoom(String id) {
		return roomDao.get(id);
	}

}
