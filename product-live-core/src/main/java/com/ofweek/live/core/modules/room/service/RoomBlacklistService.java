package com.ofweek.live.core.modules.room.service;

import com.ofweek.live.core.common.service.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;

import com.ofweek.live.core.modules.room.dao.RoomBlacklistDao;
import com.ofweek.live.core.modules.room.entity.RoomBlacklist;

import java.util.List;

/**
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomBlacklistService extends CrudService<RoomBlacklistDao, RoomBlacklist> {

    @Override
    @Transactional(readOnly = false)
    public void save(RoomBlacklist entity) {
        if (StringUtils.isBlank(entity.getId())) {
            RoomBlacklist exsits = get(entity.getRoomId(), entity.getUserId());
            if (exsits != null) {
                exsits.setReason(entity.getReason());
                exsits.preUpdate();
                dao.update(exsits);
            } else {
                entity.setId(SequenceUtils.getNextString("LiveRoomBlacklist"));
                entity.preInsert();
                dao.insert(entity);
            }
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    public RoomBlacklist get(String roomId, String userId) throws ServiceException {
        return dao.getByRoomUserId(roomId, userId);
    }

    public void check(String roomId, String userId) throws ServiceException {
        if (get(roomId, userId) != null) {
            throw new ServiceException("黑名单用户");
        }
    }

}
