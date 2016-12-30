package com.thinkgem.jeesite.modules.live.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.room.dao.DataAuditVoDao;
import com.thinkgem.jeesite.modules.live.room.vo.DataAuditVo;

@Service
@Transactional(readOnly = true)
public class DataAuditVoService extends AbstractModuleService<DataAuditVoDao, DataAuditVo> {

}
