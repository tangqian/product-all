package com.thinkgem.jeesite.modules.live.speaker.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.speaker.entity.SpeakerData;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface SpeakerDataDao extends CrudDao<SpeakerData> {
	
}