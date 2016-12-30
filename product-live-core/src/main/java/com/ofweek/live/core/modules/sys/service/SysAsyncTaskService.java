package com.ofweek.live.core.modules.sys.service;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.sys.dao.SysAsyncTaskDao;
import com.ofweek.live.core.modules.sys.entity.SysAsyncTask;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class SysAsyncTaskService extends CrudService<SysAsyncTaskDao, SysAsyncTask> {

	@Override
	@Transactional(readOnly = false)
	public void save(SysAsyncTask entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("SysAsyncTask"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}

	public int countUnendTask(int type) {
		SysAsyncTask asyncTask = new SysAsyncTask();
		asyncTask.setType(type);
		return dao.countUnendTask(asyncTask);
	}

}