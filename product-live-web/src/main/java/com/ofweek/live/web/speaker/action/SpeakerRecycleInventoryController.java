package com.ofweek.live.web.speaker.action;

import java.io.IOException;
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

import com.ofweek.live.core.common.persistence.Page;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.speaker.entity.SpeakerRecycleInventory;
import com.ofweek.live.core.modules.speaker.service.SpeakerRecycleInventoryService;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 
 * @author tangqian
 */
@Controller
@RequestMapping(value = "/speaker/recycle")
public class SpeakerRecycleInventoryController extends BaseController {
	
    @Resource
    private SpeakerRecycleInventoryService speakerRecycleInventoryService;
    
	@ModelAttribute("recycle")
	public SpeakerRecycleInventory get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return speakerRecycleInventoryService.get(id);
		}else{
			return new SpeakerRecycleInventory();
		}
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SpeakerRecycleInventory recycle, HttpServletRequest request, HttpServletResponse response, Model model) {
		recycle.setSpeakerId(UserUtils.getUser().getId());
		Page<SpeakerRecycleInventory> page = speakerRecycleInventoryService.findPage(new Page<>(request, response), recycle);
        model.addAttribute("page", page);
		return "modules/speaker/recycleList";
	}

	@RequestMapping(value = "form")
	public String form(@ModelAttribute("recycle") SpeakerRecycleInventory recycle, Model model) {
		return "modules/speaker/recycleForm";
	}
	
	@RequestMapping(value = "save")
	public String save(SpeakerRecycleInventory recycle, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, recycle)){
			return form(recycle, model);
		}
		speakerRecycleInventoryService.save(recycle);
		addMessage(redirectAttributes, "保存回收站成功");
		return "redirect:/speaker/recycle/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(SpeakerRecycleInventory recycle, RedirectAttributes redirectAttributes) {
		speakerRecycleInventoryService.delete(recycle);
		addMessage(redirectAttributes, "删除回收站成功");
		return "redirect:/speaker/recycle/?repage";
	}
	
    @RequestMapping(value = "validateReport", method = {RequestMethod.POST })
    @ResponseBody
    public Map<String, String> validateReport(@RequestBody SpeakerRecycleInventory recycle, HttpServletResponse response) {
    	Map<String, String> map = new HashMap<>();
    	try {
    		String isExists = speakerRecycleInventoryService.validateReport(recycle);
    		if ("limit".equals(isExists)) {
    			map.put("status", "2");
    		} else if ("repeat".equals(isExists)) {
    			map.put("status", "1");
    		} else {
    			map.put("status", "0");
    		}
    	} catch (Exception e) {
    		map.put("status", "3");
    		logger.error("验证资料异常", e);
    		e.printStackTrace();
    	}
    	return map;
    }
    
    @RequestMapping(value = "asyncReduction", method = {RequestMethod.POST })
    @ResponseBody
    public Map<String, String> asyncReduction(@RequestBody SpeakerRecycleInventory recycle, HttpServletResponse response) {
    	Map<String, String> map = new HashMap<>();
    	try {
    		speakerRecycleInventoryService.reduction(recycle);
    		map.put("status", "0");
    	} catch (Exception e) {
    		map.put("status", "1");
    		e.printStackTrace();
    		logger.error("还原资料失败", e);
    	}
    	return map;
    } 
}
