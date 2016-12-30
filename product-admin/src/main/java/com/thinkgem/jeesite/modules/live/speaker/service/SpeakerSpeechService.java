package com.thinkgem.jeesite.modules.live.speaker.service;

import javax.annotation.Resource;

import com.thinkgem.jeesite.modules.live.base.enums.AuditStatusEnum;
import com.thinkgem.jeesite.modules.live.room.service.RoomSpeechPublicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.speaker.dao.SpeakerSpeechDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomSpeechPublic;
import com.thinkgem.jeesite.modules.live.speaker.entity.SpeakerSpeech;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class SpeakerSpeechService extends AbstractModuleService<SpeakerSpeechDao, SpeakerSpeech> {
	
	@Resource
	private RoomSpeechPublicService roomSpeechPublicService;
	
	@Override
	@Transactional(readOnly = false)
	public void save(SpeakerSpeech entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(sequenceService.nextIntString("LiveSpeakerSpeech"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
			syncRoomSpeechPublic(entity);
		}
	}
	
	private void syncRoomSpeechPublic(SpeakerSpeech entity) {
		if (entity == null || entity.getStatus() == AuditStatusEnum.NOPASS.getCode()) return ;
		RoomSpeechPublic roomSpeechPublic = roomSpeechPublicService.findRoomSpeechPublicForConditions(entity.getId(), entity.getRoomId());
		if (roomSpeechPublic == null) {
			roomSpeechPublic = roomSpeechPublicService.findRoomSpeakerSpeechForConditions(entity.getId(), entity.getRoomId());
		} else {
			roomSpeechPublic.setName(entity.getName());
			roomSpeechPublic.setFileId(entity.getFileId());		
		}
		roomSpeechPublicService.save(roomSpeechPublic);
	}
    
    
}
