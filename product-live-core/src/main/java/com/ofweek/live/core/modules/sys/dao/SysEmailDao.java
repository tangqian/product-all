package com.ofweek.live.core.modules.sys.dao;

import com.ofweek.live.core.common.persistence.CrudDao;
import com.ofweek.live.core.common.persistence.annotation.MyBatisDao;
import com.ofweek.live.core.modules.sys.entity.SysEmail;

/**
 * 
 * @author tangqian
 */
@MyBatisDao
public interface SysEmailDao extends CrudDao<SysEmail> {

    /**
     * 更新邮件状态
     * @param entity
     */
    int updateStatus(SysEmail entity);
}