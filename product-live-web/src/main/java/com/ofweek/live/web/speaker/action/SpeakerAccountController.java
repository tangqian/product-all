package com.ofweek.live.web.speaker.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ofweek.live.core.common.web.BaseController;
import com.ofweek.live.core.modules.speaker.entity.Speaker;
import com.ofweek.live.core.modules.speaker.service.SpeakerService;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.service.SystemService;
import com.ofweek.live.core.modules.sys.service.UserService;
import com.ofweek.live.core.modules.sys.utils.UserUtils;

/**
 * @author tangqian
 *
 */
@Controller
@RequestMapping(value = "/speaker/account")
public class SpeakerAccountController extends BaseController{
	
    @Resource
    private SpeakerService speakerService;
    
    @Resource
    private UserService userService;
    
	@RequestMapping(value = "contacts")
	public String contacts(@ModelAttribute("speaker") Speaker speaker, Model model) {
		//speaker.setId(getUserId());
		model.addAttribute("speaker", speakerService.getLiveSpeakerForId());
		return "modules/speaker/account/contacts/contactsForm";
	}
	
    @RequestMapping(value = "save")
    public String save(Speaker speaker, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        if (!beanValidator(model, speaker)){
            return contacts(speaker, model);
        }
        try {
			speakerService.save(speaker);
			redirectAttributes.addFlashAttribute("result", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("result", "error");
			logger.error("更新用户信息失败!", e);
			e.printStackTrace();
		}
        return "redirect:/speaker/account/contacts/?repage";
    }
    
	@RequestMapping(value = "security")
	public String security(@ModelAttribute("user") User user, Model model) {
		user.setId(getUserId());
		model.addAttribute("user", userService.get(user));
		model.addAttribute("result", (String) model.asMap().get("result"));
		return "modules/speaker/account/security/securityManage";
	}
	
	@RequestMapping(value = "emailForm")
	public String emailForm(@ModelAttribute("user") User user, Model model) {
		user.setId(getUserId());
		model.addAttribute("user", user);
		return "modules/speaker/account/security/emailForm";
	}
	
	@RequestMapping(value = "passwordForm")
	public String passwordForm(@ModelAttribute("user") User user, Model model) {
		user.setId(getUserId());
		model.addAttribute("user", userService.get(user));
		return "modules/speaker/account/security/passwordForm";
	}
	
    @RequestMapping(value = "saveUser")
    public String saveUser(User user, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        if (!beanValidator(model, user)){
            return emailForm(user, model);
        }
       try {
    	   userService.save(user);
           redirectAttributes.addFlashAttribute("result", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("result", "error");
			logger.error("更新用户信息失败!", e);
			e.printStackTrace();
		}
       return "redirect:/speaker/account/security/?repage";
    }
	
    @RequestMapping(value = "verifyOriginalPass", method = {RequestMethod.POST })
    @ResponseBody
	public Boolean verifyOriginalPass(@RequestBody String password, HttpServletRequest request) {
    	Boolean flag = SystemService.validatePassword(password.split("=")[1], userService.get(getUserId()).getPassword());
		if (flag) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	private static String getUserId() {
		return UserUtils.getUser().getId();
	}
}