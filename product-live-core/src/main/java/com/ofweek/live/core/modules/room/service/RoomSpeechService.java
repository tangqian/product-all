package com.ofweek.live.core.modules.room.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.room.dao.RoomSpeechDao;
import com.ofweek.live.core.modules.room.entity.RoomSpeech;
import com.ofweek.live.core.modules.room.enums.RoomStatusEnum;
import com.ofweek.live.core.modules.speaker.dao.SpeakerSpeechDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerSpeech;
import com.ofweek.live.core.modules.sys.enums.AuditStatusEnum;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomSpeechService extends CrudService<RoomSpeechDao, RoomSpeech> {
	
	@Autowired
	private RoomSpeechPublicService roomSpeechPublicService;
	
	@Autowired
	private SpeakerSpeechDao speakerSpeechDao;
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomSpeech entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("LiveRoomSpeech"));
			entity.preInsert();
			dao.insert(entity);
			insertRoomSpeechPub(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	public List<RoomSpeech> findRoomSpeechForRoomId(String roomId) {
		return dao.findRoomSpeechForRoomId(roomId, UserUtils.getUser().getId());
	}
	
	public boolean countOngoing(String speechId)
	{
		return dao.countOngoing(RoomStatusEnum.HOLDING.getCode(), UserUtils.getUser().getId(), speechId) > 0 ? true : false;
	}
    
	private void insertRoomSpeechPub(RoomSpeech entity) 
	{
		SpeakerSpeech speakerSpeech = speakerSpeechDao.get(entity.getSourceId());
		if (speakerSpeech != null)
		{
			if (AuditStatusEnum.isPass(speakerSpeech.getStatus()))
			{
				RoomSpeech roomSpeechPub = roomSpeechPublicService.getRoomSpeechPub(entity);
				if (roomSpeechPub == null)
				{
					entity.setName(speakerSpeech.getName());
					entity.setFileId(speakerSpeech.getFileId());
					roomSpeechPublicService.insert(entity);
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(RoomSpeech entity)
	{
		dao.delete(entity);
		roomSpeechPublicService.deleteRoomSpeechPub(entity);
	}
}
