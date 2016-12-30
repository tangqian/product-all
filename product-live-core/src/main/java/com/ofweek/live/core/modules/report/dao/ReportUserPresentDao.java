package com.ofweek.live.core.modules.report.dao;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.report.entity.ReportUserPresent;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface ReportUserPresentDao extends CrudDao<ReportUserPresent> {

    ReportUserPresent getByRoomUser(@Param("roomId") String roomId, @Param("userId") String userId);
    
    Integer countAllAudienceForRoomId(@Param("roomId") String roomId);
}