package com.ofweek.live.core.modules.speaker.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerData;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface SpeakerDataDao extends CrudDao<SpeakerData> {

	void delSpeakerDataForIds(@Param("dataIds") String[] dataIds);
	
	SpeakerData getSpeakerDataForObj(SpeakerData speakerData);
	
	List<SpeakerData> findSpeakerDataForIds(@Param("dataIds") String[] dataIds);
	
	SpeakerData getSpeakerDataForName(@Param("name") String name, @Param("speakerId") String speakerId);
	
	void insertFormDataRecycle2Data(SpeakerData speakerData);
	
	void delSpeakerDataForObj(SpeakerData speakerData);
	
	List<SpeakerData> findSpeakerDataForConditions(@Param("dataIds") String dataIds, @Param("speakerId") String speakerId);
	
	Integer countSpeakerData(@Param("speakerId") String speakerId);

}