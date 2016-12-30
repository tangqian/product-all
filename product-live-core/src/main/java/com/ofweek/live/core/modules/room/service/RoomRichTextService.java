package com.ofweek.live.core.modules.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

import com.ofweek.live.core.modules.room.dao.RoomRichTextDao;
import com.ofweek.live.core.modules.room.entity.RoomRichText;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomRichTextService extends CrudService<RoomRichTextDao, RoomRichText> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(RoomRichText entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("LiveRoomRichText"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}

	public List<RoomRichText> findPage(String roomId, Date endTime, int offset, int limit) {
		return dao.findRange(roomId, endTime, offset, limit);
	}
}
