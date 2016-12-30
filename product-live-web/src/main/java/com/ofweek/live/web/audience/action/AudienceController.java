package com.ofweek.live.web.audience.action;

import java.util.List;

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
import com.ofweek.live.core.modules.audience.service.AudienceExportService;
import com.ofweek.live.core.modules.audience.service.AudienceService;
import com.ofweek.live.core.modules.room.service.RoomChatService;
import com.ofweek.live.core.modules.room.service.RoomService;
import com.ofweek.live.core.modules.speaker.service.SpeakerDataService;

/**
 * 
 * @author tangqian
 */
@Controller
@RequestMapping(value = "/speaker/audience")
public class AudienceController extends BaseController {
	
    @Resource
    private RoomService roomService;
    
    @Resource
    private RoomChatService roomChatService;
    
    @Resource
    private SpeakerDataService speakerDataService;
    
    @Resource
    private AudienceService audienceService;
    
    @Resource
    private AudienceExportService audienceExportService;
    
	@ModelAttribute("audience")
	public Audience get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return audienceService.get(id);
		}else{
			return new Audience();
		}
	}
	
	@RequestMapping(value = {"all", ""})
	public String all(Audience audience, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Audience> page = audienceService.findPage(new Page<Audience>(request, response), audience);
		setModelView(page, audience, model);
		return "modules/audience/allAudience";
	}
	
	@RequestMapping(value = {"downloadData", ""})
	public String downloadData(Audience audience, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Audience> page = audienceService.findDownloadAudienceList(new Page<Audience>(request, response), audience);
		setModelView(page, audience, model);
		return "modules/audience/downloadDataAudience";
	}
	
	@RequestMapping(value = {"chat", ""})
	public String chat(Audience audience, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Audience> page = audienceService.findAudienceChatList(new Page<Audience>(request, response), audience);
		for (Audience a : page.getList()) {
			a.getRoomChat().setChats(roomChatService.findRoomChatForRoomId(a.getRoomChat().getRoomId(), a.getId()));
		}
		setModelView(page, audience, model);
		return "modules/audience/chatAudience";
	}
	
	private static void setModelView(Page<Audience> page, Audience audience, Model model) {
		model.addAttribute("page", page);
		model.addAttribute("roomId", getRoomId(audience));
	}
	
	private static String getRoomId(Audience audience) {
		if (audience == null) {
			return null;
		}
		return audience.getRoomChat().getRoomId();
	}
	
	/**
	 * 导出
	 * 
	 * @param response
	 * @param audience
	 * @param expType
	 */
	@RequestMapping("export")
    public void export(HttpServletResponse response, Audience audience, String expType) {
        if ("download".equals(expType)) {
			List<Audience> exp = audienceService.findDownloadAudienceList(audience);
			audienceExportService.export(response, exp, "下载资料观众名单", "下载资料观众.xls", expType);
		} else if ("chat".equals(expType)) {
			List<Audience> exp = audienceService.findAudienceChatList(audience);
			exp.forEach(data -> {
				data.getRoomChat().setChats(roomChatService.findRoomChatForRoomId(data.getRoomChat().getRoomId(), data.getId()));
			});
			audienceExportService.export(response, exp, "聊天观众名单", "聊天观众.xls", expType);
		} else {
			List<Audience> exp = audienceService.findAllAudienceList(audience);
			audienceExportService.export(response, exp, "全部观众名单", "全部观众.xls", expType);
		}
	}

}
