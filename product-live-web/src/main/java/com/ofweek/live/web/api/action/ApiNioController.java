package com.ofweek.live.web.api.action;

import com.google.common.collect.Lists;
import com.ofweek.live.nio.handlers.api.NioService;
import com.ofweek.live.web.api.action.dto.RoomOnlineCountDto;
import com.ofweek.live.web.api.common.HttpCommonResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/nio")
public class ApiNioController extends ApiBaseController {

    @Resource
    private NioService nioService;

    @RequestMapping(value = {"onlineCount"})
    public HttpCommonResponse<Object> onlineCount(@RequestParam(required = false) String roomId) {
        List<RoomOnlineCountDto> data = Lists.newArrayList();
        if (roomId != null) {
            data.add(new RoomOnlineCountDto(roomId, nioService.getOnlineUserCount(roomId)));
        } else {
            Map<String, Integer> counts = nioService.getOnlineUserCount();
            counts.forEach((k, v) -> data.add(new RoomOnlineCountDto(k, v)));
        }
        HttpCommonResponse<Object> result = new HttpCommonResponse<>();
        result.setData(data);
        return result;
    }

    @RequestMapping(value = {"noticeRoomStatus"})
    public HttpCommonResponse noticeRoomStatus(@RequestParam(required = true) String roomId) {
        nioService.noticeRoomStatus(roomId);
        return new HttpCommonResponse<>();
    }

    @RequestMapping(value = {"noticeBlacklist"})
    public HttpCommonResponse noticeBlacklist(@RequestParam(required = true) String id) {
        nioService.noticeBlacklist(id);
        return new HttpCommonResponse<>();
    }

}
