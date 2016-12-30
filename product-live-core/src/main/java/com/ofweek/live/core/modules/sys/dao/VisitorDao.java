package com.ofweek.live.core.modules.sys.dao;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.sys.entity.Visitor;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface VisitorDao extends CrudDao<Visitor> {
	
}