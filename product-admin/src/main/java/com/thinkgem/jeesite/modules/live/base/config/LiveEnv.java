package com.thinkgem.jeesite.modules.live.base.config;

import java.util.Map;

import com.google.common.collect.Maps;
import com.qcloud.api.common.IdentityConfig;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 直播系统配置类
 * 
 * @author tangqian
 * 
 */
public class LiveEnv {

	/**
	 * 保存全局属性值
	 */
	private static final Map<String, String> map = Maps.newHashMap();

	private static IdentityConfig config;

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("live.properties");

	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null) {
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取网站地址
	 * @return
	 */
	public static String getWebSite() {
		return getConfig("base.webSite");
	}

	/**
	 * 获取用户上传文件访问前缀
	 * @return
	 */
	public static String getUploadUrlPrefix() {
		return getConfig("base.upload.urlPrefix");
	}

	public static IdentityConfig getIdentityConfig() {
		if (config == null) {
			config = new IdentityConfig(getConfig("qcloud.secretId"), getConfig("qcloud.secretKey"));
		}
		return config;
	}

	public static String getAppId() {
		return getConfig("qcloud.appId");
	}

}
