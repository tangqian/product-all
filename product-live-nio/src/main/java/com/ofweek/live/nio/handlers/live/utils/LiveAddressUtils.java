package com.ofweek.live.nio.handlers.live.utils;

import com.baidubce.services.lss.model.GetSessionResponse;
import com.baidubce.services.lss.model.ListSessionsResponse;
import com.baidubce.services.lss.model.LivePlay;
import com.baidubce.services.lss.model.LiveSession;
import com.google.common.collect.Maps;
import com.ofweek.live.core.common.utils.SpringContextHolder;
import com.ofweek.live.core.modules.rpc.baidu.BaiduCloudService;
import com.ofweek.live.nio.handlers.live.LiveAddressHandler;
import com.qcloud.api.modules.live.dto.CreateLVBChannelResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by tangqian on 2016/8/30.
 */
public class LiveAddressUtils {

    private static BaiduCloudService baiduCloudService = SpringContextHolder.getBean(BaiduCloudService.class);

    private static boolean isInited = false;

    private static final Map<String, LVBChannel> roomIdAddress = Maps.newHashMap();

    public static void put(String roomId, LVBChannel address) {
        roomIdAddress.put(roomId, address);
    }

    public static LVBChannel get(String roomId) {
        if (roomIdAddress.get(roomId) == null) {
            initSession();
        }
        return roomIdAddress.get(roomId);
    }

    private static void initSession() {
        if(!isInited){
            ListSessionsResponse listResponse = baiduCloudService.listSessions();
            List<LiveSession> sessions = listResponse.getSessions();
            for (LiveSession liveSession : sessions) {
                String desc = liveSession.getDescription();
                if (desc != null && desc.contains(LiveAddressHandler.LIVE_NAME_PREFIX)) {
                    String roomId = StringUtils.substringAfter(desc, "-");
                    GetSessionResponse sessionResponse = baiduCloudService.getSession(liveSession.getSessionId());
                    LiveAddressUtils.put(roomId, newLVBChannel(sessionResponse));
                }
            }
            isInited = true;
        }
    }

    private static LVBChannel newLVBChannel(GetSessionResponse sessionResponse) {
        LVBChannel channel = new LVBChannel();
        channel.setChannelId(sessionResponse.getSessionId());
        channel.setUpstreamAddress(sessionResponse.getPublish().getPushUrl());

        LivePlay playInfo = sessionResponse.getPlay();
        CreateLVBChannelResponse.DownstreamAddress address = new CreateLVBChannelResponse.DownstreamAddress();
        address.setFlvAddress(playInfo.getFlvUrl());
        address.setHlsAddress(playInfo.getHlsUrl());
        address.setRtmpAddress(playInfo.getRtmpUrl());

        channel.setDownstreamAddress(address);
        return channel;
    }
}
