package com.ofweek.live.web.speaker.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tangqian
 *
 */
@Controller
@RequestMapping(value = "/speaker")
public class SpeakerController {
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/speaker/index";
	}

}
