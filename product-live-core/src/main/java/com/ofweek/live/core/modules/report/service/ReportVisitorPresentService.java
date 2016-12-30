package com.ofweek.live.core.modules.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.report.dao.ReportVisitorPresentDao;
import com.ofweek.live.core.modules.report.entity.ReportVisitorPresent;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class ReportVisitorPresentService extends CrudService<ReportVisitorPresentDao, ReportVisitorPresent> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(ReportVisitorPresent entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("LiveReportVisitorPresent"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}

	@Transactional(readOnly = false)
	public void save(String roomId, String visitorId) {
		ReportVisitorPresent present = dao.getByRoomVisitor(roomId, visitorId);
		if (present == null){
			present = new ReportVisitorPresent();
			present.setId(SequenceUtils.getNextString("LiveReportVisitorPresent"));
			present.setRoomId(roomId);
			present.setVisitorId(visitorId);
			present.preInsert();
			dao.insert(present);
		}else{
			present.preUpdate();
			dao.update(present);
		}
	}
    
	public Integer countRoomVisitorForRoomId(String roomId) {
		return dao.countRoomVisitorForRoomId(roomId);
	}
}
