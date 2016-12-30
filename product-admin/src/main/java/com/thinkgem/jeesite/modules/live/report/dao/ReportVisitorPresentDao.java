package com.thinkgem.jeesite.modules.live.report.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.report.entity.ReportVisitorPresent;

/**
 * @author tangqian
 */
@MyBatisDao
public interface ReportVisitorPresentDao extends CrudDao<ReportVisitorPresent> {

    Integer countRoomVisitorForRoomId(@Param("roomId") String roomId);
}