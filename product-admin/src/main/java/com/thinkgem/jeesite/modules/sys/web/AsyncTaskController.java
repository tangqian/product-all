package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.AsyncTask;
import com.thinkgem.jeesite.modules.sys.service.AsyncTaskService;

@Controller
@RequestMapping(value = "${adminPath}/sys/asyncTask")
public class AsyncTaskController extends BaseController {

	@Autowired
	private AsyncTaskService asyncTaskService;

	@ModelAttribute
	public AsyncTask get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return asyncTaskService.get(id);
		} else {
			return new AsyncTask();
		}
	}
	
	@RequiresPermissions("sys:asyncTask:view")
	@RequestMapping(value = {"list", ""})
	public String list(AsyncTask asyncTask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AsyncTask> page = asyncTaskService.findPage(new Page<AsyncTask>(request, response), asyncTask);

		model.addAttribute("page", page);
		return "modules/sys/asyncTaskList";
	}
	
/*	@RequiresPermissions("sys:asyncTask:view")
	@RequestMapping(value = "form")
	public String form(AsyncTask asyncTask, Model model) {
		model.addAttribute("asyncTask", asyncTask);
		return "modules/sys/asyncTaskForm";
	}

	@RequiresPermissions("sys:asyncTask:edit")
	@RequestMapping(value = "save")
	public String save(AsyncTask asyncTask, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, asyncTask)) {
			return form(asyncTask, model);
		}
		asyncTaskService.save(asyncTask);
		addMessage(redirectAttributes, "保存任务'" + asyncTask.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/asyncTask/?repage";
	}


	@RequiresPermissions("sys:asyncTask:delete")
	@RequestMapping(value = "delete")
	public String delete(AsyncTask asyncTask, RedirectAttributes redirectAttributes) {
		asyncTaskService.delete(asyncTask);
		addMessage(redirectAttributes, "删除任务成功");
		return "redirect:" + adminPath + "/sys/asyncTask/?repage";
	}*/
	
}
