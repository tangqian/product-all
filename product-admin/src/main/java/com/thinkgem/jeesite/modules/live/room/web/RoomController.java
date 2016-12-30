package com.thinkgem.jeesite.modules.live.room.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.live.base.enums.LiveRoomStatusEnum;
import com.thinkgem.jeesite.modules.live.base.utils.CommonUtils;
import com.thinkgem.jeesite.modules.live.base.utils.NewHttpClient;
import com.thinkgem.jeesite.modules.live.audience.service.AudienceRegisterService;
import com.thinkgem.jeesite.modules.live.base.config.LiveEnv;
import com.thinkgem.jeesite.modules.live.report.service.ReportUserPresentService;
import com.thinkgem.jeesite.modules.live.report.service.ReportVisitorPresentService;
import com.thinkgem.jeesite.modules.live.room.entity.Room;
import com.thinkgem.jeesite.modules.live.room.service.RoomService;
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
import java.util.List;
import java.util.Map;

/**
 * @author tangqian
 * @version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/live/room")
public class RoomController extends BaseController {

    @Resource
    private RoomService roomService;

    @Resource
    private AudienceRegisterService audienceRegisterService;

    @Resource
    private ReportUserPresentService reportUserPresentService;

    @Resource
    private ReportVisitorPresentService reportVisitorPresentService;

    @ModelAttribute("room")
    public Room get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return roomService.get(id);
        } else {
            return new Room();
        }
    }

    @RequiresPermissions("live:room:view")
    @RequestMapping(value = {"list", ""})
    public String list(Room room, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Room> page = roomService.findPage(new Page<>(request, response), room);
        Map<String, Integer> maps = getRoomUserCount();
        for (Room r : page.getList()) {
            r.setOnLineAudienceCount(maps.get(r.getId()));
            r.setRegisterAudienceCount(audienceRegisterService.countAudienceRegisterForRoomId(r.getId()));// 登记观众
            r.setPresentAudienceCount(reportUserPresentService.countPresentAudienceForRoomId(r.getId())); // 出席观众
            r.setVisitorCount(reportVisitorPresentService.countRoomVisitorForRoomId(r.getId()));// 游客
        }
        model.addAttribute("page", page);
        return "modules/live/room/roomList";
    }

    @RequiresPermissions("live:room:view")
    @RequestMapping(value = "form")
    public String form(Room room, Model model) {
        getIndustryIds(room);
        model.addAttribute("room", room);
        return "modules/live/room/roomForm";
    }

    private void getIndustryIds(Room room) {
        List<String> idList = Lists.newArrayList();
        String industry = room.getIndustry();
        if (StringUtils.isNotBlank(industry)) {
            String industryIds[] = industry.split(",");
            for (int j = 1, len = industryIds.length; j < len; j++) {
                idList.add(industryIds[j]);
            }
        }
        room.setIndustriesIdList(idList);
    }

    @RequiresPermissions("live:room:edit")
    @RequestMapping(value = "save")
    public String save(Room room, Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (!beanValidator(model, room)) {
            return form(room, model);
        }
        roomService.save(room);
        addMessage(redirectAttributes, "保存直播'" + room.getName() + "'成功");
        return "redirect:" + adminPath + "/live/room/?repage";
    }

    @RequiresPermissions("live:room:delete")
    @RequestMapping(value = "delete")
    public String delete(Room room, RedirectAttributes redirectAttributes) {
        roomService.delete(room);
        addMessage(redirectAttributes, "删除直播成功");
        return "redirect:" + adminPath + "/live/room/?repage";
    }

    private Map<String, Integer> getRoomUserCount() {
        Map<String, Integer> roomIdCounts = Maps.newHashMap();
        try {
            JSONObject json = NewHttpClient.get(LiveEnv.getWebSite() + "/api/nio/onlineCount", JSONObject.class);
            if (json != null) {
                JSONArray jsonArray = (JSONArray) json.get("data");
                jsonArray.forEach(d -> {
                    JSONObject obj = (JSONObject) d;
                    roomIdCounts.put(obj.getString("roomId"), obj.getIntValue("count"));
                });
            }
        } catch (Exception e) {
            logger.error("调用直播系统-用户在线人数接口失败！", e);
        }
        return roomIdCounts;
    }

    @RequiresPermissions("live:room:check")
    @RequestMapping("check")
    public String check(Room room, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String redirectUrl = LiveEnv.getWebSite() + "/live/" + room.getId();
        String ak = CommonUtils.encode("admin", "jeesite", redirectUrl);

        StringBuilder sb = new StringBuilder(LiveEnv.getWebSite()).append("/api/admin/login?redirectUrl=")
                .append(redirectUrl).append("&ak=").append(ak);
        response.sendRedirect(sb.toString());
        return null;
    }

    @RequiresPermissions("live:room:view")
    @RequestMapping(value = "statusForm")
    public String statusForm(Room room, Model model) {
        model.addAttribute("room", room);
        model.addAttribute("statusOptions", LiveRoomStatusEnum.getOptions());
        return "modules/live/room/roomStatusForm";
    }

    @RequiresPermissions("live:room:edit")
    @ResponseBody
    @RequestMapping(value = "updateStatus")
    public String saveStatus(Room room, Model model) {
        roomService.updateStatus(room);
        return "true";
    }

    @RequiresPermissions("live:room:top")
    @RequestMapping("top")
    @ResponseBody
    public Map<String, String> top(@RequestParam(value = "id[]") Integer ids[], Integer isTop) {
        Map<String, String> map = new HashMap<>();
        Room room = new Room();
        room.setIsTop(isTop);
        room.setTopSort(100);
        for (Integer id : ids) {
            room.setId(String.valueOf(id));
            roomService.updateTop(room);
        }
        map.put("result", "success");
        return map;
    }

    @RequiresPermissions("live:room:top")
    @RequestMapping("topSort")
    @ResponseBody
    public Map<String, String> topSort(@RequestParam(value = "ids[]") Integer ids[], @RequestParam(value = "orders[]") String orders[]) {
        Map<String, String> map = new HashMap<>();
        Room room = new Room();
        for (int i = 0; i < ids.length; i++) {
            if (NumberUtils.isDigits(orders[i])) {
                room.setId(String.valueOf(ids[i]));
                room.setTopSort(Integer.valueOf(orders[i]));
                roomService.updateTop(room);
            }
        }
        map.put("result", "success");
        return map;
    }


}
