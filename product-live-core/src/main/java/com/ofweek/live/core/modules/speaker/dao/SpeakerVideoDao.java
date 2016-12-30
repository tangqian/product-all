package com.ofweek.live.core.modules.speaker.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerVideo;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface SpeakerVideoDao extends CrudDao<SpeakerVideo> {
	
	void delSpeakerVideoForIds(@Param("videoIds") String[] videoIds);
	
	SpeakerVideo getSpeakerVideoForObj(SpeakerVideo speakerVideo);
	
	List<SpeakerVideo> findSpeakerVideoForIds(@Param("videoIds") String[] videoIds);
	
	SpeakerVideo getSpeakerVideoForName(@Param("name") String name, @Param("speakerId") String speakerId);
	
	void insertFormVideoRecycle2Video(SpeakerVideo speakerVideo);
	
	void delSpeakerVideoForObj(SpeakerVideo speakerVideo);
	
	List<SpeakerVideo> findSpeakerVideoForConditions(@Param("videoIds") String videoIds, @Param("speakerId") String speakerId);

	List<SpeakerVideo> findAllRoomVideoList();
	
	Integer countSpeakerVideo(@Param("speakerId") String speakerId);
}