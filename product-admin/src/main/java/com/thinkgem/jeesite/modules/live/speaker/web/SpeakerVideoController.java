package com.thinkgem.jeesite.modules.live.speaker.web;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.live.base.utils.NewHttpClient;
import com.thinkgem.jeesite.modules.live.base.config.LiveEnv;
import com.thinkgem.jeesite.modules.live.base.dto.HttpBaseResponse;
import com.thinkgem.jeesite.modules.live.base.utils.LiveSysFileUtils;
import com.thinkgem.jeesite.modules.live.speaker.entity.SpeakerVideo;
import com.thinkgem.jeesite.modules.live.speaker.service.SpeakerVideoService;
import com.thinkgem.jeesite.modules.sys.service.QcloudService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangqian
 * @version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/live/speakerVideo")
public class SpeakerVideoController extends BaseController {

    @Resource
    private SpeakerVideoService speakerVideoService;

    @Resource
    private QcloudService qcloudService;

    @ModelAttribute("speakerVideo")
    public SpeakerVideo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return speakerVideoService.get(id);
        } else {
            return new SpeakerVideo();
        }
    }

    @RequiresPermissions("live:data:view")
    @RequestMapping(value = "form")
    public String form(SpeakerVideo speakerVideo, Model model) {
        model.addAttribute("speakerVideo", speakerVideo);
        String qcloudFileId = qcloudService.getFileId(LiveSysFileUtils.getCloudFileName(String.valueOf(speakerVideo.getFileId())));
        if (qcloudFileId == null) {
            NewHttpClient.get(LiveEnv.getWebSite() + "/api/cloud/videoSync", HttpBaseResponse.class);
        }
        model.addAttribute("qcloudFileId", qcloudFileId);
        return "modules/live/speaker/speakerVideoForm";
    }

    @RequiresPermissions("live:video:audit")
    @ResponseBody
    @RequestMapping(value = "audit")
    public String audit(SpeakerVideo speakerVideo, Model model) {
        if (speakerVideo != null && StringUtils.isNotBlank(speakerVideo.getId()))
            speakerVideoService.save(speakerVideo);
        return "true";
    }

    @RequestMapping(value = "rewriteForm")
    public String rewriteForm(SpeakerVideo SpeakerVideo, Model model) {
        model.addAttribute("SpeakerVideo", SpeakerVideo);
        return "modules/live/speaker/speakerVedioRewriteForm";
    }

    @RequestMapping(value = "rewrite")
    @ResponseBody
    public Map<String, String> rewrite(SpeakerVideo speakerVideo, Model model, RedirectAttributes redirectAttributes) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            speakerVideoService.save(speakerVideo);
            map.put("result", "success");
        } catch (Exception e) {
            map.put("result", "error");
            e.printStackTrace();
        }
        return map;
    }

}
