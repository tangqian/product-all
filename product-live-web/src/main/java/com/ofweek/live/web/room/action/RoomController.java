package com.ofweek.live.web.room.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ofweek.live.core.common.persistence.Page;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.report.service.ReportUserPresentService;
import com.ofweek.live.core.modules.report.service.ReportVisitorPresentService;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.entity.RoomData;
import com.ofweek.live.core.modules.room.entity.RoomSpeech;
import com.ofweek.live.core.modules.room.entity.RoomVideo;
import com.ofweek.live.core.modules.room.service.RoomDataService;
import com.ofweek.live.core.modules.room.service.RoomService;
import com.ofweek.live.core.modules.room.service.RoomSpeechService;
import com.ofweek.live.core.modules.room.service.RoomVideoService;
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.core.modules.speaker.service.SpeakerDataService;
import com.ofweek.live.core.modules.speaker.service.SpeakerSpeechService;
import com.ofweek.live.core.modules.speaker.service.SpeakerVideoService;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * 
 * @author tangqian
 */
@Controller
@RequestMapping(value = "/room")
public class RoomController extends BaseController {
	
    @Resource
    private RoomService roomService;
	
    @Resource
    private RoomDataService roomDataService;
	
    @Resource
    private RoomVideoService roomVideoService;
	
    @Resource
    private RoomSpeechService roomSpeechService;
    
    @Resource
    private SpeakerDataService speakerDataService;
    
    @Resource
    private SpeakerVideoService speakerVideoService;
    
    @Resource
    private SpeakerSpeechService speakerSpeechService;
    
    @Resource
    private ReportVisitorPresentService reportVisitorPresentService;
    
	@Resource
	private ReportUserPresentService reportUserPresentService;
    
	@ModelAttribute("room")
	public Room get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return roomService.get(id);
		}else{
			return new Room();
		}
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(Room room, HttpServletRequest request, HttpServletResponse response, Model model) {
		room.setSpeaker(new Speaker(UserUtils.getUser().getId()));
		Page<Room> page = roomService.findPage(new Page<>(request, response), room);
		for (Room r : page.getList()) {
			r.setAudienceCount(reportUserPresentService.countAllAudienceForRoomId(r.getId()));// 观众数
			r.setVisitorCount(r.getPv());// 游客数
		}
        model.addAttribute("page", page);
		return "modules/room/roomList";
	}

	@RequestMapping(value = "form")
	public String form(@ModelAttribute("room") Room room, Model model) {
		return "modules/room/roomForm";
	}
	
	@RequestMapping(value = "save")
	public String save(Room room, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, room)){
			return form(room, model);
		}
		roomService.save(room);
		addMessage(redirectAttributes, "保存直播'" + room.getName() + "'成功");
		return "redirect:/room/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(Room room, RedirectAttributes redirectAttributes) {
		roomService.delete(room);
		addMessage(redirectAttributes, "删除直播成功");
		return "redirect:/room/?repage";
	}
	
	@RequestMapping(value = "dataManage")
	public String dataManage(@RequestParam("roomId") String roomId, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("roomId", roomId);
		model.addAttribute("status", roomService.get(roomId).getStatus());
		setRoomDataList(roomId, model);
		setRoomVideoList(roomId, model);
		setRoomSpeechList(roomId, model);
		return "modules/room/dataManage";
	}
	
	private void setRoomSpeechList(String roomId, Model model) {
		List<RoomSpeech> sList = roomSpeechService.findRoomSpeechForRoomId(roomId);
		model.addAttribute("sList", sList);
		setChooseSpeakerSpeech(sList, model);
	}
	
	private void setChooseSpeakerSpeech(List<RoomSpeech> sList, Model model) {
		List<String> sSourceIds = new ArrayList<String>();
		sList.forEach(sppech -> {
			sSourceIds.add(sppech.getSourceId());
		});
        String speechIds = String.join(",", sSourceIds);
        model.addAttribute("chooseRoomSpeechList", speakerSpeechService.findSpeakerSpeechForConditions(speechIds));
	}
	
	private void setRoomVideoList(String roomId, Model model) {
		List<RoomVideo> vList = roomVideoService.findRoomVideoForRoomId(roomId);
		model.addAttribute("vList", vList);
		setChooseSpeakerVideo(vList, model);
	}
	
	private void setChooseSpeakerVideo(List<RoomVideo> vList, Model model) {
		List<String> vSourceIds = new ArrayList<String>();
		vList.forEach(video -> {
			vSourceIds.add(video.getSourceId());
		});
        String videoIds = String.join(",", vSourceIds);
        model.addAttribute("chooseRoomVideoList", speakerVideoService.findSpeakerVideoForConditions(videoIds));
	}
	
	private void setRoomDataList(String roomId, Model model) {
		List<RoomData> dList = roomDataService.findRoomDataForRoomId(roomId);
		model.addAttribute("dList", dList);
		setChooseSpeakerData(dList, model);
	}
	
	private void setChooseSpeakerData(List<RoomData> dList, Model model) {
		List<String> dSourceIds = new ArrayList<String>();
		dList.forEach(data -> {
			dSourceIds.add(data.getSourceId());			
		});
        String dataIds = String.join(",", dSourceIds);
        model.addAttribute("chooseRoomDataList", speakerDataService.findSpeakerDataForConditions(dataIds));
	}
	
}
