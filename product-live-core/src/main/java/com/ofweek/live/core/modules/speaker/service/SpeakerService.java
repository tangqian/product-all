package com.ofweek.live.core.modules.speaker.service;

import com.ofweek.live.core.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDao;
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class SpeakerService extends CrudService<SpeakerDao, Speaker> {

	@Autowired
	private UserService userService;
	
	@Override
	@Transactional(readOnly = false)
	public void save(Speaker entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("LiveSpeaker"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			userService.updateUserEmail(entity);
			dao.update(entity);
		}
	}
	
	public Speaker getSpeakerForId() {
		return dao.getSpeakerForId(UserUtils.getUser().getId());
	}

	public Speaker getLiveSpeakerForId() {
		return dao.getLiveSpeakerForId(UserUtils.getUser().getId());
	}
    
}
