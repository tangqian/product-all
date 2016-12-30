package com.thinkgem.jeesite.modules.live.base.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.live.base.entity.HomeBanner;
import com.thinkgem.jeesite.modules.live.base.service.HomeBannerService;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping(value = "${adminPath}/live/homeBanner")
public class HomeBannerController extends BaseController {
	
    @Resource
    private HomeBannerService homeBannerService;
    
	@ModelAttribute("homeBanner")
	public HomeBanner get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return homeBannerService.get(id);
		}else{
			return new HomeBanner();
		}
	}
	
	@RequiresPermissions("live:homeBanner:view")
	@RequestMapping(value = {"list", ""})
	public String list(HomeBanner homeBanner, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HomeBanner> page = homeBannerService.findPage(new Page<HomeBanner>(request, response), homeBanner);
        model.addAttribute("page", page);
		return "modules/live/base/homeBannerList";
	}

	@RequiresPermissions("live:homeBanner:view")
	@RequestMapping(value = "form")
	public String form(HomeBanner homeBanner, Model model) {
		model.addAttribute("homeBanner", homeBanner);
		return "modules/live/base/homeBannerForm";
	}
	
	@RequiresPermissions("live:homeBanner:edit")
	@RequestMapping(value = "save")
	public String save(HomeBanner homeBanner, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, homeBanner)){
			return form(homeBanner, model);
		}
		homeBannerService.save(homeBanner);
		addMessage(redirectAttributes, "保存banner图'" + homeBanner.getName() + "'成功");
		return "redirect:" + adminPath + "/live/homeBanner/?repage";
	}
	
	@RequiresPermissions("live:homeBanner:edit")
	@RequestMapping("sort")
	@ResponseBody
	public Map<String, String> sort(@RequestParam(value = "id[]") Integer id[], @RequestParam(value = "order[]") String order[]) {
		Map<String, String> map = new HashMap<String, String>();
		HomeBanner banner = new HomeBanner();
		for (int i = 0; i < id.length; i++) {
			if (NumberUtils.isDigits(order[i])) {
				banner.setId(String.valueOf(id[i]));
				banner.setSort(Integer.valueOf(order[i]));
				homeBannerService.updateSort(banner);
			}
		}
		map.put("result", "success");
		return map;
	}
	
	@RequiresPermissions("live:homeBanner:delete")
	@RequestMapping(value = "delete")
	public String delete(HomeBanner homeBanner, RedirectAttributes redirectAttributes) {
		homeBannerService.delete(homeBanner);
		addMessage(redirectAttributes, "删除banner图成功");
		return "redirect:" + adminPath + "/live/homeBanner/?repage";
	}
	
}
