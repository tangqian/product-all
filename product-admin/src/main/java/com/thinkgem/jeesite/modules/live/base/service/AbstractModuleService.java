package com.thinkgem.jeesite.modules.live.base.service;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;
import com.thinkgem.jeesite.modules.sys.service.SequenceService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 各业务模块Service基类
 *
 * @author tangqian
 * @version 2016-02-25
 */
@Transactional(readOnly = true)
public abstract class AbstractModuleService<D extends CrudDao<T>, T extends ExDataEntity<T>> extends ExCrudService<D, T> {

    @Resource
    protected SequenceService sequenceService;

}
