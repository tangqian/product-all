package com.ofweek.live.core.modules.rpc.qcloud.utils;

import com.ofweek.live.core.modules.rpc.qcloud.dto.QcloudVodPlayInfo;
import com.qcloud.api.modules.vod.dto.DescribeVodPlayUrlsResponse;
import com.qcloud.api.modules.vod.dto.MultipartUploadVodFileResponse;
import com.qcloud.api.modules.vod.dto.VodPlayUrlInfo;
import com.qcloud.api.modules.vod.service.DescribeVodPlayUrlsService;
import com.qcloud.api.modules.vod.service.MultipartUploadVodFileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by tangqian on 2016/9/8.
 */
public class VideoUploadTask extends Thread {

    private Logger logger = LoggerFactory.getLogger(VideoUploadTask.class);

    private File file;

    private Integer classId;

    private boolean success;

    private String feedback;

    private String fileName;

    private AsyncTaskMonitor monitor;

    public VideoUploadTask(File file, Integer classId) {
        this.file = file;
        this.classId = classId;
        fileName = QcloudUtils.getFileName(file);
    }

    @Override
    public void run() {
        MultipartUploadVodFileResponse response = null;
        try {
            logger.error("start upload fileName={}", fileName);
            response = new MultipartUploadVodFileService(QcloudUtils.vodCaller).call(file.getAbsolutePath(), fileName, classId, null);
            if (!response.isSuccess()) {
                Thread.sleep(1000);
                response = new MultipartUploadVodFileService(QcloudUtils.vodCaller).call(file.getAbsolutePath(), fileName, classId, null);
            }
        } catch (Exception e) {
            logger.error("live qcloud upload video exception", e);
        }

        if (response == null) {
            success = false;
            feedback = "发生异常";
        } else {
            if (response.isSuccess()) {
                String fileId = response.getFileId();
                success = true;
                try {
                    DescribeVodPlayUrlsResponse call = new DescribeVodPlayUrlsService(QcloudUtils.vodCaller).call(fileId);
                    VodPlayUrlInfo[] playUrls = call.getPlaySet();
                    QcloudUtils.setPlayInfoCache(fileId, fileName, playUrls);
                } catch (Exception e) {
                }
            } else {
                success = false;
                feedback = response.getMessage();
            }
        }
        monitor.addFinishedTask(this);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getFileName() {
        return fileName;
    }

    public void setMonitor(AsyncTaskMonitor monitor) {
        this.monitor = monitor;
    }
}
