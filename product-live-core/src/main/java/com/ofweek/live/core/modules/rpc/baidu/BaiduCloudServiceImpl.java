package com.ofweek.live.core.modules.rpc.baidu;

import com.baidubce.BceServiceException;
import com.baidubce.services.lss.LssClient;
import com.baidubce.services.lss.model.*;
import com.baidubce.services.vod.model.GenerateMediaDeliveryInfoResponse;
import com.baidubce.services.vod.model.GenerateMediaPlayerCodeResponse;
import com.baidubce.services.vod.model.ListMediaResourceResponse;
import com.baidubce.services.vod.model.MediaResource;
import com.google.common.collect.Lists;
import com.ofweek.live.core.common.utils.DateUtils;
import com.ofweek.live.core.modules.rpc.qcloud.utils.BaiduCloudUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * @author tangqian
 */
@Service
public class BaiduCloudServiceImpl implements BaiduCloudService {

    private static final Logger logger = LoggerFactory.getLogger(BaiduCloudServiceImpl.class);

    private LssClient client;

    private static final LivePublishInfo PUBLISH_INFO;

    static {
        PUBLISH_INFO = new LivePublishInfo();
        PUBLISH_INFO.setRegion("gz");
    }

    @PostConstruct
    public void postConstruct() {
        client = BaiduCloudUtils.lssClient;
    }

    @Override
    public CreateSessionResponse createPushSession(String description, String preset, String notification, String securityPolicy, String recording) {
        CreateSessionResponse resp = client.createSession(description, preset, notification, securityPolicy, recording, PUBLISH_INFO);
        return resp;
    }

    @Override
    public ListSessionsResponse listSessions() {
        ListSessionsResponse resp = client.listSessions();
        return resp;
    }

    @Override
    public GetSessionResponse getSession(String sessionId) {
        GetSessionResponse resp = client.getSession(sessionId);
        return resp;
    }

    @Override
    public RefreshSessionResponse refreshSession(String sessionId) {
        RefreshSessionResponse resp = client.refreshSession(sessionId);
        return resp;
    }

    @Override
    public void startRecording(String sessionId, String recording) {
        client.startRecording(sessionId, recording);
    }

    @Override
    public void stopRecording(String sessionId) {
        client.stopRecording(sessionId);
    }

    @Override
    public ListMediaResourceResponse listMediaResource(String sessionId) {
        int pageNo = 1;
        int pageSize = 100;
        String status = "PUBLISHED";
        status = null;
        Date end = DateUtils.parseDate("2016-10-29 11:51:46");
        ListMediaResourceResponse responce = BaiduCloudUtils.vodClient.listMediaResources(pageNo, pageSize, status, null, end, null);
        System.out.println(responce.getMedia().size());
        List<MediaResource> lists = Lists.reverse(responce.getMedia());
        for (MediaResource mediaResource : lists) {
            String mediaId = mediaResource.getMediaId();
            String createTime = mediaResource.getCreateTime();
            String title = mediaResource.getAttributes().getTitle();
            String description = mediaResource.getAttributes().getDescription();
            System.out.println("------------------");
            System.out.println(mediaId);
            /*System.out.println(DateUtils.formatDate(com.baidubce.util.DateUtils.parseAlternateIso8601Date(createTime),"yyyy-MM-dd HH:mm:ss"));
            System.out.println(createTime);
            System.out.println(title);
            System.out.println(description);*/
            deleteMediaId(mediaId);
            try {
                Thread.sleep(100l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return responce;
    }

    @Override
    public ListMediaResourceResponse getMediaResourceResponse(String title) {
        int pageNo = 1;
        int pageSize = 1000;
        String status = "PUBLISHED";
        ListMediaResourceResponse responce = BaiduCloudUtils.vodClient.listMediaResources(pageNo, pageSize, status, null, null, title);
        return responce;
    }

    private void deleteMediaId(String mediaId){
        BaiduCloudUtils.vodClient.deleteMediaResource(mediaId);
    }

    @Override
    public GenerateMediaDeliveryInfoResponse generateMediaDeliveryInfo(String mediaId) {
        GenerateMediaDeliveryInfoResponse response = null;
        try {
            response = BaiduCloudUtils.vodClient.generateMediaDeliveryInfo(mediaId);
        }catch (BceServiceException e){
            logger.error("获取媒质信息失败!", e);
        }
        return response;
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.parseDate("2016-08-22 11:51:46"));
    }

}
