package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.sys.dao.AsyncTaskDao;
import com.thinkgem.jeesite.modules.sys.entity.AsyncTask;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class AsyncTaskService extends AbstractModuleService<AsyncTaskDao, AsyncTask> {

	@Override
	@Transactional(readOnly = false)
	public void save(AsyncTask entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(sequenceService.nextIntString(AsyncTask.class));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.setLastOperatorId(1);
			User user = UserUtils.getUser();
			if (user != null && StringUtils.isNotBlank(user.getId())){
				try{
					entity.setLastOperatorId(Integer.valueOf(user.getId()));
				}catch (Exception e){
				}
			}
			entity.setLastOperationTime(new Date());
			dao.update(entity);
		}
	}
	
	public int countUnendTask(int type) {
		AsyncTask asyncTask = new AsyncTask();
		asyncTask.setType(type);
		return dao.countUnendTask(asyncTask);
	}

}