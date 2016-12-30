/**
 * @Date 2015年11月3日 下午5:06:31
 * @author tangq
 * @version V1.0
 */
package com.ofweek.live.core.modules.rpc;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.utils.FastJsonUtils;
import com.ofweek.live.core.common.utils.NewHttpClient;
import com.ofweek.live.core.modules.rpc.dto.WebinarMeetingsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 调用研讨会相关接口
 */
public class InvokeWebinar {

    private static final Logger logger = LoggerFactory.getLogger(InvokeWebinar.class);

    private static final String URL_MEETINGS_GET = "url.webinar.getMeetings";

    public static WebinarMeetingsDto getMeetings(int status) {
        String ret = NewHttpClient.get(LiveEnv.getConfig(URL_MEETINGS_GET) + status);
        WebinarMeetingsDto dto = null;
        if (ret == null) {
            logger.error("调用研讨会提供的获取会议接口失败！");
        } else {
            try {
                dto = FastJsonUtils.parseObject(ret.trim(), WebinarMeetingsDto.class);
            } catch (Exception e) {
                logger.error("json解析研讨会提供的获取会议接口失败, Response Text={}", ret);
            }
        }
        return dto;
    }

}
