package com.ofweek.live.core.modules.sys.utils;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;

import com.ofweek.live.core.common.utils.SpringContextHolder;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.entity.Room;

/**
 * 直播工具类
 * 
 * @author tangqian
 * 
 */
public class RoomUtils {

	private static RoomDao roomDao = SpringContextHolder.getBean(RoomDao.class);

	private static final Integer INDEX = 0;

	public static List<Room> getRoomList() {
		Room room = new Room();
		room.setSpeakerId(UserUtils.getUser().getId());
		return roomDao.findList(room);
	}

	public static String getRoomListFirst() {
		if (CollectionUtils.isEmpty(getRoomList())) {
			return "";
		}
		return getRoomList().get(INDEX).getId();
	}
	
	public static Room getRoomById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		return roomDao.get(id);
	}

}
