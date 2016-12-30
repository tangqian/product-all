package com.ofweek.live.web.speaker.action;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.persistence.Page;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.speaker.entity.SpeakerData;
import com.ofweek.live.core.modules.speaker.service.SpeakerDataService;
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
@RequestMapping(value = "/speaker/data")
public class SpeakerDataController extends BaseController {

    @Resource
    private SpeakerDataService speakerDataService;

    @ModelAttribute("data")
    public SpeakerData get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return speakerDataService.get(id);
        } else {
            return new SpeakerData();
        }
    }

    @RequestMapping(value = {"list", ""})
    public String list(SpeakerData data, HttpServletRequest request, HttpServletResponse response, Model model) {
        data.setSpeakerId(UserUtils.getUser().getId());
        Page<SpeakerData> page = speakerDataService.findPage(new Page<>(request, response), data);
        model.addAttribute("page", page);
        model.addAttribute("totalCount", page.getCount());
        model.addAttribute("maxLimit", LiveEnv.getDataLimit());
        return "modules/speaker/dataList";
    }

    @RequestMapping(value = "form")
    public String form(@ModelAttribute("data") SpeakerData data, Model model) {
        return "modules/speaker/dataForm";
    }

    @RequestMapping(value = "save")
    public String save(SpeakerData data, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        if (!beanValidator(model, data)) {
            return form(data, model);
        }
        SysFile sysFile = sysFileService.save(data.getFileId(), SysFileUtils.TypeEnum.SPEAKER_DATA);
        data.setFileId(sysFile.getId());
        data.setName(sysFile.getOriginalName());
        data.setStatus(AuditStatusEnum.AUDITING.getCode());
        speakerDataService.save(data);
        addMessage(redirectAttributes, "保存下载资料'" + data.getName() + "'成功");
        return "redirect:/speaker/data/?repage";
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public Map<String, String> delete(@RequestParam("dataIds") String dataIds, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        try {
            String idsArr[] = dataIds.split(",");
            boolean canDel = speakerDataService.canDelete(idsArr);
            if (!canDel) {
                map.put("status", "1");
                map.put("msg", "资料正在直播间中使用，不可删除");
            } else {
                speakerDataService.delSpeakerDataForIds(idsArr);
                map.put("status", "0");
            }
        } catch (Exception e) {
            map.put("status", "1");
            logger.error("删除资料失败!", e);
        }
        return map;
    }

    @RequestMapping(value = "asyncCheckDataName", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, String> asyncCheckDataName(@RequestBody SpeakerData speakerData, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        try {
            speakerData = speakerDataService.getSpeakerDataForObj(speakerData);
            if (speakerData == null) {
                map.put("status", "0");
            } else {
                map.put("status", "1");
            }
        } catch (Exception e) {
            map.put("status", "2");
            logger.error("校验资料名称失败!", e);
            e.printStackTrace();
        }
        return map;
    }

}

