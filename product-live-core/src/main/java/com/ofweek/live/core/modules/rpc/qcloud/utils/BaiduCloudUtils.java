package com.ofweek.live.core.modules.rpc.qcloud.utils;

import com.baidubce.BceClientConfiguration;
import com.baidubce.Region;
import com.baidubce.services.lss.LssClient;
import com.baidubce.services.vod.VodClient;
import com.ofweek.live.core.common.config.LiveEnv;

/**
 * 百度云视频工具类
 * 
 * @author tangqian
 */
public class BaiduCloudUtils {

	public static final LssClient lssClient;

	public static final VodClient vodClient;

	static {
		BceClientConfiguration config = new BceClientConfiguration();
	    config.setCredentials(LiveEnv.getBaiduCredentials());
	    config.setConnectionTimeoutInMillis(3 * 1000);
	    config.setSocketTimeoutInMillis(3 * 1000);
	    lssClient = new LssClient(config);
		vodClient = new VodClient(config);
	}

}
