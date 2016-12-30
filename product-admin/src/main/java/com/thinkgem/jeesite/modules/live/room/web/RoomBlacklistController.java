package com.thinkgem.jeesite.modules.live.room.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.live.room.entity.RoomBlacklist;
import com.thinkgem.jeesite.modules.live.room.service.RoomBlacklistService;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping(value = "${adminPath}/live/roomBlacklist")
public class RoomBlacklistController extends BaseController {
	
    @Resource
    private RoomBlacklistService roomBlacklistService;
    
	@ModelAttribute("roomBlacklist")
	public RoomBlacklist get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return roomBlacklistService.get(id);
		}else{
			return new RoomBlacklist();
		}
	}
	
	@RequiresPermissions("live:roomBlacklist:view")
	@RequestMapping(value = {"list", ""})
	public String list(RoomBlacklist roomBlacklist, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RoomBlacklist> page = roomBlacklistService.findPage(new Page<RoomBlacklist>(request, response), roomBlacklist);
        model.addAttribute("page", page);
		return "modules/live/room/roomBlacklistList";
	}

	@RequiresPermissions("live:roomBlacklist:view")
	@RequestMapping(value = "form")
	public String form(@ModelAttribute("roomBlacklist") RoomBlacklist roomBlacklist, Model model) {
		return "modules/live/room/roomBlacklistForm";
	}
	
	@RequiresPermissions("live:roomBlacklist:edit")
	@RequestMapping(value = "save")
	public String save(RoomBlacklist roomBlacklist, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, roomBlacklist)){
			return form(roomBlacklist, model);
		}
		roomBlacklistService.save(roomBlacklist);
		addMessage(redirectAttributes, "保存黑名单成功");
		return "redirect:" + adminPath + "/live/roomBlacklist/?repage";
	}
	
	@RequiresPermissions("live:roomBlacklist:delete")
	@RequestMapping(value = "delete")
	public String delete(RoomBlacklist roomBlacklist, RedirectAttributes redirectAttributes) {
		roomBlacklistService.delete(roomBlacklist);
		addMessage(redirectAttributes, "删除黑名单成功");
		return "redirect:" + adminPath + "/live/roomBlacklist/?repage";
	}
	
}
