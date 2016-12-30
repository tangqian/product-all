package com.ofweek.live.core.modules.room.dao;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomChatReceiver;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface RoomChatReceiverDao extends CrudDao<RoomChatReceiver> {
	
}