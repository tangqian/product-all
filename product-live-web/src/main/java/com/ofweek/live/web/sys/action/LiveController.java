package com.ofweek.live.web.sys.action;

import com.ofweek.live.core.common.config.VersionEnv;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.audience.entity.Audience;
import com.ofweek.live.core.modules.audience.service.AudienceService;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.core.modules.room.enums.RoomWatchModeEnum;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDao;
import com.ofweek.live.core.modules.sys.dto.AccessVo;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.entity.Visitor;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.core.modules.sys.service.VisitorService;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tangqian on 2016/9/28.
 */
@Controller
public class LiveController extends BaseController {

    @Resource
    private RoomDao roomDao;

    @Resource
    private SpeakerDao speakerDao;

    @Resource
    private AudienceService audienceService;

    @Resource
    private UserService userService;

    @Resource
    private VisitorService visitorService;

    @RequestMapping({"live/{roomId}"})
    public String live(@PathVariable("roomId") String roomId, @RequestParam(required = false) Integer mode,
                             HttpServletRequest request, HttpServletResponse response, Model model) {
        if (!RoomModeEnum.isPreview(mode)) {
            mode = RoomModeEnum.RELEASE.getCode();
        }
        model.addAttribute("mode", mode);


        Room room = roomDao.get(roomId);
        roomDao.updatePv(room);
        room.setSpeaker(speakerDao.get(room.getSpeakerId()));
        model.addAttribute("room", room);

        AccessVo vo = null;
        User user = UserUtils.getUser();
        if (user == null) {
            String account = com.ofweek.live.core.modules.sys.utils.WebUtils.getAccountFromCookie(request);
            if (StringUtils.isNotBlank(account)) {
                Audience audience = audienceService.saveFromSmartlifein(account);
                if (audience != null) {
                    user = audience.getUser();
                }
            }
        }

        if (user == null) {
            if (RoomWatchModeEnum.requireUser(room.getWatchMode())) {
                WebUtils.saveRequest(request);
                return "redirect:http://www.smartlifein.com/login.html?returnurl=http://live.smartlifein.com/live/" + roomId;
            }
            Visitor visitor = visitorService.save(request, response);
            vo = new AccessVo(visitor);
        } else {
            vo = new AccessVo(user);
            model.addAttribute("user", userService.getGeneralUser(user.getId()));
        }
        model.addAttribute("vo", vo);
        model.addAttribute("cssIndex", VersionEnv.getCssIndex());
        model.addAttribute("jsVendor", VersionEnv.getJsVendor());
        model.addAttribute("jsIndex", VersionEnv.getJsIndex());
        model.addAttribute("headerLive", roomDao.findHeaderLiveList());
        return "modules/sys/live";
    }
}
