package com.thinkgem.jeesite.modules.live.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.room.dao.RoomReviewDataDao;
import com.thinkgem.jeesite.modules.live.room.entity.RoomReviewData;

/**
 * @version 1.0
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomReviewDataService extends AbstractModuleService<RoomReviewDataDao, RoomReviewData> {

	@Override
	@Transactional(readOnly = false)
	public void save(RoomReviewData entity) {
		if (StringUtils.isBlank(entity.getId())) {
			entity.setId(sequenceService.nextIntString("LiveRoomReviewData"));
			entity.preInsert();
			setSort(entity);
			dao.insert(entity);
		} else {
			entity.preUpdate();
			dao.update(entity);
		}
	}

	public Page<RoomReviewData> findReviewDataList(Page<RoomReviewData> page, RoomReviewData entity) {
		entity.setPage(page);
		page.setList(dao.findReviewDataList(entity));
		return page;
	}
	
	public void setSort(RoomReviewData entity) {
		Integer sort = dao.getMaxSort();
		if (sort != null) {
			entity.setSort(sort.intValue() + 1);
		} else {
			entity.setSort(1);
		}
	}
}
