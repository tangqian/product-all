package com.ofweek.live.core.modules.speaker.dao;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerSpeech;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface SpeakerSpeechRecycleDao extends CrudDao<SpeakerSpeech> {
	
	void insertFormSpeech2SpeechRecycle(@Param("id") String id);
	
}