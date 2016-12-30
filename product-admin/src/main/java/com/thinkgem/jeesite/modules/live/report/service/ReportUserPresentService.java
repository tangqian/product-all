package com.thinkgem.jeesite.modules.live.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.report.dao.ReportUserPresentDao;
import com.thinkgem.jeesite.modules.live.report.entity.ReportUserPresent;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class ReportUserPresentService extends AbstractModuleService<ReportUserPresentDao, ReportUserPresent> {
	
    @Override
    @Transactional(readOnly = false)
    public void save(ReportUserPresent entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(sequenceService.nextIntString("LiveReportUserPresent"));
            entity.preInsert();
            dao.insert(entity);
        } else {
        	entity.preUpdate();
            dao.update(entity);
        }
    }
    
    public Integer countPresentAudienceForRoomId(String roomId) {
    	return dao.countPresentAudienceForRoomId(roomId);
    }
 
}
