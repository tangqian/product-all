package com.ofweek.live.core.modules.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

import com.ofweek.live.core.modules.room.dao.RoomChatReceiverDao;
import com.ofweek.live.core.modules.room.entity.RoomChatReceiver;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomChatReceiverService extends CrudService<RoomChatReceiverDao, RoomChatReceiver> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomChatReceiver entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("LiveRoomChatReceiver"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
    
}
