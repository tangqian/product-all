package com.ofweek.live.core.modules.room.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.room.dao.RoomDataDao;
import com.ofweek.live.core.modules.room.entity.RoomData;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDataDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerData;
import com.ofweek.live.core.modules.sys.enums.AuditStatusEnum;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomDataService extends CrudService<RoomDataDao, RoomData> {
	
	@Autowired
	private RoomDataPublicService roomDataPublicService;
	
	@Autowired
	private SpeakerDataDao speakerDataDao;
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomData entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("LiveRoomData"));
			entity.preInsert();
			dao.insert(entity);
			insertRoomDataPub(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
			updateRoomDataPubSort(entity);
		}
	}
	
	public List<RoomData> findRoomDataForRoomId(String roomId) {
		return dao.findRoomDataForRoomId(roomId, UserUtils.getUser().getId());
	}

	private void updateRoomDataPubSort(RoomData entity) {
		roomDataPublicService.updateSort(entity);
	}
	
	private void insertRoomDataPub(RoomData entity)
	{
		SpeakerData speakerData = speakerDataDao.get(entity.getSourceId());
		if (speakerData != null)
		{
			if (AuditStatusEnum.isPass(speakerData.getStatus()))
			{
				RoomData roomDataPub = roomDataPublicService.getRoomDataPub(entity);
				if (roomDataPub == null)
				{
					entity.setName(speakerData.getName());
					entity.setFileId(speakerData.getFileId());
					roomDataPublicService.insert(entity);
				}
			}
		}
		
	}
	
	@Transactional(readOnly = false)
	public void delete(RoomData entity)
	{
		dao.delete(entity);
		roomDataPublicService.deleteRoomDataPub(entity);
	}
	
}
