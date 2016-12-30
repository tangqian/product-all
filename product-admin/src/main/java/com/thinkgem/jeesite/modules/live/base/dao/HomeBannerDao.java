package com.thinkgem.jeesite.modules.live.base.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.live.base.entity.HomeBanner;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface HomeBannerDao extends CrudDao<HomeBanner> {
	
}