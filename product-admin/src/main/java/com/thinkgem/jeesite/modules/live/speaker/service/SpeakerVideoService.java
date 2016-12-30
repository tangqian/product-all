package com.thinkgem.jeesite.modules.live.speaker.service;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.enums.AuditStatusEnum;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.room.service.RoomVideoPublicService;
import com.thinkgem.jeesite.modules.live.speaker.dao.SpeakerVideoDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomVideoPublic;
import com.thinkgem.jeesite.modules.live.speaker.entity.SpeakerVideo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author tangqian
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class SpeakerVideoService extends AbstractModuleService<SpeakerVideoDao, SpeakerVideo> {

    @Resource
    private RoomVideoPublicService roomVideoPublicService;

    @Override
    @Transactional(readOnly = false)
    public void save(SpeakerVideo entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(sequenceService.nextIntString("LiveSpeakerVideo"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
            syncRoomVideoPublic(entity);
        }
    }

    private void syncRoomVideoPublic(SpeakerVideo entity) {
        if (entity == null || entity.getStatus() == AuditStatusEnum.NOPASS.getCode()) return;
        RoomVideoPublic roomVideoPublic = roomVideoPublicService.findRoomVideoPublicForConditions(entity.getId(), entity.getRoomId());
        if (roomVideoPublic == null) {
            roomVideoPublic = roomVideoPublicService.findRoomSpeakerVideoForConditions(entity.getId(), entity.getRoomId());
        } else {
            roomVideoPublic.setName(entity.getName());
            roomVideoPublic.setFileId(entity.getFileId());
            roomVideoPublic.setDetail(entity.getDetail());
            roomVideoPublic.setCoverId(entity.getCoverId());
        }
        roomVideoPublicService.save(roomVideoPublic);
    }

}
