package com.thinkgem.jeesite.modules.live.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.report.dao.ReportDataDownloadDao;
import com.thinkgem.jeesite.modules.live.report.entity.ReportDataDownload;

/**
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class ReportDataDownloadService extends AbstractModuleService<ReportDataDownloadDao, ReportDataDownload> {

    @Override
    @Transactional(readOnly = false)
    public void save(ReportDataDownload entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(sequenceService.nextIntString("LiveReportDataDownload"));
            entity.preInsert();
            dao.insert(entity);
        } else {
        	entity.preUpdate();
            dao.update(entity);
        }
    }

}
