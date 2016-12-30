package com.ofweek.live.core.modules.speaker.dao;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerData;

/**
 * @author tangqian
 */
@MyBatisDao
public interface SpeakerDataRecycleDao extends CrudDao<SpeakerData> {
	
	void insertFormData2DataRecycle(@Param("dataIds") String[] dataIds);

}