package com.ofweek.live.core.modules.audience.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.ofweek.live.core.modules.report.service.ReportDataDownloadService;
import com.ofweek.live.core.modules.report.service.ReportUserPresentService;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.service.RoomChatService;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class AudienceDataService {
	
	@Resource
	private AudienceService audienceService;
	
	@Resource
	private ReportDataDownloadService reportDataDownloadService;
	
	@Resource
	private ReportUserPresentService reportUserPresentService;
	
	@Resource
	private RoomChatService roomChatService;
	
	public void count(Room room) {
		room.setAudienceCount(getAudienceCount(room.getId()).get("audienceCount"));
		room.setDownloadAudienceCount(getAudienceCount(room.getId()).get("downloadAudienceCount"));
		room.setRoomChatAudienceCount(getRoomChatAudienceCount(room.getId()).get("roomChatAudienceCount"));
	}

	private Map<String, Integer> getAudienceCount(String roomId) {
		Map<String, Integer> maps = Maps.newHashMap();
		maps.put("audienceCount", reportUserPresentService.countAllAudienceForRoomId(roomId));
		maps.put("downloadAudienceCount", reportDataDownloadService.countDownloadAudienceRoomId(roomId));
		return maps;
	}
	
	private Map<String, Integer> getRoomChatAudienceCount(String roomId) {
		Map<String, Integer> maps = Maps.newHashMap();
		maps.put("roomChatAudienceCount", roomChatService.countRoomChatAudienceForRoomId(roomId));
		return maps;
	}
	
}
