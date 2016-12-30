package com.ofweek.live.core.common.config;

import com.baidubce.auth.BceCredentials;
import com.baidubce.auth.DefaultBceCredentials;
import com.google.common.collect.Maps;
import com.ofweek.live.core.common.utils.PropertiesLoader;
import com.qcloud.api.common.IdentityConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 直播版本配置类
 * 
 * @author tangqian
 * @version 2016-09-29
 */
@Component
public class VersionEnv {

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("config:version.properties");

	public void reload(){
		PropertiesLoader tmp = new PropertiesLoader("config:version.properties");
		loader = tmp;
	}

	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		return loader.getProperty(key);
	}

	public static String getCssIndex() {
		return getConfig("css.index");
	}

	public static String getJsVendor() {
		return getConfig("js.vendor");
	}

	public static String getJsIndex() {
		return getConfig("js.index");
	}

}
