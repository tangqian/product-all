package com.ofweek.live.web.room.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.room.entity.RoomData;
import com.ofweek.live.core.modules.room.service.RoomDataService;

/**
 * 
 * @author tangqian
 */
@Controller
@RequestMapping(value = "/room/data")
public class RoomDataController extends BaseController {
	
    @Resource
    private RoomDataService roomDataService;
	
    @ModelAttribute("roomData")
    public RoomData get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return roomDataService.get(id);
        }else{
            return new RoomData();
        }
    }
    
	@RequestMapping(value = "delete")
	public String delete(RoomData room, RedirectAttributes redirectAttributes) {
		roomDataService.delete(room);
		redirectAttributes.addAttribute("roomId", room.getRoomId());
		return "redirect:/room/dataManage/?repage";
	}
	
    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, String> save(@RequestParam("roomId") String roomId, @RequestParam("objIds") String objIds, HttpServletResponse response) {
    	Map<String, String> map = new HashMap<>();
    	try {
    		String dataId[] = objIds.split(",");
    		for (int index = 0, len = dataId.length; index < len; index++) {
    			roomDataService.save(new RoomData(roomId, dataId[index]));
			}
			map.put("status", "0");
		} catch (Exception e) {
			map.put("status", "1");
			logger.error("保存直播房间资料失败!", e);
			e.printStackTrace();
		}
    	return map;
    }
    
    @RequestMapping(value = "sort", method = {RequestMethod.POST })
    @ResponseBody
    public Map<String, String> sort(@RequestBody RoomData room, HttpServletResponse response) {
    	Map<String, String> map = new HashMap<>();
    	try {
    		roomDataService.save(room);
    		map.put("status", "0");
    	} catch (Exception e) {
    		map.put("status", "1");
    		logger.error("直播房间资料排序失败!", e);
    		e.printStackTrace();
    	}
    	return map;
    }

	
}