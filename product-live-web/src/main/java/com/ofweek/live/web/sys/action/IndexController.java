package com.ofweek.live.web.sys.action;

import com.google.common.collect.Lists;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.service.RoomService;
import com.ofweek.live.core.modules.rpc.common.RpcService;
import com.ofweek.live.core.modules.rpc.common.dto.LiveVo;
import com.ofweek.live.core.modules.sys.entity.HomeBanner;
import com.ofweek.live.core.modules.sys.service.HomeBannerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tangqian on 2016/9/6.
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private HomeBannerService homeBannerService;

    @Resource
    private RoomService roomService;

    @Resource
    private RpcService rpcService;

    private static final int pageSize = 10;

    /**
     * 直播系统首页
     */
    @RequestMapping(value = "")
    public String index(Model model) {
        List<LiveVo> notOverLives = Lists.newArrayList();
        roomService.findUnendIndexList(null).forEach(room -> notOverLives.add(new LiveVo(room)));

        notOverLives.sort((v1, v2) -> {
            int result = v1.getStatus().compareTo(v2.getStatus());
            return result != 0 ? result : v1.getStartTime().compareTo(v2.getStartTime());
        });

        model.addAttribute("notOverLives", notOverLives);
        model.addAttribute("homeBanners", homeBannerService.findList(new HomeBanner()));
        return "modules/sys/index";
    }

    @RequestMapping("/api/overLives")
    @ResponseBody
    public List<LiveVo> overLives(@RequestParam(required = true) Integer pageNo) {
        List<LiveVo> overLives = Lists.newArrayList();
        roomService.findEndIndexList(new Room()).forEach(room -> overLives.add(new LiveVo(room)));
        overLives.sort((v1, v2) -> v2.getStartTime().compareTo(v1.getStartTime()));

        int fromIndex = Math.min((pageNo - 1) * pageSize, overLives.size());
        int toIndex = Math.min(pageNo * pageSize, overLives.size());
        return overLives.subList(fromIndex, toIndex);
    }

    @RequestMapping(value = "/index/help")
    public String help() {
        return "modules/sys/help";
    }

    @RequestMapping(value = "/index/contact")
    public String contact() {
        return "modules/sys/contact";
    }
}
