package com.ofweek.live.core.modules.report.dao;

import org.apache.ibatis.annotations.Param;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.report.entity.ReportDataDownload;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface ReportDataDownloadDao extends CrudDao<ReportDataDownload> {

    ReportDataDownload getOne(ReportDataDownload entity);
    
	Integer countDownloadAudienceRoomId(@Param("roomId") String roomId, @Param("type") Integer type);
	
}