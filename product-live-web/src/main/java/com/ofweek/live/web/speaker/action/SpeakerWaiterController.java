package com.ofweek.live.web.speaker.action;

import java.io.IOException;

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
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.core.modules.speaker.entity.SpeakerWaiter;
import com.ofweek.live.core.modules.speaker.service.SpeakerService;
import com.ofweek.live.core.modules.speaker.service.SpeakerWaiterService;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.core.modules.sys.utils.SysFileUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 
 * @author tangqian
 */
@Controller
@RequestMapping(value = "/speaker/waiter")
public class SpeakerWaiterController extends BaseController {
	
    @Resource
    private SpeakerWaiterService speakerWaiterService;
    
    @Resource
    private UserService userService;
    
    @Resource
    private SpeakerService speakerService;
     
	@ModelAttribute("waiter")
	public SpeakerWaiter get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return speakerWaiterService.get(id);
		}else{
			return new SpeakerWaiter();
		}
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SpeakerWaiter waiter, HttpServletRequest request, HttpServletResponse response, Model model) {
		waiter.setSpeakerId(UserUtils.getUser().getId());
		Page<SpeakerWaiter> page = speakerWaiterService.findPage(new Page<>(request, response), waiter);
		for (SpeakerWaiter entity : page.getList()) {
			setSysFile(entity, model, "list");
		}
        model.addAttribute("page", page);
        model.addAttribute("totalCount", getCount(page.getCount()));
        model.addAttribute("maxLimit", LiveEnv.getWaiterLimit());
        setSpeakerWaiter(model);
		return "modules/speaker/account/waiter/waiterList";
	}
	
	private void setSpeakerWaiter(Model model) {
		Speaker speaker = speakerService.getSpeakerForId();
		model.addAttribute("speaker", speaker);
	}
	
	private static long getCount(long count) {
		return count + 1;
	}
	
	@RequestMapping(value = "form")
	public String form(@ModelAttribute("waiter") SpeakerWaiter waiter, Model model) {
		setSysFile(waiter, model, "form");
		model.addAttribute("waiter", speakerWaiterService.getSpeakerWaiterForId(waiter.getId()));
		return "modules/speaker/account/waiter/waiterForm";
	}
	
	private void setSysFile(SpeakerWaiter waiter, Model model, String pageType) {
		String logoId = waiter.getLogoId();
		if (StringUtils.isNotBlank(logoId)) {
			SysFile sysFile = sysFileService.get(logoId);
			if ("list".equals(pageType)) {
				waiter.setUri(sysFile.getUri());
			} else {
				model.addAttribute("sysFile", sysFile);
			}
		}
	}
	
	@RequestMapping(value = "save")
	public String save(SpeakerWaiter waiter, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, waiter)){
			return form(waiter, model);
		}
		if (StringUtils.isNotBlank(waiter.getLogoId())) {
			SysFile sysFile = sysFileService.save(waiter.getLogoId(), SysFileUtils.TypeEnum.WAITER_LOGO);
			waiter.setLogoId(sysFile.getId());
		}
		speakerWaiterService.save(waiter);
		addMessage(redirectAttributes, "保存客服'" + waiter.getName() + "'成功");
		return "redirect:/speaker/waiter/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(SpeakerWaiter waiter, RedirectAttributes redirectAttributes) {
		speakerWaiterService.delete(waiter);
		addMessage(redirectAttributes, "删除客服成功");
		return "redirect:/speaker/waiter/?repage";
	}
	
    @RequestMapping(value = "verifyAccount", method = {RequestMethod.POST })
    @ResponseBody
	public Boolean verifyAccount(@RequestBody String account, HttpServletRequest request) {
		if (userService.get(account.split("=")[1], UserTypeEnum.WAITER.getCode()) == null 
				&& userService.get(account.split("=")[1], UserTypeEnum.SPEAKER.getCode()) == null) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
}
