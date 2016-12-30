package com.thinkgem.jeesite.modules.live.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.room.dao.RoomBlacklistDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomBlacklist;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomBlacklistService extends AbstractModuleService<RoomBlacklistDao, RoomBlacklist> {

	@Override
	@Transactional(readOnly = false)
	public void save(RoomBlacklist entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(sequenceService.nextIntString("LiveRoomBlacklist"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	public RoomBlacklist findBlacklistForObj(RoomBlacklist entity) {
		return dao.findBlacklistForObj(entity);
	}
    
}
