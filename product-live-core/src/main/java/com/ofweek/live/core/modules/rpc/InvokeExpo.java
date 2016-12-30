/**
 * @Date 2015年11月3日 下午5:06:31
 * @author tangq
 * @version V1.0
 */
package com.ofweek.live.core.modules.rpc;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.utils.FastJsonUtils;
import com.ofweek.live.core.common.utils.NewHttpClient;
import com.ofweek.live.core.modules.rpc.dto.ExpoExhibitionsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 调用展会相关接口
 */
public class InvokeExpo {

    private static final Logger logger = LoggerFactory.getLogger(InvokeExpo.class);

    private static final String URL_EXHIBITION_NOTOVER = "url.expo.exhibition.getNotOver";
    private static final String URL_EXHIBITION_OVER = "url.expo.exhibition.getOver";

    public static ExpoExhibitionsDto getNotOverExhibitions() {
        return NewHttpClient.get(LiveEnv.getConfig(URL_EXHIBITION_NOTOVER), ExpoExhibitionsDto.class);
    }

    public static ExpoExhibitionsDto getOverExhibitions() {
        return NewHttpClient.get(LiveEnv.getConfig(URL_EXHIBITION_OVER), ExpoExhibitionsDto.class);
    }


}
