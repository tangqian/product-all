package com.thinkgem.jeesite.modules.live.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.report.dao.ReportVisitorPresentDao;
import com.thinkgem.jeesite.modules.live.report.entity.ReportVisitorPresent;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class ReportVisitorPresentService extends AbstractModuleService<ReportVisitorPresentDao, ReportVisitorPresent> {

    @Override
    @Transactional(readOnly = false)
    public void save(ReportVisitorPresent entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(sequenceService.nextIntString("LiveReportVisitorPresent"));
            entity.preInsert();
            dao.insert(entity);
        } else {
        	entity.preUpdate();
            dao.update(entity);
        }
    }
    
	public Integer countRoomVisitorForRoomId(String roomId) {
		return dao.countRoomVisitorForRoomId(roomId);
	}
}
