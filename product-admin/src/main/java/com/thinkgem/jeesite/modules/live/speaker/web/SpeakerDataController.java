package com.thinkgem.jeesite.modules.live.speaker.web;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.live.speaker.entity.SpeakerData;
import com.thinkgem.jeesite.modules.live.speaker.service.SpeakerDataService;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping(value = "${adminPath}/live/speakerData")
public class SpeakerDataController extends BaseController {
	
    @Resource
    private SpeakerDataService speakerDataService;
    
	@ModelAttribute("speakerData")
	public SpeakerData get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return speakerDataService.get(id);
		}else{
			return new SpeakerData();
		}
	}
	
	@RequiresPermissions("live:data:view")
	@RequestMapping(value = "form")
	public String form(SpeakerData speakerData, Model model) {
		model.addAttribute("speakerData", speakerData);
		return "modules/live/speaker/speakerDataForm";
	}
	
	@RequiresPermissions("live:data:audit")
	@ResponseBody
	@RequestMapping(value = "audit")
	public String audit(SpeakerData speakerData, Model model) {
		if (speakerData != null && StringUtils.isNotBlank(speakerData.getId())) speakerDataService.save(speakerData);
		return "true";
	}
	
}
