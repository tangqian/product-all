package com.thinkgem.jeesite.modules.live.speaker.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.live.base.entity.LiveUser;
import com.thinkgem.jeesite.modules.live.base.enums.LiveUserTypeEnum;
import com.thinkgem.jeesite.modules.live.base.service.LiveUserService;
import com.thinkgem.jeesite.modules.live.speaker.entity.Speaker;
import com.thinkgem.jeesite.modules.live.speaker.service.SpeakerService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping(value = "${adminPath}/live/speaker")
public class SpeakerController extends BaseController {
	
    @Resource
    private SpeakerService speakerService;
    
    @Resource
    private LiveUserService liveUserService;
    
	@ModelAttribute("speaker")
	public Speaker get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return speakerService.get(id);
		}else{
			return new Speaker();
		}
	}
	
	@RequiresPermissions("live:speaker:view")
	@RequestMapping(value = {"list", ""})
	public String list(Speaker speaker, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Speaker> page = speakerService.findPage(new Page<Speaker>(request, response), speaker);
        model.addAttribute("page", page);
		return "modules/live/speaker/speakerList";
	}

	@RequiresPermissions("live:speaker:view")
	@RequestMapping(value = "form")
	public String form(Speaker speaker, Model model) {
		Integer source = speaker.getSource();
		if (StringUtils.isNoneBlank(speaker.getId())) {
			speaker = speakerService.getLiveSpeakerForId(speaker.getId());
		}
		getIndustryIds(speaker);
		model.addAttribute("speaker", speaker);
		switch (source) {
			case 1 :
				return "modules/live/speaker/speakerForm";
			default :
				
				return "modules/live/speaker/exhibitorForm";
		}
	}
	
	private void getIndustryIds(Speaker speaker) {
		List<String> idList = Lists.newArrayList();
		String industry = speaker.getIndustry();
		if (StringUtils.isNotBlank(industry)) {
			String industryIds[] = industry.split(",");
			for (int j = 1, len = industryIds.length; j < len; j++) {
				idList.add(industryIds[j]);
			}
		}
		speaker.setIndustriesIdList(idList);
	}
	
	@RequiresPermissions("live:speaker:edit")
	@RequestMapping(value = "save")
	public String save(Speaker speaker, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, speaker)){
			return form(speaker, model);
		}
		speakerService.save(speaker);
		addMessage(redirectAttributes, "保存直播公司'" + speaker.getCompany() + "'成功");
		return "redirect:" + adminPath + "/live/speaker/?repage";
	}
	
	@RequiresPermissions("live:speaker:delete")
	@RequestMapping(value = "delete")
	public String delete(Speaker speaker, RedirectAttributes redirectAttributes) {
		speakerService.delete(speaker);
		addMessage(redirectAttributes, "删除直播公司成功");
		return "redirect:" + adminPath + "/live/speaker/?repage";
	}
	
	@RequestMapping(value = "checkAccount", method = {RequestMethod.POST })
	@ResponseBody
	public String checkAccount(@RequestBody String account, HttpServletRequest request) {
		if (liveUserService.getLiveUserForAccount(account.split("=")[1], LiveUserTypeEnum.WAITER.getCode()) == null
				&& liveUserService.getLiveUserForAccount(account.split("=")[1], LiveUserTypeEnum.SPEAKER.getCode()) == null) {
			return "true";
		}
		return "false";
	}
	
	@RequestMapping(value = "checkCompany", method = {RequestMethod.POST })
	@ResponseBody
	public String checkCompany(@RequestParam String company, @RequestParam String id, HttpServletRequest request) {
		if (speakerService.getLiveSpeakerForConditions(company, id) == null) {
			return "true";
		}
		return "false";
	}
	
    @RequiresPermissions("system:manage:view")
    @RequestMapping("resetPassForm")
    public String resetPassForm(HttpServletRequest request) 
    {
        return "modules/live/speaker/resetPassForm";
    }
	
    @RequiresPermissions("live:speaker:edit")
    @RequestMapping("resetPass")
    public String resetPass(HttpServletRequest request, String account, String password, RedirectAttributes redirectAttributes)
    {
        if (StringUtils.isNotBlanks(account, password))
        {
            LiveUser user = liveUserService.getLiveUserForAccount(account, LiveUserTypeEnum.SPEAKER.getCode());
            if (user != null)
            {
            	user.setPassword(SystemService.entryptPassword(password));
            	liveUserService.save(user);
                addMessage(redirectAttributes, "重置密码成功~");
            }
            else 
            {
                addMessage(redirectAttributes, "亲.用户名不存在~");
            }
        } 
        else
        {
        	addMessage(redirectAttributes, "用户名或密码不能为空哦~");
        }
        
        return "redirect:" + adminPath + "/live/speaker/resetPassForm";
    }
}
