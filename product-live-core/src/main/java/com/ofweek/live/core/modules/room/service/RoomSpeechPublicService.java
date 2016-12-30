package com.ofweek.live.core.modules.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.modules.room.dao.RoomSpeechPublicDao;
import com.ofweek.live.core.modules.room.entity.RoomSpeech;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomSpeechPublicService extends CrudService<RoomSpeechPublicDao, RoomSpeech> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomSpeech entity) {
		throw new UnsupportedOperationException();
	}
	
	public RoomSpeech getRoomSpeechPub(RoomSpeech roomSpeech) {
		return dao.getRoomSpeechPub(roomSpeech);
	}
	
	public void insert(RoomSpeech entity) {
		entity.setId(SequenceUtils.getNextString("LiveRoomSpeechPublic"));
		entity.preInsert();
		dao.insert(entity);
	}
	
	public void deleteRoomSpeechPub(RoomSpeech entity) {
		dao.deleteRoomSpeechPub(entity);
	}
    
}
