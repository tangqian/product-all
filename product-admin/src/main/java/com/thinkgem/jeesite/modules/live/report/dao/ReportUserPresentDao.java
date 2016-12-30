package com.thinkgem.jeesite.modules.live.report.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.report.entity.ReportUserPresent;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface ReportUserPresentDao extends CrudDao<ReportUserPresent> {
	
	Integer countPresentAudienceForRoomId(@Param("roomId") String roomId);

}