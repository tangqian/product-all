package com.thinkgem.jeesite.modules.live.room.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomReviewData;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface RoomReviewDataDao extends CrudDao<RoomReviewData> {
	
	List<RoomReviewData> findReviewDataList(RoomReviewData entity);
	
	Integer getMaxSort();

}