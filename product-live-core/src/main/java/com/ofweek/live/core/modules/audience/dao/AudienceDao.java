package com.ofweek.live.core.modules.audience.dao;

import java.util.List;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.audience.entity.Audience;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface AudienceDao extends CrudDao<Audience> {
	
	/**
	 * 观众数据->下载资料观众列表
	 * 
	 * @param audience
	 * @return
	 */
	List<Audience> findDownloadAudienceList(Audience audience);
	
	/**
	 * 观众数据->聊天观众列表
	 * 
	 * @param audience
	 * @return
	 */
	List<Audience> findAudienceChatList(Audience audience);
	
	
}