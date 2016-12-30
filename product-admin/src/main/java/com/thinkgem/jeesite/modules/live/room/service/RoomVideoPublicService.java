package com.thinkgem.jeesite.modules.live.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.room.dao.RoomVideoPublicDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomVideoPublic;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomVideoPublicService extends AbstractModuleService<RoomVideoPublicDao, RoomVideoPublic> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomVideoPublic entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(sequenceService.nextIntString("LiveRoomVideoPublic"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	public RoomVideoPublic findRoomSpeakerVideoForConditions(String id, String roomId) {
		return dao.findRoomSpeakerVideoForConditions(id, roomId);
	}
	
	public RoomVideoPublic findRoomVideoPublicForConditions(String sourceId, String roomId) {
		return dao.findRoomVideoPublicForConditions(sourceId, roomId);
	}
    
}
