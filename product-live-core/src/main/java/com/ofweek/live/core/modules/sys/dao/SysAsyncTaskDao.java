package com.ofweek.live.core.modules.sys.dao;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.sys.entity.SysAsyncTask;

@MyBatisDao
public interface SysAsyncTaskDao extends CrudDao<SysAsyncTask> {
	
	int countUnendTask(SysAsyncTask asyncTask);

}
