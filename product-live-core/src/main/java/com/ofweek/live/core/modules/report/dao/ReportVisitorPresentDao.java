package com.ofweek.live.core.modules.report.dao;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.report.entity.ReportVisitorPresent;

/**
 * @author tangqian
 */
@MyBatisDao
public interface ReportVisitorPresentDao extends CrudDao<ReportVisitorPresent> {

    ReportVisitorPresent getByRoomVisitor(@Param("roomId") String roomId, @Param("visitorId") String visitorId);
    
    Integer countRoomVisitorForRoomId(@Param("roomId") String roomId);
}