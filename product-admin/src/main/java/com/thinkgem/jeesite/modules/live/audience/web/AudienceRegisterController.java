package com.thinkgem.jeesite.modules.live.audience.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.live.audience.entity.AudienceRegister;
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
import com.thinkgem.jeesite.modules.live.base.utils.NewHttpClient;
import com.thinkgem.jeesite.modules.live.room.entity.RoomBlacklist;
import com.thinkgem.jeesite.modules.live.audience.form.AudienceRegisterPageForm;
import com.thinkgem.jeesite.modules.live.audience.service.AudienceRegisterService;
import com.thinkgem.jeesite.modules.live.audience.service.ExportAudienceRegisterService;
import com.thinkgem.jeesite.modules.live.room.service.RoomBlacklistService;
import com.thinkgem.jeesite.modules.live.audience.vo.AudienceRegisterVo;
import com.thinkgem.jeesite.modules.live.base.config.LiveEnv;
import com.thinkgem.jeesite.modules.live.base.dto.HttpBaseResponse;

/**
 * @version 1.0
 * @author tangqian
 */
@Controller
@RequestMapping(value = "${adminPath}/live/audienceRegister")
public class AudienceRegisterController extends BaseController {
	
    @Resource
    private AudienceRegisterService audienceRegisterService;
    
    @Resource
    private ExportAudienceRegisterService exportService;
    
    @Resource
    private RoomBlacklistService roomBlacklistService;
    
	@ModelAttribute("audienceRegister")
	public AudienceRegister get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return audienceRegisterService.get(id);
		}else{
			return new AudienceRegister();
		}
	}
	
	@RequiresPermissions("live:audienceRegister:view")
	@RequestMapping(value = {"list", ""})
	public String list(AudienceRegister audienceRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AudienceRegister> page = audienceRegisterService.findPage(new Page<AudienceRegister>(request, response), audienceRegister);
        model.addAttribute("page", page);
		return "modules/live/audience/audienceRegisterList";
	}

	@RequiresPermissions("live:audienceRegister:view")
	@RequestMapping(value = "form")
	public String form(Integer id, Model model) {
	    if (id != null) {
	    	AudienceRegisterVo audienceRegister = audienceRegisterService.queryAudienceRegisterForId(id);
	    	model.addAttribute("audienceRegister", audienceRegister);
	    }
		return "modules/live/audience/audienceRegisterForm";
	}
	
	@RequiresPermissions("live:audienceRegister:delete")
	@RequestMapping(value = "delete")
	public String delete(AudienceRegister audienceRegister, RedirectAttributes redirectAttributes) {
		audienceRegisterService.delete(audienceRegister);
		addMessage(redirectAttributes, "删除观众登记成功");
		return "redirect:" + adminPath + "/live/audienceRegister/?repage";
	}
	
	@RequiresPermissions("live:audienceRegister:export")
	@RequestMapping("export")
    public void export(HttpServletResponse response, AudienceRegisterPageForm form) {
        List<AudienceRegisterVo> datasource = audienceRegisterService.queryAudienceRegisterForObj(form);
        exportService.export(response, datasource);
    }
	
	@RequiresPermissions("live:audienceRegister:blacklist")
	@RequestMapping("audienceRegisterBacklistForm")
    public String audienceRegisterBacklistForm(HttpServletRequest request) {
		
        return "modules/live/audience/audienceRegisterBacklistForm";
    }
	
	@RequiresPermissions("live:audienceRegister:blacklist")
	@RequestMapping("addBlacklist")
    @ResponseBody
    public Map<String, String> addBlacklist(Integer audienceId, Integer roomId, String reason) {
        Map<String, String> map = new HashMap<String, String>();
        if (audienceId != null && roomId != null) {
            try {
            	RoomBlacklist record = roomBlacklistService.findBlacklistForObj(new RoomBlacklist(audienceId, roomId, reason));
            	if (record != null) {
            		record.setReason(reason);
            	} else {
            		record = new RoomBlacklist(audienceId, roomId, reason);
            	}
            	roomBlacklistService.save(record);
				sendNotice(record.getId());
                map.put("result", "success");
            } catch (Exception e) {
                logger.error("加入黑名单失败", e);
                map.put("result", "failure");
            }
        }
        return map;
    }

	/**
	 * 通知直播系统该用户已拉黑
	 */
	private void sendNotice(String id) {
		HttpBaseResponse response = NewHttpClient.get(LiveEnv.getWebSite() + "/api/nio/noticeBlacklist?id=" + id, HttpBaseResponse.class);
		if (response != null && response.getCode() != 0) {
			logger.error("调用直播系统通知接口失败!,msg={}", response.getMessage());
		}
	}
	
	/**
	 * 直播间登记观众列表
	 * 
	 * @param audienceRegister
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"count/list", ""})
	public String count(AudienceRegister audienceRegister, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AudienceRegister> page = audienceRegisterService.findAudienceRegisterForRoomId(new Page<AudienceRegister>(request, response), audienceRegister);
        model.addAttribute("page", page);
		return "modules/live/audience/roomAudienceRegisterList";
	}
	
}
