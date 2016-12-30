package com.ofweek.live.core.modules.speaker.service;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.dao.RoomVideoDao;
import com.ofweek.live.core.modules.room.dao.RoomVideoPublicDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.enums.RoomStatusEnum;
import com.ofweek.live.core.modules.speaker.dao.SpeakerVideoDao;
import com.ofweek.live.core.modules.speaker.dao.SpeakerVideoRecycleDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerVideo;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangqian
 */
@Service
@Transactional
public class SpeakerVideoService extends CrudService<SpeakerVideoDao, SpeakerVideo> {

    @Autowired
    private SpeakerVideoRecycleDao speakerVideoRecycleDao;

    @Autowired
    private SpeakerRecycleInventoryService speakerRecycleInventoryService;

    @Resource
    private RoomDao roomDao;

    @Resource
    private RoomVideoDao roomVideoDao;

    @Resource
    private RoomVideoPublicDao roomVideoPublicDao;

    @Override
    @Transactional(readOnly = false)
    public void save(SpeakerVideo entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(SequenceUtils.getNextString("LiveSpeakerVideo"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    /**
     * 检查视频是否能被删除，如果视频对应的房间正在进行中，则不能被删除
     *
     * @param idsArr
     * @return
     */
    public boolean canDelete(String idsArr[]) {
        boolean result = true;
        List<Room> rooms = roomDao.findBySpeakerVideoIds(idsArr);
        for (Room room : rooms) {
            if (RoomStatusEnum.isHolding(room.getStatus())) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void delSpeakerVideoForIds(String idsArr[]) {
        speakerVideoRecycleDao.insertFormVideo2VideoRecycle(idsArr);// 备份到回收站表
        speakerRecycleInventoryService.saveSpeakerRecycleInventoryForVideo(dao.findSpeakerVideoForIds(idsArr));// 同步到回收站清单表
        dao.delSpeakerVideoForIds(idsArr);// 删除记录
        roomVideoDao.deleteDatas(idsArr);
        roomVideoPublicDao.deleteDatas(idsArr);
    }

    public SpeakerVideo getSpeakerVideoForObj(SpeakerVideo speakerVideo) {
        speakerVideo.setSpeakerId(UserUtils.getUser().getId());
        return dao.getSpeakerVideoForObj(speakerVideo);
    }

    public List<SpeakerVideo> findSpeakerVideoForConditions(String videoIds) {
        return dao.findSpeakerVideoForConditions(videoIds, UserUtils.getUser().getId());
    }
}
