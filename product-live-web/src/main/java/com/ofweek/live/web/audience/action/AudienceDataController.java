package com.ofweek.live.web.audience.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ofweek.live.core.common.persistence.Page;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.audience.service.AudienceDataService;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.service.RoomService;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "/speaker/audience/data")
public class AudienceDataController extends BaseController {
	
	@Resource
	private RoomService roomService;
	
	@Resource
	private AudienceDataService audienceDataService;
	
	@RequestMapping(value = {"list", ""})
	public String list(Room room, HttpServletRequest request, HttpServletResponse response, Model model) {
		room.setSpeakerId(UserUtils.getUser().getId());
		Page<Room> page = roomService.findPage(new Page<Room>(request, response), room);
		for (Room os : page.getList()) {
			audienceDataService.count(os);
		}
		model.addAttribute("page", page);
		return "modules/audience/audienceDataManage";
	}

}
