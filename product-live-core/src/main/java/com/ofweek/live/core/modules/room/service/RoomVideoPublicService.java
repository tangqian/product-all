package com.ofweek.live.core.modules.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.modules.room.dao.RoomVideoPublicDao;
import com.ofweek.live.core.modules.room.entity.RoomVideo;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomVideoPublicService extends CrudService<RoomVideoPublicDao, RoomVideo> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomVideo entity) {
		throw new UnsupportedOperationException();
	}
    
	public RoomVideo getRoomVideoPub(RoomVideo roomVideo) {
		return dao.getRoomVideoPub(roomVideo);
	}
	
	public void insert(RoomVideo entity) {
		entity.setId(SequenceUtils.getNextString("LiveRoomVideoPublic"));
		entity.preInsert();
		dao.insert(entity);
	}
	
	public void deleteRoomVideoPub(RoomVideo entity) {
		dao.deleteRoomVideoPub(entity);
	}
}
