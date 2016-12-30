package com.thinkgem.jeesite.modules.live.speaker.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.speaker.entity.Speaker;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface SpeakerDao extends CrudDao<Speaker> {
	
	Speaker getLiveSpeakerForId(String id);
	
	Speaker getLiveSpeakerForConditions(@Param("company") String company, @Param("id") String id);
	
}