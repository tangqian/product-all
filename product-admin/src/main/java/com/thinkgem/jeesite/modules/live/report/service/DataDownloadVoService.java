package com.thinkgem.jeesite.modules.live.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.report.dao.DataDownloadVoDao;
import com.thinkgem.jeesite.modules.live.report.vo.DataDownloadVo;

@Service
@Transactional(readOnly = true)
public class DataDownloadVoService extends AbstractModuleService<DataDownloadVoDao, DataDownloadVo> {

}
