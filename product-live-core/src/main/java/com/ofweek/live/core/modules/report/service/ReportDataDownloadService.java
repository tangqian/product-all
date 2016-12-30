package com.ofweek.live.core.modules.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.modules.report.dao.ReportDataDownloadDao;
import com.ofweek.live.core.modules.report.entity.ReportDataDownload;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

/**
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class ReportDataDownloadService extends CrudService<ReportDataDownloadDao, ReportDataDownload> {

    @Override
    @Transactional(readOnly = false)
    public void save(ReportDataDownload entity) {
        ReportDataDownload download = dao.getOne(entity);
        if (download == null) {
            entity.setId(SequenceUtils.getNextString("LiveReportDataDownload"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            download.preUpdate();
            dao.update(download);
        }
    }
    
	public Integer countDownloadAudienceRoomId(String roomId) {
		return dao.countDownloadAudienceRoomId(roomId, 1);
	}

}
