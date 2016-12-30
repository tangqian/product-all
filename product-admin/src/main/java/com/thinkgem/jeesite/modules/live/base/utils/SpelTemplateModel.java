/**
 * @Date 2016年3月24日 下午2:26:21
 * @author tangq
 * @version V1.0
 * 
 */
package com.thinkgem.jeesite.modules.live.base.utils;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;

import com.thinkgem.jeesite.modules.live.base.config.LiveEnv;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/** 
 *  
 */
public class SpelTemplateModel implements TemplateMethodModelEx {

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List list) throws TemplateModelException {
		if (list.size() != 1) {
			return new TemplateModelException("wrong param number, must be 1!");
		}
		
		Object obj = list.get(0);
		
		String config = "";
		if(obj instanceof SimpleScalar){
			SimpleScalar scalar = (SimpleScalar) obj;
			String key = scalar.getAsString();
			config = StringUtils.defaultString(LiveEnv.getConfig(key));
		}
		return config;
	}

}
