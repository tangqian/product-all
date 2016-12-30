package com.ofweek.live.nio.handlers.room;

import com.google.common.collect.Lists;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.room.dao.RoomVideoDao;
import com.ofweek.live.core.modules.room.dao.RoomVideoPublicDao;
import com.ofweek.live.core.modules.room.entity.RoomVideo;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.core.modules.speaker.dao.SpeakerSpeechDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerSpeech;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.nio.common.dto.BaseMessage;
import com.ofweek.live.nio.common.dto.EmptyRequest;
import com.ofweek.live.nio.common.handler.AbstractBaseHandler;
import com.ofweek.live.nio.common.utils.MsgNoEnum;
import com.ofweek.live.nio.handlers.room.dto.RoomPptDetailRequest;
import com.ofweek.live.nio.handlers.room.dto.RoomPptDetailResponse;
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
public class RoomPptDetailHandler extends AbstractBaseHandler<RoomPptDetailRequest> {

    @Resource
    private SpeakerSpeechDao speakerSpeechDao;

    @Override
    public int msgNo() {
        return MsgNoEnum.ROOM_PPT_DETAIL.code();
    }

    @Override
    protected Class<RoomPptDetailRequest> getReqestBodyClass() {
        return RoomPptDetailRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, RoomPptDetailRequest requestBody) {
        String roomId = LoginHandler.NioUserUtils.getRoomId(channel);
        Integer mode = LoginHandler.NioUserUtils.getMode(channel);

        SpeakerSpeech speakerSpeech = speakerSpeechDao.get(requestBody.getId());
        RoomPptDetailResponse response = new RoomPptDetailResponse();

        if (speakerSpeech != null) {
            List<SysFile> files = sysFileService.findByParentId(speakerSpeech.getFileId());
            if (!files.isEmpty()) {
                String prefix = StringUtils.substringBeforeLast(files.get(0).getUrl(), "/") + "/";
                response.setPrefix(prefix);

                List<RoomPptDetailResponse.PptPage> lists = Lists.newArrayList();
                int i = 1;
                for (SysFile file : files) {
                    lists.add(new RoomPptDetailResponse.PptPage(i++, StringUtils.substringAfterLast(file.getUri(), "/")));
                }
                response.setPages(lists);
            }
        }
        return response;
    }
}
