package com.ofweek.live.web.speaker.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.persistence.Page;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.speaker.entity.SpeakerSpeech;
import com.ofweek.live.core.modules.speaker.service.SpeakerSpeechService;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 
 * @author tangqian
 */
@Controller
@RequestMapping(value = "/speaker/speech")
public class SpeakerSpeechController extends BaseController {
	
    @Resource
    private SpeakerSpeechService speakerSpeechService;
    
	@ModelAttribute("speech")
	public SpeakerSpeech get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return speakerSpeechService.get(id);
		}else{
			return new SpeakerSpeech();
		}
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SpeakerSpeech speech, HttpServletRequest request, HttpServletResponse response, Model model) {
		speech.setSpeakerId(UserUtils.getUser().getId());
		Page<SpeakerSpeech> page = speakerSpeechService.findPage(new Page<>(request, response), speech);
        model.addAttribute("page", page);
        model.addAttribute("totalCount", page.getCount());
        model.addAttribute("maxLimit", LiveEnv.getSpeechLimit());
		return "modules/speaker/speechList";
	}

	@RequestMapping(value = "form")
	public String form(@ModelAttribute("speech") SpeakerSpeech speech, Model model) {
		return "modules/speaker/speechForm";
	}
	
	@RequestMapping(value = "save")
	public String save(SpeakerSpeech speech, Model model, RedirectAttributes redirectAttributes) throws Exception {
		try {
			if (!beanValidator(model, speech)){
				return form(speech, model);
			}
			speakerSpeechService.saveSpeech(speech);
			return "redirect:/speaker/speech/?repage";
		} catch (Exception e) {
			logger.error("上传PPT失败!", e);
			model.addAttribute("error", e.getMessage());
			return "modules/speaker/speechForm";
		}
	}
	
	@RequestMapping(value = "check")
	@ResponseBody
	public Map<String, String> check(String speechId, RedirectAttributes redirectAttributes) {
		Map<String, String> map = new HashMap<>();
    	try {
    		String result = speakerSpeechService.check(speechId);
    		if ("Exists".equals(result)) {
    			map.put("status", "1");
    		} else {
    			map.put("status", "0");
    		}
    	} catch (Exception e) {
    		map.put("status", "2");
    		logger.error("校验PPT失败!", e);
    		e.printStackTrace();
    	}
		return map;
	}
	
	@RequestMapping(value = "delete")
	@ResponseBody
	public Map<String, String> delete(@RequestBody SpeakerSpeech speech, RedirectAttributes redirectAttributes) {
		Map<String, String> map = new HashMap<>();
		try {
			speakerSpeechService.delete(speech);
			map.put("status", "0");
		} catch (Exception e) {
			map.put("status", "1");
			logger.error("删除PPT失败!", e);
			e.printStackTrace();
		}
		return map;
	}
    
    @RequestMapping(value = "asyncCheckSpeechName", method = {RequestMethod.POST })
    @ResponseBody
    public Map<String, String> asyncCheckSpeechName(@RequestBody SpeakerSpeech speakerSpeech, HttpServletResponse response) {
    	Map<String, String> map = new HashMap<>();
    	try {
    		speakerSpeech = speakerSpeechService.getSpeakerSpeechForObj(speakerSpeech);
    		if (speakerSpeech == null) {
    			map.put("status", "0");
    		} else {
    			map.put("status", "1");
    		}
    	} catch (Exception e) {
    		map.put("status", "2");
    		logger.error("校验PPT名称失败!", e);
    		e.printStackTrace();
    	}
    	return map;
    }
}
