package com.ofweek.live.core.common.config;

import com.baidubce.auth.BceCredentials;
import com.baidubce.auth.DefaultBceCredentials;
import com.google.common.collect.Maps;
import com.ofweek.live.core.common.utils.PropertiesLoader;
import com.qcloud.api.common.IdentityConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 直播系统配置类
 * 
 * @author tangqian
 * @version 2016-02-24
 */
public class LiveEnv {

	/**
	 * 保存全局属性值
	 */
	private static final Map<String, String> map = Maps.newHashMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("config:env.properties");

	private static final IdentityConfig qcloudConfig;

	private static final BceCredentials baiduConfig;

	static {
		qcloudConfig = new IdentityConfig(getConfig("qcloud.secretId"), getConfig("qcloud.secretKey"));
		baiduConfig = new DefaultBceCredentials(getConfig("baidu.accessKeyId"), getConfig("baidu.secretAccessKey"));
	}


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

	public static String getNioPort() {
		return getConfig("base.nio.port");
	}
	
	public static String getUploadRoot()        {
            return getConfig("upload.root");
        }

        public static class Smartlifein {
            public static String getLoginUrl() {
                return getConfig("url.smartlifein.login");
            }

            public static String getQueryUrl() {
                return getConfig("url.smartlifein.queryUserInfo");
            }

            public static String getUpdateUser() {
                return getConfig("url.smartlifein.updateUser");
            }
	}
	
	public static int getDataLimit() {
		return Integer.valueOf(getConfig("data.maxCount"));
	}
	
	public static int getVediotLimit() {
		return Integer.valueOf(getConfig("vedio.maxCount"));
	}
	
	public static int getSpeechLimit() {
		return Integer.valueOf(getConfig("speech.maxCount"));
	}
	
	public static int getWaiterLimit() {
		return Integer.valueOf(getConfig("waiter.maxCount"));
	}

	public static String getAppId() {
		return getConfig("qcloud.appId");
	}

	public static IdentityConfig getIdentityConfig() {
		return qcloudConfig;
	}

	public static BceCredentials getBaiduCredentials() {
		return baiduConfig;
	}
}
