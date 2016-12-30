package com.thinkgem.jeesite.modules.live.report.web;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.live.audience.service.AudienceService;
import com.thinkgem.jeesite.modules.live.audience.service.ExportAudienceService;
import com.thinkgem.jeesite.modules.live.audience.vo.AudienceVo;
import com.thinkgem.jeesite.modules.live.report.service.DataDownloadVoService;
import com.thinkgem.jeesite.modules.live.report.vo.DataDownloadVo;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping(value = "${adminPath}/live/dataDownload")
public class DataDownloadController extends BaseController {

	@Resource
	private DataDownloadVoService dataDownloadVoService;
	
    @Resource
    private AudienceService audienceService;
    
    @Resource
    private ExportAudienceService exportService;

	@ModelAttribute("dataDownloadVo")
	public DataDownloadVo get(@RequestParam(required=false) String id) {
		return new DataDownloadVo();
	}

	@RequiresPermissions("live:dataDownload:view")
	@RequestMapping(value = {"list", ""})
	public String list(DataDownloadVo vo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DataDownloadVo> page = dataDownloadVoService.findPage(new Page<DataDownloadVo>(request, response), vo);
		model.addAttribute("page", page);

		return "modules/live/report/dataDownloadList";
	}
	
	/**
	 * 导出下载资料观众
	 * 
	 * @param response
	 * @param ids
	 */
	@RequiresPermissions("live:dataDownload:export")
	@RequestMapping("export")
    public void export(HttpServletResponse response, String ids) {
		List<String> idsList = Arrays.asList(StringUtils.split(ids, ","));
        List<AudienceVo> datasource = audienceService.findAudienceForIds(idsList);
        exportService.export(response, datasource, "下载资料观众名单");
    }
}
