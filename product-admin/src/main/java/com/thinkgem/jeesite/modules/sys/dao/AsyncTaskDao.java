package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.AsyncTask;

@MyBatisDao
public interface AsyncTaskDao extends CrudDao<AsyncTask> {
	
	int countUnendTask(AsyncTask asyncTask);

}
