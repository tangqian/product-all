package com.thinkgem.jeesite.modules.live.speaker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.speaker.dao.SpeakerWaiterDao;
import com.thinkgem.jeesite.modules.live.speaker.entity.SpeakerWaiter;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class SpeakerWaiterService extends AbstractModuleService<SpeakerWaiterDao, SpeakerWaiter> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(SpeakerWaiter entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(sequenceService.nextIntString("LiveSpeakerWaiter"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
}
