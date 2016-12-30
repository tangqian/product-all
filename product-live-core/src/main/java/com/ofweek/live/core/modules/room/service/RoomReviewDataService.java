package com.ofweek.live.core.modules.room.service;

import com.baidubce.services.vod.model.ListMediaResourceResponse;
import com.baidubce.services.vod.model.MediaResource;
import com.google.common.collect.Sets;
import com.ofweek.live.core.common.service.CrudService;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.room.dao.RoomReviewDataDao;
import com.ofweek.live.core.modules.room.entity.RoomReviewData;
import com.ofweek.live.core.modules.rpc.baidu.BaiduCloudService;
import com.ofweek.live.core.modules.sys.utils.SequenceUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author tangqian
 */
@Service
@Transactional(readOnly = true)
public class RoomReviewDataService extends CrudService<RoomReviewDataDao, RoomReviewData> {

    @Resource
    private BaiduCloudService baiduCloudService;

    @Override
    @Transactional(readOnly = false)
    public void save(RoomReviewData entity) {
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(SequenceUtils.getNextString("LiveRoomReviewData"));
            entity.preInsert();
            dao.insert(entity);
        } else {
            entity.preUpdate();
            dao.update(entity);
        }
    }

    @Transactional(readOnly = false)
    public boolean syncBCloud() {
        logger.info("autoSyncBCloud start!");
        try {
            ListMediaResourceResponse responce = baiduCloudService.getMediaResourceResponse("SL");
            List<MediaResource> lists = responce.getMedia();
            if (CollectionUtils.isNotEmpty(lists)) {
                Set<String> mediaIds = Sets.newHashSet();
                List<RoomReviewData> all = findList(null);
                all.forEach(d -> mediaIds.add(d.getMediaId()));

                Set<String> exsits = Sets.newHashSet();
                for (MediaResource mediaResource : lists) {
                    String mediaId = mediaResource.getMediaId();
                    String title = mediaResource.getAttributes().getTitle();
                    exsits.add(title);
                    String roomId = getRoomIdByTitle(title);
                    if (StringUtils.isNotBlank(roomId) && !mediaIds.contains(mediaId)) {
                        RoomReviewData reviewData = new RoomReviewData();
                        reviewData.setName(title);
                        reviewData.setRoomId(roomId);
                        reviewData.setStatus(0);
                        reviewData.setType(1);
                        reviewData.setFileId("-1");//-1代表不存在
                        reviewData.setMediaId(mediaId);
                        reviewData.setCreateDate(new Date());
                        reviewData.setUpdateDate(new Date());
                        this.save(reviewData);
                    }
                }

                all.forEach(d -> {
                    if (!exsits.contains(d.getName())) {
                        delete(d);
                    }
                });
            }
        } catch (Exception e) {
            logger.error("autoSyncBCloud Fail!", e);
            return false;
        }
        logger.info("autoSyncBCloud Success!");
        return true;
    }

    /**
     * 根据mediaId获取RoomId
     *
     * @param title
     * @return
     */
    private String getRoomIdByTitle(String title) {
        String roomId = "";
        //判断mediaId是否以数字开头，SL15-001中15代表roomId
        try {
            if (StringUtils.isNotBlank(title)) {
                String tmp = title.split("-")[0];
                tmp = tmp.substring(2, tmp.length());
                roomId = Integer.valueOf(tmp).toString();
            }
        } catch (Exception e) {
        }
        return roomId;
    }

}
