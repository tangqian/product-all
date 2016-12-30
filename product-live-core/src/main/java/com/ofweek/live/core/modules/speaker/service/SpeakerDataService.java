package com.ofweek.live.core.modules.speaker.service;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.dao.RoomDataDao;
import com.ofweek.live.core.modules.room.dao.RoomDataPublicDao;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.enums.RoomStatusEnum;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDataDao;
import com.ofweek.live.core.modules.speaker.dao.SpeakerDataRecycleDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerData;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import com.ofweek.live.core.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangqian
 */
@Service
@Transactional
public class SpeakerDataService extends CrudService<SpeakerDataDao, SpeakerData> {

    @Resource
    private SpeakerDataRecycleDao speakerDataRecycleDao;

    @Resource
    private SpeakerRecycleInventoryService speakerRecycleInventoryService;

    @Resource
    private RoomDao roomDao;

    @Resource
    private RoomDataDao roomDataDao;

    @Resource
    private RoomDataPublicDao roomDataPublicDao;

    @Override
    @Transactional(readOnly = false)
    public void save(SpeakerData entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(SequenceUtils.getNextString("LiveSpeakerData"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    /**
     * 检查资料是否能被删除，如果资料对应的房间正在进行中，则不能被删除
     * @param idsArr
     * @return
     */
    public boolean canDelete(String idsArr[]) {
        boolean result = true;
        List<Room> rooms =  roomDao.findBySpeakerDataIds(idsArr);
        for (Room room : rooms) {
            if(RoomStatusEnum.isHolding(room.getStatus())){
                result = false;
                break;
            }
        }
        return result;
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public void delSpeakerDataForIds(String idsArr[]) {
        speakerDataRecycleDao.insertFormData2DataRecycle(idsArr);// 备份到回收站表
        speakerRecycleInventoryService.saveSpeakerRecycleInventoryForData(dao.findSpeakerDataForIds(idsArr));// 同步到回收站清单表
        dao.delSpeakerDataForIds(idsArr);// 删除记录
        roomDataDao.deleteDatas(idsArr);
        roomDataPublicDao.deleteDatas(idsArr);
    }

    public SpeakerData getSpeakerDataForObj(SpeakerData speakerData) {
        speakerData.setSpeakerId(UserUtils.getUser().getId());
        return dao.getSpeakerDataForObj(speakerData);
    }

    public List<SpeakerData> findSpeakerDataForConditions(String dataIds) {
        return dao.findSpeakerDataForConditions(dataIds, UserUtils.getUser().getId());
    }

}
