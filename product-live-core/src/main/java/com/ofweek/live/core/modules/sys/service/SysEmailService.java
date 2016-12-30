package com.ofweek.live.core.modules.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.ofweek.live.core.common.beanvalidator.BeanValidators;
import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.GeneratorTemplateUtils;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.sys.dao.SysEmailDao;
import com.ofweek.live.core.modules.sys.entity.SysEmail;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

/**
 * 
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class SysEmailService extends CrudService<SysEmailDao, SysEmail> {

	@Resource
	private Validator validator;

	private static final SysEmail.EmailAccount ACCOUNT;

	static {
		ACCOUNT = new SysEmail.EmailAccount();
		String server = LiveEnv.getConfig("email.smtpServer");
		if (server == null || server.trim().isEmpty()) {
			throw new IllegalArgumentException("未配置邮箱服务器email.smtpServer");
		}
		ACCOUNT.setSmtpServer(server);
		ACCOUNT.setUser(LiveEnv.getConfig("email.user"));
		ACCOUNT.setPassword(LiveEnv.getConfig("email.password"));
		ACCOUNT.setAccount(LiveEnv.getConfig("email.account"));
		ACCOUNT.setAccountName(LiveEnv.getConfig("email.accountName"));
		ACCOUNT.setSmtpPort(Integer.valueOf(LiveEnv.getConfig("email.smtpPort")));
		ACCOUNT.setSslSmtpPort(LiveEnv.getConfig("email.sslSmtpPort"));
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(SysEmail entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.setId(SequenceUtils.getNextString("SysEmail"));
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}

	@Transactional(readOnly = false)
	public boolean saveAndSend(SysEmail entity){
		try{
			BeanValidators.validateWithException(validator, entity);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			for (String msg : list) {
				logger.error("数据验证失败!,原因[{}]", msg);
			}
			return false;
		}

		try {
			entity.setId(SequenceUtils.getNextString("SysEmail"));
			entity.preInsert();
			dao.insert(entity);
		} catch (Exception e) {
			logger.error("保存待发送邮件到数据库失败, receiver =" + entity.getReceiver(), e);
			return false;
		}

		return send(entity);
	}

	@Transactional(readOnly = false)
	public boolean sendById(String id) {
		SysEmail entity = dao.get(id);
		if (entity == null) {
			logger.error("email[id={}] not found", id);
			return false;
		}
		return send(entity);
	}

	/**
	 * @param entity
	 * @return
	 */
	private boolean send(SysEmail entity) {
		if(entity.isSendSuccess()){
			return true;
		}

		boolean isSuccess = false;
		try {
			doSend(entity, ACCOUNT);
			isSuccess = true;
		} catch (Exception e) {
			logger.error("发送邮件失败, id =" + entity.getId(), e);
		}

		if(isSuccess){
			entity.setStatus(SysEmail.SEND_SUCCESS);
		}else{
			entity.setStatus(SysEmail.SEND_FAIL);
		}

		dao.updateStatus(entity);
		return isSuccess;
	}

	/**
	 * @param entity
	 * @param emailAccount
	 * @throws EmailException
	 */
	private void doSend(SysEmail entity, SysEmail.EmailAccount emailAccount) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(emailAccount.getSmtpServer());
		email.setAuthentication(emailAccount.getUser(), emailAccount.getPassword());
		email.setSmtpPort(emailAccount.getSmtpPort());
		email.setSslSmtpPort(emailAccount.getSslSmtpPort());
		email.setHtmlMsg(entity.getContent());
		email.setCharset("UTF-8");
		email.setFrom(emailAccount.getAccount(), emailAccount.getAccountName());
		email.setSubject(entity.getSubject());
		email.addTo(entity.getReceiver());
		if (StringUtils.isNotBlank(entity.getCc())) {
			for (String cc : StringUtils.split(entity.getCc(), ",")) {
				email.addCc(cc);
			}
		}
		if (StringUtils.isNotBlank(entity.getBcc())) {
			for (String bcc : StringUtils.split(entity.getBcc(), ",")) {
				email.addBcc(bcc);
			}
		}
		
		email.send();
	}
	
}
