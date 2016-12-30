package com.ofweek.live.core.modules.speaker.dao;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerVideo;

/**
 * @author tangqian
 */
@MyBatisDao
public interface SpeakerVideoRecycleDao extends CrudDao<SpeakerVideo> {
	
	void insertFormVideo2VideoRecycle(@Param("videoIds") String[] videoIds);

}