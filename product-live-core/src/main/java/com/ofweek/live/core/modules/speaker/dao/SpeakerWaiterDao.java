package com.ofweek.live.core.modules.speaker.dao;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerWaiter;

/**
 * @author tangqian
 */
@MyBatisDao
public interface SpeakerWaiterDao extends CrudDao<SpeakerWaiter> {

    SpeakerWaiter getBySpeaker(@Param("id") String id, @Param("speakerId") String speakerId);
    
    SpeakerWaiter getSpeakerWaiterForId(@Param("id") String id, @Param("speakerId") String speakerId);
}