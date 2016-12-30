package com.ofweek.live.core.modules.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.report.dao.ReportUserPresentDao;
import com.ofweek.live.core.modules.report.entity.ReportUserPresent;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class ReportUserPresentService extends CrudService<ReportUserPresentDao, ReportUserPresent> {
	
	@Override
	@Transactional(readOnly = false)
	public void save(ReportUserPresent entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("LiveReportUserPresent"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}

	@Transactional(readOnly = false)
	public void save(String roomId, String userId) {
		ReportUserPresent present = dao.getByRoomUser(roomId, userId);
		if (present == null){
			present = new ReportUserPresent();
			present.setId(SequenceUtils.getNextString("LiveReportUserPresent"));
			present.setRoomId(roomId);
			present.setUserId(userId);
			present.preInsert();
			dao.insert(present);
		}else{
			present.preUpdate();
			dao.update(present);
		}
	}
	
	public Integer countAllAudienceForRoomId(String roomId) {
		return dao.countAllAudienceForRoomId(roomId);
	}
    
}
