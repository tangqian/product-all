package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Sequence;

/**
 * @version 1.0
 * @author tangqian
 */
@MyBatisDao
public interface SequenceDao extends CrudDao<Sequence> {

}