package com.ofweek.live.core.modules.room.dao;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.room.entity.RoomReviewData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface RoomReviewDataDao extends CrudDao<RoomReviewData> {

	List<RoomReviewData>  findReviewListByMediaId(@Param("mediaId") String mediaId);

}