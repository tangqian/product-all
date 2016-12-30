package com.thinkgem.jeesite.modules.live.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.room.dao.RoomDataPublicDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomDataPublic;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomDataPublicService extends AbstractModuleService<RoomDataPublicDao, RoomDataPublic> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomDataPublic entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(sequenceService.nextIntString("LiveRoomDataPublic"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	public RoomDataPublic findRoomSpeakerDataForConditions(String id, String roomId) {
		return dao.findRoomSpeakerDataForConditions(id, roomId);
	}
	
	public RoomDataPublic findRoomDataPublicForConditions(String sourceId, String roomId) {
		return dao.findRoomDataPublicForConditions(sourceId, roomId);
	}
    
}
