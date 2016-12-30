package com.thinkgem.jeesite.modules.live.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.room.dao.RoomSpeechPublicDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomSpeechPublic;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomSpeechPublicService extends AbstractModuleService<RoomSpeechPublicDao, RoomSpeechPublic> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomSpeechPublic entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(sequenceService.nextIntString("LiveRoomSpeechPublic"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	public RoomSpeechPublic findRoomSpeakerSpeechForConditions(String id, String roomId) {
		return dao.findRoomSpeakerSpeechForConditions(id, roomId);
	}
	
	public RoomSpeechPublic findRoomSpeechPublicForConditions(String sourceId, String roomId) {
		return dao.findRoomSpeechPublicForConditions(sourceId, roomId);
	}
    
}
