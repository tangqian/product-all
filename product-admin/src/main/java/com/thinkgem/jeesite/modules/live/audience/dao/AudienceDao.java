package com.thinkgem.jeesite.modules.live.audience.dao;

import java.util.List;

import com.thinkgem.jeesite.modules.live.audience.entity.Audience;
import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.audience.vo.AudienceVo;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface AudienceDao extends CrudDao<Audience> {
	
	List<AudienceVo> findAudienceForIds(@Param("ids") List<String> ids);
	
	List<Audience> findAudienceForRoomId(Audience entity);
	
}