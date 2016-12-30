package com.ofweek.live.core.modules.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.modules.room.dao.RoomDataPublicDao;
import com.ofweek.live.core.modules.room.entity.RoomData;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomDataPublicService extends CrudService<RoomDataPublicDao, RoomData> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomData entity) {
		throw new UnsupportedOperationException();
	}
	
	public void updateSort(RoomData roomData) {
		dao.updateSort(roomData);
	}
	
	public RoomData getRoomDataPub(RoomData roomData) {
		return dao.getRoomDataPub(roomData);
	}
	
	public void insert(RoomData entity) {
		entity.setId(SequenceUtils.getNextString("LiveRoomDataPublic"));
		entity.preInsert();
		dao.insert(entity);
	}
	
	public void deleteRoomDataPub(RoomData entity) {
		dao.deleteRoomDataPub(entity);
	}
}
