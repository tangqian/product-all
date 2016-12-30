/*
 * 文 件 名:  FreemarkerIOUtils.java
 * 创 建 人:  tangqian
 * 创建时间:  2013-12-25
 */
package com.thinkgem.jeesite.modules.live.base.utils;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <模板IO操作辅助类>
 */
public class FreemarkerIOUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerIOUtils.class);
    
    private static FreeMarkerConfigurer freeMarkerConfigurer;
    
    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        FreemarkerIOUtils.freeMarkerConfigurer = freeMarkerConfigurer;
    }
    
    /**
     * <获取模板输出字符串>
     * @param ftlName
     * @param data
     * @return
     */
    public static String parse(String ftlName, Map<String, Object> data) {
        String result = null;
        Template t;
        try {
        	t = freeMarkerConfigurer.getConfiguration().getTemplate(ftlName);
        	result = FreeMarkerTemplateUtils.processTemplateIntoString(t, data);
        }
        catch (IOException e) {
            logger.error(" ftl output IOException ,while ftlName=" + ftlName, e);
        }
        catch (TemplateException e) {
            logger.error(" ftl output TemplateException ,while ftlName=" + ftlName, e);
        }
        return result;
    }
    
}
