package com.ofweek.live.core.modules.sys.dao;

import java.util.List;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.sys.dto.HomeBannerDto;
import com.ofweek.live.core.modules.sys.entity.HomeBanner;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface HomeBannerDao extends CrudDao<HomeBanner> {
	
}