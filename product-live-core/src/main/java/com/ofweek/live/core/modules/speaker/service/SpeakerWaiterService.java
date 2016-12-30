package com.ofweek.live.core.modules.speaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.speaker.dao.SpeakerWaiterDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerWaiter;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class SpeakerWaiterService extends CrudService<SpeakerWaiterDao, SpeakerWaiter> {
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional(readOnly = false)
	public void save(SpeakerWaiter entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(userService.saveWaiterUser(entity));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			userService.saveWaiterUser(entity);
			dao.update(entity);
		}
	}
	
	public boolean isExist(String id, String speakerId){
		SpeakerWaiter waiter = dao.getBySpeaker(id, speakerId);
		return  waiter != null;
	}
	
	public SpeakerWaiter getSpeakerWaiterForId(String id) {
		return dao.getSpeakerWaiterForId(id, UserUtils.getUser().getId());
	}
    
	@Transactional(readOnly = false, rollbackFor = {Exception.class})
	public void delete(SpeakerWaiter entity) {
		dao.delete(entity);
		userService.delete(new User(entity.getId()));
	}
}
