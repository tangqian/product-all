package com.ofweek.live.nio.handlers.room;

import com.baidubce.services.vod.model.GenerateMediaDeliveryInfoResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ofweek.live.core.modules.room.dao.RoomReviewDataDao;
import com.ofweek.live.core.modules.room.entity.RoomReviewData;
import com.ofweek.live.core.modules.rpc.baidu.BaiduCloudService;
import com.ofweek.live.core.modules.rpc.qcloud.QcloudService;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.room.dto.RoomVideoResponse;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by tangqian on 2016/10/9.
 */
@Service
public class RoomVideoForReviewHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private RoomReviewDataDao roomReviewDataDao;

    @Resource
    private QcloudService qcloudService;

    @Resource
    private BaiduCloudService baiduCloudService;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_VIDEO_FOR_REVIEW.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    private static Map<String, GenerateMediaDeliveryInfoResponse> addressCache = Maps.newHashMap();

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);

        //LVBChannel liveInfo = LiveAddressUtils.get(roomId);
        //baiduCloudService.listMediaResource(liveInfo.getChannelId());

        RoomReviewData search = new RoomReviewData();
        search.setRoomId(roomId);
        search.setStatus(1);
        //search.setType(1);
        List<RoomReviewData> dataList = roomReviewDataDao.findList(search);
        List<RoomVideoResponse> responses = Lists.newArrayList();
        for (RoomReviewData data : dataList) {
            RoomVideoResponse response = new RoomVideoResponse(data);
            //类型为1代表百度云,2代表腾讯云
            if (data.getType() != null && data.getType().equals(1)) {
                GenerateMediaDeliveryInfoResponse mediaResponse = addressCache.get(data.getMediaId());
                if (mediaResponse == null) {
                    mediaResponse = baiduCloudService.generateMediaDeliveryInfo(data.getMediaId());
                    addressCache.put(data.getMediaId(), mediaResponse);
                }
                if (mediaResponse != null) {
                    response.setbPlayUrl(mediaResponse.getFile());
                    response.setbCoverPage(mediaResponse.getCover());
                } else {
                    continue;
                }
            } else {
                SysFile sysFile = sysFileService.get(data.getFileId());
                response.setUrl(sysFile.getUrl());
                response.setVodPlayInfo(qcloudService.getPlayInfo(sysFile.getUri()));
            }
            responses.add(response);
        }
        return responses;
    }
}
