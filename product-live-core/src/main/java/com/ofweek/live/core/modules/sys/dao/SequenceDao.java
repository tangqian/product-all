package com.ofweek.live.core.modules.sys.dao;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.sys.entity.Sequence;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface SequenceDao extends CrudDao<Sequence> {
	
}