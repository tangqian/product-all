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
import com.ofweek.live.core.modules.room.entity.RoomSpeech;
import com.ofweek.live.core.modules.room.service.RoomSpeechService;

/**
 * 
 * @author tangqian
 */
@Controller
@RequestMapping(value = "/room/speech")
public class RoomSpeechController extends BaseController {
	
    @Resource
    private RoomSpeechService roomSpeechService;
	
    @ModelAttribute("roomSpeech")
    public RoomSpeech get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return roomSpeechService.get(id);
        }else{
            return new RoomSpeech();
        }
    }
    
	@RequestMapping(value = "delete")
	public String delete(RoomSpeech room, RedirectAttributes redirectAttributes) {
		roomSpeechService.delete(room);
		redirectAttributes.addAttribute("roomId", room.getRoomId());
		return "redirect:/room/dataManage/?repage";
	}
	
    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, String> save(@RequestParam("roomId") String roomId, @RequestParam("objIds") String objIds, HttpServletResponse response) {
    	Map<String, String> map = new HashMap<>();
    	try {
    		String sppechId[] = objIds.split(",");
    		for (int index = 0, len = sppechId.length; index < len; index++) {
    			roomSpeechService.save(new RoomSpeech(roomId, sppechId[index]));
			}
			map.put("status", "0");
		} catch (Exception e) {
			map.put("status", "1");
			logger.error("保存直播房间PPT失败!", e);
			e.printStackTrace();
		}
    	return map;
    }
    
}