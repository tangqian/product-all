package com.thinkgem.jeesite.modules.live.room.service;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.live.base.config.LiveEnv;
import com.thinkgem.jeesite.modules.live.base.dto.HttpBaseResponse;
import com.thinkgem.jeesite.modules.live.base.enums.LiveFileSubjectTypeEnum;
import com.thinkgem.jeesite.modules.live.base.service.AbstractModuleService;
import com.thinkgem.jeesite.modules.live.base.utils.NewHttpClient;
import com.thinkgem.jeesite.modules.live.room.dao.RoomDao;
import com.thinkgem.jeesite.modules.live.room.entity.Room;
import com.thinkgem.jeesite.modules.sys.service.SysFileService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangqian
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class RoomService extends AbstractModuleService<RoomDao, Room> {

	@Resource
	private SysFileService sysFileService;

	@Override
	@Transactional(readOnly = false)
	public void save(Room entity) {
		if (StringUtils.isBlank(entity.getId())) {
			buildEntity(entity);
			setIndustryIds(entity);
			entity.setId(sequenceService.nextIntString("LiveRoom"));
			entity.preInsert();
			dao.insert(entity);
		} else {
			buildEntity(entity);
			entity.preUpdate();
			setIndustryIds(entity);
			dao.update(entity);
		}
	}

	private void buildEntity(Room entity){
		entity.setCoverId(sysFileService.remoteSave(entity.getCoverId(), LiveFileSubjectTypeEnum.ROOM_COVER));
		if(StringUtils.isNotBlank(entity.getReviewId())){
			entity.setReviewId(sysFileService.remoteSave(entity.getReviewId(), LiveFileSubjectTypeEnum.ROOM_REVIEW));
		}
		entity.setDetail(StringEscapeUtils.unescapeHtml4(entity.getDetail()));
		entity.setSummary(StringEscapeUtils.unescapeHtml4(entity.getSummary()));
	}

	private void setIndustryIds(Room entity) {
		if (entity == null)
			return;
		StringBuffer buffer = new StringBuffer();
		List<String> industriesIdList = entity.getIndustriesIdList();
		if (industriesIdList != null) {
			for (int i = 0, len = industriesIdList.size(); i < len; i++) {
				buffer.append(industriesIdList.get(i)).append(",");
			}
			buffer.insert(0, ",");
			entity.setIndustry(buffer.toString());
		}
		if (StringUtils.isBlank(entity.getId())) {
			entity.setStatus(0);// 新建直播,即为待举办状态
		}
	}

	@Transactional(readOnly = false)
	public void updateStatus(Room room) {
		room.preUpdate();
		dao.updateStatus(room);
		HttpBaseResponse response = NewHttpClient.get(LiveEnv.getWebSite() + "/api/nio/noticeRoomStatus?roomId=" + room.getId(), HttpBaseResponse.class);
		if (response != null && response.getCode() != 0) {
			logger.error("调用直播系统房间状态改变通知接口失败!,msg={}", response.getMessage());
		}
	}

	@Transactional(readOnly = false)
	public void updateTop(Room room) {
		room.preUpdate();
		dao.update(room);
	}

}
