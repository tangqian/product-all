package com.ofweek.live.core.modules.sys.utils;

import java.io.UnsupportedEncodingException;

import com.ofweek.live.core.common.config.LiveEnv;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * callback参数编解码工具类
 *
 * @author tangqian
 */
public class CallbackUtils {

    private final static Logger logger = LoggerFactory.getLogger(CallbackUtils.class);

    public static String encodeUri(String requestUri) {
        return encode(LiveEnv.getWebSite() + requestUri);
    }

    public static String encode(String requestUrl) {
        String callback = requestUrl;
        try {
            callback = Base64.encodeBase64URLSafeString(callback.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("encode callback error, callback={}", callback);
        }
        return callback;
    }

    public static String decode(String callback) {
        if (Base64.isBase64(callback)) {
            try {
                byte[] asBytes = Base64.decodeBase64(callback);
                callback = new String(asBytes, "utf-8");
            } catch (Exception e) {
                logger.error("decode callback error", e);
            }
        }
        return callback;
    }

}
