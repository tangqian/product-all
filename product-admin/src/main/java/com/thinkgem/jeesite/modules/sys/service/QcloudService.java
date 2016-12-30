package com.thinkgem.jeesite.modules.sys.service;

import com.google.common.collect.Lists;
import com.qcloud.api.modules.vod.dto.*;
import com.qcloud.api.modules.vod.dto.DescribeAllClassResponse.ClassDirInfo;
import com.qcloud.api.modules.vod.dto.DescribeAllClassResponse.ClassNodeInfo;
import com.qcloud.api.modules.vod.dto.DescribeVodInfoResponse.VodInfo;
import com.qcloud.api.modules.vod.service.*;
import com.qcloud.api.modules.vod.service.DescribeVodInfoService.DescribeVodInfoRequest;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.live.base.enums.AsyncTaskStatusEnum;
import com.thinkgem.jeesite.modules.live.base.utils.QcloudUtils;
import com.thinkgem.jeesite.modules.sys.entity.AsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class QcloudService {

    private static final Logger logger = LoggerFactory.getLogger(QcloudService.class);

    @Resource
    private AsyncTaskService asyncTaskService;

    public String getFileId(String fileName) {
        try {
            int pos = fileName.lastIndexOf('/');
            if (pos != -1) {
                fileName = fileName.substring(pos + 1);
            }
            String searchName = fileName;
            Integer pageNo = 1;
            Integer pageSize = 100;
            DescribeVodPlayInfoResponse response = new DescribeVodPlayInfoService(QcloudUtils.vodCaller).call(searchName, pageNo, pageSize);
            if (response.isSuccess()) {
                DescribeVodPlayInfoResponse.VodPlayInfo[] vods = response.getFileSet();
                for (DescribeVodPlayInfoResponse.VodPlayInfo vodPlayInfo : vods) {
                    QcloudUtils.FileNameCache.put(vodPlayInfo.getFileName(), vodPlayInfo.getFileId());
                }
            }
        } catch (Exception e) {
            logger.error("qcloud getPlayUrl happen exception!", e);
        }
        return QcloudUtils.FileNameCache.get(fileName);
    }

    public void clearCache() {
        QcloudUtils.FileNameCache.clear();
    }

}