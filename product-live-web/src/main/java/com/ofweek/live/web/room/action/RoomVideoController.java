package com.ofweek.live.web.room.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.room.entity.RoomVideo;
import com.ofweek.live.core.modules.room.service.RoomVideoService;

/**
 * 
 * @author tangqian
 */
@Controller
@RequestMapping(value = "/room/video")
public class RoomVideoController extends BaseController {
	
    @Resource
    private RoomVideoService roomVideoService;
	
    @ModelAttribute("roomVideo")
    public RoomVideo get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return roomVideoService.get(id);
        }else{
            return new RoomVideo();
        }
    }
    
	@RequestMapping(value = "delete")
	public String delete(RoomVideo room, RedirectAttributes redirectAttributes) {
		roomVideoService.delete(room);
		redirectAttributes.addAttribute("roomId", room.getRoomId());
		return "redirect:/room/dataManage/?repage";
	}
	
    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, String> save(@RequestParam("roomId") String roomId, @RequestParam("objIds") String objIds, HttpServletResponse response) {
    	Map<String, String> map = new HashMap<>();
    	try {
    		String videoId[] = objIds.split(",");
    		for (int index = 0, len = videoId.length; index < len; index++) {
    			roomVideoService.save(new RoomVideo(roomId, videoId[index]));
			}
			map.put("status", "0");
		} catch (Exception e) {
			map.put("status", "1");
			logger.error("保存直播房间视频失败!", e);
			e.printStackTrace();
		}
    	return map;
    }
    
}