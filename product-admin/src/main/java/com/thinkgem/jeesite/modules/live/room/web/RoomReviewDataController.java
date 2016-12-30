package com.thinkgem.jeesite.modules.live.room.web;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.live.base.config.LiveEnv;
import com.thinkgem.jeesite.modules.live.base.utils.NewHttpClient;
import com.thinkgem.jeesite.modules.live.room.entity.RoomReviewData;
import com.thinkgem.jeesite.modules.live.room.service.RoomReviewDataService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangqian
 * @version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/live/review")
public class RoomReviewDataController extends BaseController {

    @Resource
    private RoomReviewDataService roomReviewDataService;

    @ModelAttribute("roomReviewData")
    public RoomReviewData get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return roomReviewDataService.get(id);
        } else {
            return new RoomReviewData();
        }
    }

    @RequiresPermissions("live:review:view")
    @RequestMapping(value = {"list", ""})
    public String list(RoomReviewData roomReviewData, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<RoomReviewData> page = roomReviewDataService.findPage(new Page<RoomReviewData>(request, response), roomReviewData);
        model.addAttribute("page", page);
        return "modules/live/room/reviewList";
    }

    @RequestMapping(value = "data/form")
    public String form(RoomReviewData roomReviewData, Model model) {
        try {
            String sJson = NewHttpClient.get(LiveEnv.getWebSite() + "/api/cloud/getMediaInfo?mediaId=" + roomReviewData.getMediaId(), true);
            if (sJson != null) {
                JSONObject response = JSONObject.parseObject(sJson);
                JSONObject data = response.getJSONObject("data");
                if (data != null) {
                    roomReviewData.setSourceUrl(data.getString("sourceUrl"));
                    roomReviewData.setCoverPage(data.getString("coverPage"));
                }
            }
        } catch (Exception e) {
            logger.error("调用直播系统-查询媒资分发信息接口失败！", e);
        }
        model.addAttribute("roomReviewData", roomReviewData);
        return "modules/live/room/reviewDataForm";
    }

    @RequestMapping(value = "data/list")
    public String data(RoomReviewData roomReviewData, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            NewHttpClient.get(LiveEnv.getWebSite() + "/api/cloud/syncBCloud", true);
        } catch (Exception e) {
            logger.error("调用直播系统-从百度云上同步获取回放视频接口失败！", e);
        }
        Page<RoomReviewData> page = roomReviewDataService.findReviewDataList(new Page<RoomReviewData>(request, response), roomReviewData);
        model.addAttribute("page", page);
        return "modules/live/room/reviewDataList";
    }

    @RequiresPermissions("live:roomReviewData:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, String> save(RoomReviewData roomReviewData, Model model, RedirectAttributes redirectAttributes) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        try {
            roomReviewDataService.save(roomReviewData);
            map.put("result", "success");
        } catch (Exception e) {
            map.put("result", "error");
            e.printStackTrace();
        }
        return map;
    }

    @RequiresPermissions("live:roomReviewData:edit")
    @RequestMapping("sort")
    @ResponseBody
    public Map<String, String> sort(@RequestParam(value = "id[]") Integer id[], @RequestParam(value = "order[]") String order[]) {
        Map<String, String> map = new HashMap<String, String>();
        RoomReviewData entity = new RoomReviewData();
        for (int i = 0; i < id.length; i++) {
            if (NumberUtils.isDigits(order[i])) {
                entity.setId(String.valueOf(id[i]));
                entity.setSort(Integer.valueOf(order[i]));
                roomReviewDataService.save(entity);
            }
        }
        map.put("result", "success");
        return map;
    }

    @RequiresPermissions("live:roomReviewData:delete")
    @RequestMapping(value = "delete")
    public String delete(RoomReviewData roomReviewData, RedirectAttributes redirectAttributes) {
        roomReviewDataService.delete(roomReviewData);
        addMessage(redirectAttributes, "删除成功");
        redirectAttributes.addAttribute("roomId", roomReviewData.getRoomId());
        return "redirect:" + adminPath + "/live/review/data/list/?repage";
    }

    @RequiresPermissions("live:roomReviewData:edit")
    @RequestMapping(value = "disable")
    public String disable(RoomReviewData roomReviewData, RedirectAttributes redirectAttributes) {
        roomReviewData.setStatus(0);
        roomReviewDataService.save(roomReviewData);
        addMessage(redirectAttributes, "禁用成功");
        redirectAttributes.addAttribute("roomId", roomReviewData.getRoomId());
        return "redirect:" + adminPath + "/live/review/data/list/?repage";
    }

    @RequiresPermissions("live:roomReviewData:edit")
    @RequestMapping(value = "enable")
    public String enable(RoomReviewData roomReviewData, RedirectAttributes redirectAttributes) {
        roomReviewData.setStatus(1);
        roomReviewDataService.save(roomReviewData);
        addMessage(redirectAttributes, "启用成功");
        redirectAttributes.addAttribute("roomId", roomReviewData.getRoomId());
        return "redirect:" + adminPath + "/live/review/data/list/?repage";
    }

}
