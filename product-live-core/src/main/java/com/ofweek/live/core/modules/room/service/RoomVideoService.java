package com.ofweek.live.core.modules.room.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.room.dao.RoomVideoDao;
import com.ofweek.live.core.modules.room.entity.RoomVideo;
import com.ofweek.live.core.modules.speaker.dao.SpeakerVideoDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerVideo;
import com.ofweek.live.core.modules.sys.enums.AuditStatusEnum;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomVideoService extends CrudService<RoomVideoDao, RoomVideo> {
	
	@Autowired
	private RoomVideoPublicService roomVideoPublicService;
	
	@Autowired
	private SpeakerVideoDao speakerVideoDao;
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomVideo entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("LiveRoomVideo"));
			entity.preInsert();
			dao.insert(entity);
			insertRoomVideoPub(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	public List<RoomVideo> findRoomVideoForRoomId(String roomId) {
		return dao.findRoomVideoForRoomId(roomId, UserUtils.getUser().getId());
	}
	
	private void insertRoomVideoPub(RoomVideo entity) 
	{
		SpeakerVideo speakerVideo = speakerVideoDao.get(entity.getSourceId());
		if (speakerVideo != null)
		{
			if (AuditStatusEnum.isPass(speakerVideo.getStatus()))
			{
				RoomVideo roomSpeechPub = roomVideoPublicService.getRoomVideoPub(entity);
				if (roomSpeechPub == null)
				{
					entity.setName(speakerVideo.getName());
					entity.setFileId(speakerVideo.getFileId());
					roomVideoPublicService.insert(entity);
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(RoomVideo entity)
	{
		dao.delete(entity);
		roomVideoPublicService.deleteRoomVideoPub(entity);
	}
    
}
