package com.ofweek.live.core.modules.speaker.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerSpeech;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface SpeakerSpeechDao extends CrudDao<SpeakerSpeech> {
	
	void delSpeakerSpeechForIds(@Param("speechIds") String[] speechIds);
	
	SpeakerSpeech getSpeakerSpeechForObj(SpeakerSpeech speakerSpeech);
	
	void insertFormSpeechRecycle2Speech(SpeakerSpeech speakerSpeech);
	
	void delSpeakerSpeechForObj(SpeakerSpeech speakerSpeech);
	
	List<SpeakerSpeech> findSpeakerSpeechForConditions(@Param("speechIds") String speechIds, @Param("speakerId") String speakerId);
	
}