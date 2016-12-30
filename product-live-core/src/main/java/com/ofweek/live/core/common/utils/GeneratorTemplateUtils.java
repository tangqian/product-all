package com.ofweek.live.core.common.utils;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.ofweek.live.core.common.service.ServiceException;
import com.ofweek.live.core.modules.sys.entity.SysEmail;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class GeneratorTemplateUtils {

	private static final Logger logger = LoggerFactory.getLogger(GeneratorTemplateUtils.class);

	private static FreeMarkerConfigurer freeMarkerConfigurer;

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		GeneratorTemplateUtils.freeMarkerConfigurer = freeMarkerConfigurer;
	}

	public static String generateTemplateText(SysEmail entity, Map<?, ?> model) {
		try {
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate(entity.getTemplatePath());
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (IOException e) {
			logger.error("loading templateFile Exception:" + e.getMessage());
			throw new ServiceException("loading templateFile Exception:" + e.getMessage());
		} catch (TemplateException e) {
			logger.error("parse templateContent Exception:" + e.getMessage());
			throw new ServiceException("parse templateContent Exception:" + e.getMessage());
		}
	}

}
