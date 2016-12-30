package com.ofweek.live.core.modules.room.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.modules.report.service.ReportVisitorPresentService;
import com.ofweek.live.core.modules.room.dao.RoomChatDao;
import com.ofweek.live.core.modules.room.entity.RoomChat;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

/**
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomChatService extends CrudService<RoomChatDao, RoomChat> {

    @Resource
    private ReportVisitorPresentService reportVisitorPresentService;

    @Override
    @Transactional(readOnly = false)
    public void save(RoomChat entity) {
        entity.setId(SequenceUtils.getNextString("LiveRoomChat"));
        entity.preInsert();
        dao.insert(entity);
    }

    public List<RoomChat> findPage(String roomId, Date endTime, int offset, int limit) {
        return dao.findRoomRange(roomId, endTime, offset, limit);
    }
    
    public Integer countRoomChatAudienceForRoomId(String roomId) {
    	return dao.countRoomChatAudienceForRoomId(roomId);
    }
    
    public List<RoomChat> findRoomChatForRoomId(String roomId, String userId) {
    	return dao.findRoomChatForRoomId(roomId, userId); 
    }
}
