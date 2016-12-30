package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysFile;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface SysFileDao extends CrudDao<SysFile> {
	
}