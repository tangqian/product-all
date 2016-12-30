package com.ofweek.live.core.modules.room.service;

import com.google.common.collect.Lists;
import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.persistence.Page;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.service.ServiceException;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.base.enums.PageEnum;
import com.ofweek.live.core.modules.report.service.ReportUserPresentService;
import com.ofweek.live.core.modules.report.service.ReportVisitorPresentService;
import com.ofweek.live.core.modules.room.dao.RoomDao;
import com.ofweek.live.core.modules.room.dto.RoomDto;
import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.room.enums.RoomModeEnum;
import com.ofweek.live.core.modules.room.enums.RoomStatusEnum;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.core.modules.sys.entity.Visitor;
import com.ofweek.live.core.modules.sys.enums.UserTypeEnum;
import com.ofweek.live.core.modules.sys.service.SysFileService;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoomService extends CrudService<RoomDao, Room> {

    @Resource
    private RoomBlacklistService roomBlacklistService;

    @Resource
    private SysFileService sysFileService;

    @Resource
    private ReportVisitorPresentService reportVisitorPresentService;

    @Resource
    private ReportUserPresentService reportUserPresentService;

    @Override
    @Transactional(readOnly = false)
    public void save(Room entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(SequenceUtils.getNextString("LiveRoom"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    /**
     * 获取全部未结束直播（过滤掉置顶直播）
     * @param search
     * @return
     */
    public List<Room> findUnendIndexList(Room search) {
        List<Room> rooms = Lists.newArrayList();
        dao.findUnendIndexList(search).stream().filter(r -> r.getIsTop() != 1).forEach(r -> {
            r.setCover(sysFileService.get(r.getCoverId()));
            rooms.add(r);
        });
        return rooms;
    }

    /**
     * 获取全部已结束直播（过滤掉置顶直播）
     * @param search
     * @return
     */
    public List<Room> findEndIndexList(Room search) {
        List<Room> rooms = Lists.newArrayList();
        dao.findEndIndexList(search).stream().filter(r -> r.getIsTop() != 1).forEach(r -> {
            r.setCover(sysFileService.get(r.getCoverId()));
            rooms.add(r);
        });
        return rooms;
    }

    /**
     * 获取全部置顶直播
     * @return
     */
    public List<Room> findAllTopList() {
        List<Room> rooms = dao.findAllTopList(new Room());
        rooms.forEach(r -> r.setCover(sysFileService.get(r.getCoverId())));
        return rooms;
    }

    /**
     * 获取预告直播
     * @return
     */
    public List<Room> findTrailerList() {
        List<Room> rooms = dao.findTrailerList();
        rooms.forEach(r -> r.setCover(sysFileService.get(r.getCoverId())));
        return rooms;
    }

    /**
     * 查询分页数据
     * @param page 分页对象
     * @param room
     * @return
     */
    public Page<Room> findReviewPage(Page<Room> page, Room room) {
        page.setPageSize(20);
        room.setPage(page);
        List<Room> rooms = dao.findReviewList(room);
        rooms.forEach(r -> r.setCover(sysFileService.get(r.getCoverId())));
        page.setList(rooms);
        return page;
    }

    public List<Room> findToCloseList() {
        return dao.findToCloseList(new Date());
    }

    public Page<Room> findPageEndIndexList(Page<Room> page, Room entity) {
        entity.setPage(page);
        List<Room> rooms = dao.findEndIndexList(entity);
        rooms.forEach(r -> r.setCover(sysFileService.get(r.getCoverId())));
        page.setList(rooms);
        return page;
    }

    @Transactional(readOnly = false)
    public Room enter(GeneralUser user, String roomId, Integer mode) throws ServiceException {
        Room room = dao.get(roomId);
        if (room == null)
            throw new ServiceException("房间不存在!");

        if (!UserTypeEnum.isAdmin(user.getType())) {
            UserTypeEnum typeEnum = UserTypeEnum.getEnum(user.getType());
            if (RoomModeEnum.isPreview(mode)) {
                if (typeEnum == UserTypeEnum.SPEAKER) {
                    if (!user.getId().equals(room.getSpeakerId())) {
                        throw new ServiceException("预览受限,禁止预览它人的直播房间!");
                    }
                } else {
                    throw new ServiceException("预览受限,非主播身份!");
                }
            } else {
                roomBlacklistService.check(roomId, user.getId());
                //if (!RoomStatusEnum.isOver(room.getStatus()))
                reportUserPresentService.save(roomId, user.getId());
            }
        }
        return room;
    }

    @Transactional(readOnly = false)
    public Room enter(Visitor user, String roomId, Integer mode) throws ServiceException {
        Room room = dao.get(roomId);
        if (room == null)
            throw new ServiceException("房间不存在!");

        if (RoomModeEnum.isPreview(mode)) {
            throw new ServiceException("游客不允许预览!");
        } else {
            //if (!RoomStatusEnum.isOver(room.getStatus()))
            reportVisitorPresentService.save(roomId, user.getId());
        }
        return room;
    }

    public List<RoomDto> findIndexLiveList(Integer status, Integer offset) {
        List<RoomDto> rList = dao.findIndexLiveList(status, PageEnum.setPageOffset(offset, PageEnum.setPageSize()), PageEnum.setPageSize());
        SysFile file = null;
        for (RoomDto room : rList) {
            file = sysFileService.get(String.valueOf(room.getCoverId()));
            if (file != null) {
                room.setCoverImgUrl(LiveEnv.getWebSite() + "/static/upload/" + file.getUri());
            }
        }
        return rList;
    }
}
