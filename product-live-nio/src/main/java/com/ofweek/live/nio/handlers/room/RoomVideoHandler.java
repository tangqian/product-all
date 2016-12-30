package com.ofweek.live.nio.handlers.room;

import com.google.common.collect.Lists;
import com.ofweek.live.core.modules.room.dao.RoomVideoDao;
import com.ofweek.live.core.modules.room.dao.RoomVideoPublicDao;
import com.ofweek.live.core.modules.room.entity.RoomVideo;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.core.modules.rpc.qcloud.QcloudService;
import com.ofweek.live.core.modules.rpc.qcloud.dto.QcloudVodPlayInfo;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.room.dto.RoomPptResponse;
import com.ofweek.live.nio.handlers.room.dto.RoomVideoResponse;
import com.ofweek.live.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tangqian on 2016/8/27.
 */
@Service
public class RoomVideoHandler extends AbstractBaseHandler<EmptyRequest> {

    @Resource
    private RoomVideoDao roomVideoDao;

    @Resource
    private RoomVideoPublicDao roomVideoPublicDao;

    @Resource
    private QcloudService qcloudService;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_VIDEO.code();
    }

    @Override
    protected Class<EmptyRequest> getReqestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        Integer mode = LoginHandler.NioUserUtils.getMode(channel);

        RoomVideo entity = new RoomVideo(roomId);
        List<RoomVideo> dataList = RoomModeEnum.isPreview(mode) ? roomVideoDao.findList(entity) :
                roomVideoPublicDao.findList(entity);
        List<RoomVideoResponse> responses = Lists.newArrayList();
        dataList.forEach(data -> {
            RoomVideoResponse response = new RoomVideoResponse(data);
            SysFile sysFile = sysFileService.get(data.getFileId());
            response.setUrl(sysFile.getUrl());
            response.setVodPlayInfo(qcloudService.getPlayInfo(sysFile.getUri()));
            responses.add(response);
        });
        return responses;
    }
}
