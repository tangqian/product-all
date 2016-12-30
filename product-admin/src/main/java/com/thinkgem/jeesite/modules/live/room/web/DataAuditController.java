package com.thinkgem.jeesite.modules.live.room.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.live.base.utils.NewHttpClient;
import com.thinkgem.jeesite.modules.live.base.config.LiveEnv;
import com.thinkgem.jeesite.modules.live.base.dto.HttpBaseResponse;
import com.thinkgem.jeesite.modules.live.room.service.DataAuditVoService;
import com.thinkgem.jeesite.modules.live.speaker.service.SpeakerVideoService;
import com.thinkgem.jeesite.modules.live.room.vo.DataAuditVo;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping(value = "${adminPath}/live/data")
public class DataAuditController extends BaseController {

	@Resource
	private DataAuditVoService dataAuditVoService;

	@Resource
	private SpeakerVideoService speakerVideoService;

	@ModelAttribute("dataAuditVo")
	public DataAuditVo get(@RequestParam(required=false) String id) {
		return new DataAuditVo();
	}

	@RequiresPermissions("live:data:view")
	@RequestMapping(value = {"list", ""})
	public String list(DataAuditVo vo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DataAuditVo> page = dataAuditVoService.findPage(new Page<DataAuditVo>(request, response), vo);
		model.addAttribute("page", page);

		return "modules/live/room/dataAuditList";
	}
	
	@RequiresPermissions("live:data:view")
	@RequestMapping("syncVideoToCloud")
	@ResponseBody
	public HttpBaseResponse syncVideoToCloud() {
		HttpBaseResponse result = NewHttpClient.get(LiveEnv.getWebSite() + "/api/cloud/videoSync", HttpBaseResponse.class);
		if (result == null) {
			result = new HttpBaseResponse();
			result.setCode(-1);
			result.setMessage("调用直播系统同步接口失败,请确认直播系统能否正常访问");
		}
		return result;
	}

}
