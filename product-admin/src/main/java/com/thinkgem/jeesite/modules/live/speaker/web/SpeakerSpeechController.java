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
import com.thinkgem.jeesite.modules.live.speaker.entity.SpeakerSpeech;
import com.thinkgem.jeesite.modules.live.speaker.service.SpeakerSpeechService;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping(value = "${adminPath}/live/speakerSpeech")
public class SpeakerSpeechController extends BaseController {
	
    @Resource
    private SpeakerSpeechService speakerSpeechService;
    
	@ModelAttribute("speakerSpeech")
	public SpeakerSpeech get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return speakerSpeechService.get(id);
		}else{
			return new SpeakerSpeech();
		}
	}
	
	@RequiresPermissions("live:data:view")
	@RequestMapping(value = "form")
	public String form(SpeakerSpeech speakerSpeech, Model model) {
		model.addAttribute("speakerSpeech", speakerSpeech);
		return "modules/live/speaker/speakerSpeechForm";
	}
	
	@RequiresPermissions("live:speech:audit")
	@ResponseBody
	@RequestMapping(value = "audit")
	public String audit(SpeakerSpeech speakerSpeech, Model model) {
		if (speakerSpeech != null && StringUtils.isNotBlank(speakerSpeech.getId())) speakerSpeechService.save(speakerSpeech);
		return "true";
	}
	
}
