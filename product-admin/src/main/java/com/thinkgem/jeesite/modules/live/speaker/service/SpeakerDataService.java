package com.thinkgem.jeesite.modules.live.speaker.service;

import javax.annotation.Resource;

import com.thinkgem.jeesite.modules.live.base.enums.AuditStatusEnum;
import com.thinkgem.jeesite.modules.live.room.service.RoomDataPublicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.room.dao.RoomDataDao;
import com.thinkgem.jeesite.modules.live.speaker.dao.SpeakerDataDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomData;
import com.thinkgem.jeesite.modules.live.room.entity.RoomDataPublic;
import com.thinkgem.jeesite.modules.live.speaker.entity.SpeakerData;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class SpeakerDataService extends AbstractModuleService<SpeakerDataDao, SpeakerData> {

	@Resource
	private RoomDataPublicService roomDataPublicService;

	@Resource
	private RoomDataDao roomDataDao;

	@Override
	@Transactional(readOnly = false)
	public void save(SpeakerData entity) {
		if (StringUtils.isBlank(entity.getId())) {
			entity.setId(sequenceService.nextIntString("LiveSpeakerData"));
			entity.preInsert();
			dao.insert(entity);
		} else {
			entity.preUpdate();
			dao.update(entity);
			syncRoomDataPublic(entity);
		}
	}

	private void syncRoomDataPublic(SpeakerData entity) {
		if (entity == null || entity.getStatus() == AuditStatusEnum.NOPASS.getCode())
			return;
		RoomDataPublic roomDataPublic = roomDataPublicService.findRoomDataPublicForConditions(entity.getId(), entity.getRoomId());
		if (roomDataPublic == null) {// 新增操作
			roomDataPublic = roomDataPublicService.findRoomSpeakerDataForConditions(entity.getId(), entity.getRoomId());
		} else {// 更新操作
			RoomData roomData = roomDataDao.getByRoomSourceId(entity.getRoomId(), entity.getId());
			roomDataPublic.setName(entity.getName());
			roomDataPublic.setFileId(entity.getFileId());
			roomDataPublic.setSort(roomData.getSort());
		}
		roomDataPublicService.save(roomDataPublic);
	}

}
