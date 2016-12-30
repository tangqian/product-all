package com.ofweek.live.web.room.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ofweek.live.core.common.persistence.Page;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.audience.service.AudienceService;
import com.ofweek.live.core.modules.room.entity.RoomChat;
import com.ofweek.live.core.modules.room.service.RoomChatService;
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.core.modules.speaker.entity.SpeakerWaiter;
import com.ofweek.live.core.modules.speaker.service.SpeakerService;
import com.ofweek.live.core.modules.speaker.service.SpeakerWaiterService;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.utils.RoomUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping(value = "/room/chat")
public class RoomChatController extends BaseController {
	
    @Resource
    private RoomChatService roomChatService;
    
    @Resource
    private SpeakerService speakerService;
    
    @Resource
    private SpeakerWaiterService speakerWaiterService;
    
    @Resource
    private AudienceService audienceService;
    
	@ModelAttribute("roomChat")
	public RoomChat get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return roomChatService.get(id);
		}else{
			return new RoomChat();
		}
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(RoomChat roomChat, HttpServletRequest request, HttpServletResponse response, Model model) {
		setRoomId(roomChat, model);
		roomChat.setSpeakerId(UserUtils.getUser().getId());
		Page<RoomChat> page = roomChatService.findPage(new Page<RoomChat>(request, response), roomChat);
		for (RoomChat chat : page.getList()) {
			setName(chat);
		}
        model.addAttribute("page", page);
		return "modules/room/roomChatList";
	}
	
	private static void setRoomId(RoomChat roomChat, Model model) {
		if (StringUtils.isBlank(roomChat.getRoomId())) {
			String firstRoomId = RoomUtils.getRoomListFirst();
			roomChat.setRoomId(firstRoomId);
			model.addAttribute("currRoomId", firstRoomId);
		} else {
			model.addAttribute("currRoomId", roomChat.getRoomId());
		}
	}
	
	private void setName(RoomChat chat) {
		Integer type = chat.getUser().getType();
		String userId = chat.getUser().getId();
		if (UserTypeEnum.isSpeaker(type)) {
			Speaker speaker = speakerService.get(userId);
			chat.setName(speaker.getName());
		} else if (UserTypeEnum.isAudience(type)) {
			Audience audience = audienceService.get(userId);
			chat.setName(audience.getName());
		} else if (UserTypeEnum.isWaiter(type)) {
			SpeakerWaiter speakerWaiter = speakerWaiterService.get(userId);
			chat.setName(speakerWaiter.getName());
		}
	}
	
	@RequestMapping(value = "form")
	public String form(RoomChat roomChat, Model model) {
		model.addAttribute("roomChat", roomChat);
		return "modules/room/roomChatForm";
	}
	
}
