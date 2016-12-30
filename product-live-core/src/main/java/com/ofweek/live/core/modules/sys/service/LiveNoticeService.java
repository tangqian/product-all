package com.ofweek.live.core.modules.sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ofweek.live.core.common.utils.DateUtils;
import com.ofweek.live.core.common.utils.GeneratorTemplateUtils;
import com.ofweek.live.core.modules.audience.entity.AudienceRegister;
import com.ofweek.live.core.modules.audience.service.AudienceRegisterService;
import com.ofweek.live.core.modules.sys.entity.SysEmail;

@Service
public class LiveNoticeService {

	@Resource
	private AudienceRegisterService audienceRegisterService;
	
	@Resource
    private SysEmailService sysEmailService;
	
	@Resource
	private ExecutorService executorService;
	
	public void sendEmail() {
		executorService.execute(new ExecuteTask(DateUtils.formatDateTime(new Date()) + " OFWeek直播邮件通知任务...", audienceRegisterService.findSendEmailAudienceRegister()));
	}
	
	class ExecuteTask implements Runnable {
		
		private Logger logger = LoggerFactory.getLogger(ExecuteTask.class);
		
		private static final String templatePath = "/email/template.ftl";
		
		private static final int norm = 30 * 60 * 1000;
		
		private String taskName;
		
		private List<AudienceRegister> audiences;
		
		protected ExecuteTask(String taskName, List<AudienceRegister> audiences) {
			this.taskName = taskName;
			this.audiences = audiences;
		}

		@Override
		public void run() {
			try {
				logger.info(" taskName = {} ", taskName);
				SysEmail email = new SysEmail();
				AudienceRegister audienceRegister = new AudienceRegister();
				audiences.forEach(audience -> {
					if (isNotice(audience)) {
						email.getTemplate().setRoomId(audience.getRoom().getId());
						email.getTemplate().setStartTime(audience.getRoom().getStartTime());
						email.getTemplate().setEndTime(audience.getRoom().getEndTime());
						email.getTemplate().setName(audience.getRoom().getName());
						email.getTemplate().setDetail(audience.getRoom().getDetail());
						email.getTemplate().setCompany(audience.getCompany());
						email.setSubject(audience.getRoom().getName());
						email.setSubjectId(audience.getId());
						email.setReceiver(audience.getUser().getEmail());
						email.setTemplatePath(templatePath);
						email.setContent(GeneratorTemplateUtils.generateTemplateText(email, setTemplateText(email)));
						sysEmailService.saveAndSend(email);
						audienceRegister.setId(audience.getId());
						audienceRegister.setIsSentEmail(SysEmail.SEND_SUCCESS);
						audienceRegisterService.save(audienceRegister);
					}
				});

			} catch (Exception e) {
				logger.error(" Failed to execute message notification task = {} ", e.getMessage());
				e.printStackTrace();
			}
		}
		
		private Boolean isNotice(AudienceRegister audience) {
			if (audience != null) {
				long time = audience.getRoom().getStartTime().getTime() - new Date().getTime();
				if (time <= norm && time > 0) {
					return Boolean.TRUE;
				}
			}
			return Boolean.FALSE;
		}
		
		private Map<String, Object> setTemplateText(SysEmail entity) {
			Map<String, Object> model = Maps.newHashMap();
			model.put("startTime", entity.getTemplate().getStartTime());
			model.put("endTime", entity.getTemplate().getEndTime());
			model.put("name", entity.getTemplate().getName());
			model.put("detail", entity.getTemplate().getDetail());
			model.put("company", entity.getTemplate().getCompany());
			model.put("roomId", entity.getTemplate().getRoomId());
			return model;
		}
	}

}