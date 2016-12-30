package com.thinkgem.jeesite.modules.live.speaker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.entity.LiveUser;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.base.service.LiveUserService;
import com.thinkgem.jeesite.modules.live.speaker.dao.SpeakerDao;
import com.thinkgem.jeesite.modules.live.speaker.entity.Speaker;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class SpeakerService extends AbstractModuleService<SpeakerDao, Speaker> {

	@Autowired
	private LiveUserService liveUserService;

	@Override
	@Transactional(readOnly = false)
	public void save(Speaker entity) {
		if (StringUtils.isBlank(entity.getId())) {
			setIndustryIds(entity);
			entity.setId(liveUserService.saveSpeakerLiveUser(entity));
			entity.preInsert();
			dao.insert(entity);
		} else {
			setIndustryIds(entity);
			entity.preUpdate();
			liveUserService.saveSpeakerLiveUser(entity);
			dao.update(entity);
		}
	}
	
	private void setIndustryIds(Speaker entity) {
		if (entity == null)
			return;
		StringBuffer buffer = new StringBuffer();
		List<String> industriesIdList = entity.getIndustriesIdList();
		if (industriesIdList != null) {
			for (int i = 0, len = industriesIdList.size(); i < len; i++) {
				buffer.append(industriesIdList.get(i)).append(",");
			}
			buffer.insert(0, ",");
			entity.setIndustry(buffer.toString());
		}
	}

	@Transactional(readOnly = false)
	public void delete(Speaker entity) {
		dao.delete(entity);
		liveUserService.delete(new LiveUser(entity.getId()));
	}

	public Speaker getLiveSpeakerForId(String id) {
		return dao.getLiveSpeakerForId(id);
	}
	
	public Speaker getLiveSpeakerForConditions(String company, String id) {
		return dao.getLiveSpeakerForConditions(company, id);
	}
}
