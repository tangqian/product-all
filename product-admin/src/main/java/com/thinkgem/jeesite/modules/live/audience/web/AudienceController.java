package com.thinkgem.jeesite.modules.live.audience.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.live.audience.entity.Audience;
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
import com.thinkgem.jeesite.modules.live.audience.service.AudienceService;
import com.thinkgem.jeesite.modules.live.audience.service.ExportAudienceService;
import com.thinkgem.jeesite.modules.live.audience.vo.AudienceVo;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping(value = "${adminPath}/live/audience")
public class AudienceController extends BaseController {
	
    @Resource
    private AudienceService audienceService;
    
    @Resource
    private ExportAudienceService exportService;
    
	@ModelAttribute("audience")
	public Audience get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return audienceService.get(id);
		}else{
			return new Audience();
		}
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Audience audience, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Audience> page = audienceService.findPage(new Page<Audience>(request, response), audience);
        model.addAttribute("page", page);
		return "modules/live/audience/audienceList";
	}

	@RequestMapping(value = "form")
	public String form(Audience audience, Model model) {
		model.addAttribute("audience", audience);
		return "modules/live/audience/audienceForm";
	}
	
	@RequestMapping(value = "save")
	public String save(Audience audience, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, audience)){
			return form(audience, model);
		}
		audienceService.save(audience);
		addMessage(redirectAttributes, "保存'" + audience.getName() + "'成功");
		return "redirect:" + adminPath + "/live/audience/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Audience audience, RedirectAttributes redirectAttributes) {
		audienceService.delete(audience);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/live/audience/?repage";
	}
	
	/**
	 * 直播间出席观众列表
	 * 
	 * @param audience
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"count/list", ""})
	public String count(Audience audience, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Audience> page = audienceService.findAudienceForRoomId(new Page<Audience>(request, response), audience);
        model.addAttribute("page", page);
		return "modules/live/audience/roomAudienceList";
	}
	
	/**
	 * 导出出席观众
	 * 
	 * @param response
	 * @param ids
	 */
	@RequiresPermissions("live:audienceRegister:export")
	@RequestMapping("export")
    public void export(HttpServletResponse response, String ids) {
		List<String> idsList = Arrays.asList(StringUtils.split(ids, ","));
        List<AudienceVo> datasource = audienceService.findAudienceForIds(idsList);
        exportService.export(response, datasource, "出席观众名单");
    }
	
}
