package com.ofweek.live.web.speaker.action;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.persistence.Page;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.speaker.entity.SpeakerVideo;
import com.ofweek.live.core.modules.speaker.service.SpeakerVideoService;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.core.modules.sys.enums.AuditStatusEnum;
import com.ofweek.live.core.modules.sys.utils.SysFileUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangqian
 */
@Controller
@RequestMapping(value = "/speaker/video")
public class SpeakerVideoController extends BaseController {

    @Resource
    private SpeakerVideoService speakerVideoService;

    @ModelAttribute("video")
    public SpeakerVideo get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return speakerVideoService.get(id);
        } else {
            return new SpeakerVideo();
        }
    }

    @RequestMapping(value = {"list", ""})
    public String list(SpeakerVideo video, HttpServletRequest request, HttpServletResponse response, Model model) {
        video.setSpeakerId(UserUtils.getUser().getId());
        Page<SpeakerVideo> page = speakerVideoService.findPage(new Page<>(request, response), video);
        model.addAttribute("page", page);
        model.addAttribute("totalCount", page.getCount());
        model.addAttribute("maxLimit", LiveEnv.getVediotLimit());
        return "modules/speaker/videoList";
    }

    @RequestMapping(value = "form")
    public String form(@ModelAttribute("video") SpeakerVideo video, Model model) {
        return "modules/speaker/videoForm";
    }

    @RequestMapping(value = "save")
    public String save(SpeakerVideo video, Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (!beanValidator(model, video)) {
            return form(video, model);
        }
        SysFile sysFile = sysFileService.save(video.getFileId(), SysFileUtils.TypeEnum.SPEAKER_VIDEO);
        video.setFileId(sysFile.getId());
        video.setName(sysFile.getOriginalName());
        video.setStatus(AuditStatusEnum.AUDITING.getCode());
        speakerVideoService.save(video);
        addMessage(redirectAttributes, "保存视频'" + video.getName() + "'成功");
        return "redirect:/speaker/video/?repage";
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Map<String, String> delete(@RequestParam("videoIds") String videoIds, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        try {
            String idsArr[] = videoIds.split(",");
            boolean canDel = speakerVideoService.canDelete(idsArr);
            if (!canDel) {
                map.put("status", "1");
                map.put("msg", "视频正在直播间中使用，不可删除");
            } else {
                speakerVideoService.delSpeakerVideoForIds(idsArr);
                map.put("status", "0");
            }
        } catch (Exception e) {
            map.put("status", "1");
            logger.error("删除视频失败!", e);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "asyncCheckVideoName", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, String> asyncCheckVideoName(@RequestBody SpeakerVideo speakerVideo, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        try {
            speakerVideo = speakerVideoService.getSpeakerVideoForObj(speakerVideo);
            if (speakerVideo == null) {
                map.put("status", "0");
            } else {
                map.put("status", "1");
            }
        } catch (Exception e) {
            map.put("status", "2");
            logger.error("校验视频名称失败!", e);
            e.printStackTrace();
        }
        return map;
    }
}
