package com.thinkgem.jeesite.modules.live.base.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.dao.LiveUserDao;
import com.thinkgem.jeesite.modules.live.base.entity.LiveUser;
import com.thinkgem.jeesite.modules.live.base.enums.LiveUserStatusEnum;
import com.thinkgem.jeesite.modules.live.base.enums.LiveUserTypeEnum;
import com.thinkgem.jeesite.modules.live.speaker.entity.Speaker;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class LiveUserService extends AbstractModuleService<LiveUserDao, LiveUser> {

	@Override
	@Transactional(readOnly = false)
	public void save(LiveUser entity) {
		if (StringUtils.isBlank(entity.getId())) {
			entity.setId(sequenceService.nextIntString("LiveUser"));
			entity.preInsert();
			dao.insert(entity);
		} else {
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	@Transactional(readOnly = false)
	private String saveLiveUser(LiveUser entity) {
		if (StringUtils.isBlank(entity.getId())) {
			String id = sequenceService.nextIntString("LiveUser");
			entity.setId(id);
			entity.preInsert();
			dao.insert(entity);
			return id;
		} else {
			entity.preUpdate();
			dao.update(entity);
			return entity.getId();
		}
	}
	
	public String saveSpeakerLiveUser(Speaker entity) {
		LiveUser user = new LiveUser();
		user.setId(entity.getId());
		user.setType(LiveUserTypeEnum.SPEAKER.getCode());
		user.setStatus(LiveUserStatusEnum.NORMAL.getCode());
		user.setAccount(entity.getAccount());
		if (StringUtils.isBlank(entity.getEmail())) 
			user.setEmail(StringUtils.EMPTY);
		else
			user.setEmail(entity.getEmail());
		if (StringUtils.isBlank(entity.getPassword()))
			user.setPassword(StringUtils.EMPTY);
		else
			user.setPassword(SystemService
					.entryptPassword(entity.getPassword()));
		return saveLiveUser(user);
	}
	
	public LiveUser getLiveUserForAccount(String account, Integer type) {
		return dao.getLiveUserForAccount(account, type);
	}

}
