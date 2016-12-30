/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.ofweek.live.core.modules.rpc.qcloud.utils;

import com.google.common.collect.Maps;
import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.modules.rpc.qcloud.dto.QcloudVodPlayInfo;
import com.qcloud.api.callers.LiveModuleCaller;
import com.qcloud.api.callers.VodModuleCaller;
import com.qcloud.api.common.RequestClient;
import com.qcloud.api.modules.vod.dto.CreateClassResponse;
import com.qcloud.api.modules.vod.dto.DescribeClassResponse;
import com.qcloud.api.modules.vod.dto.DescribeVodPlayInfoResponse.VodPlayInfo;
import com.qcloud.api.modules.vod.dto.VodPlayUrlInfo;
import com.qcloud.api.modules.vod.service.CreateClassService;
import com.qcloud.api.modules.vod.service.DescribeClassService;

import java.io.File;
import java.util.Map;

/**
 * 腾讯云视频工具类
 *
 * @author tangqian
 */
public class QcloudUtils {

    public static final LiveModuleCaller liveCaller;

    public static final VodModuleCaller vodCaller;

    /**
     * 直播系统上传视频文件统一加上“L”前缀，以区分展会上传视频
     */
    public static final String NAME_PREFIX = "L";

    public static final String P_CLASS_NAME = "直播";

    static {
        RequestClient client = new RequestClient(LiveEnv.getIdentityConfig(), 3000, 20000);
        liveCaller = new LiveModuleCaller(client);
        vodCaller = new VodModuleCaller(client);
    }

    public static String getFileName(File file) {
        return NAME_PREFIX + file.getName();
    }

    public static String getFileName(String fileName) {
        int pos = fileName.lastIndexOf('/');
        if (pos != -1) {
            fileName = fileName.substring(pos + 1);
        }
        return getFileNameCacheKey(fileName);
    }

    private static String getFileNameCacheKey(String srcFileName) {
        return srcFileName.startsWith(NAME_PREFIX) ? srcFileName : NAME_PREFIX + srcFileName;
    }

    public static Integer getParentClass() {
        Integer pid = ClassNameCache.getId(P_CLASS_NAME);
        if (pid == null) {
            DescribeClassResponse response = new DescribeClassService(QcloudUtils.vodCaller).call();
            if (response.isSuccess()) {
                DescribeClassResponse.ClassInfo[] data = response.getData();
                for (DescribeClassResponse.ClassInfo classInfo : data) {
                    if (classInfo.getName().equals(P_CLASS_NAME)) {
                        pid = classInfo.getId();
                        QcloudUtils.ClassNameCache.put(P_CLASS_NAME, pid);
                        break;
                    }
                }

                if (pid == null) {
                    CreateClassResponse createResponse = new CreateClassService(QcloudUtils.vodCaller).call(P_CLASS_NAME, null);
                    if (createResponse.isSuccess()) {
                        pid = Integer.valueOf(createResponse.getNewClassId());
                        QcloudUtils.ClassNameCache.put(P_CLASS_NAME, pid);
                    }
                }
            }
        }
        return pid;
    }

    public static class FileNameCache {

        private static final Map<String, QcloudVodPlayInfo> fileNamePlayUrl = Maps.newHashMap();

        public static void put(String fileName, QcloudVodPlayInfo playUrl) {
            fileNamePlayUrl.put(getFileNameCacheKey(fileName), playUrl);
        }

        public static boolean contains(String fileName) {
            return fileNamePlayUrl.containsKey(getFileNameCacheKey(fileName));
        }

        public static QcloudVodPlayInfo get(String fileName) {
            return fileNamePlayUrl.get(getFileNameCacheKey(fileName));
        }
    }

    public static class ClassNameCache {

        private static final Map<String, Integer> classNameClassIds = Maps.newHashMap();

        public static void put(String className, Integer classId) {
            classNameClassIds.put(className, classId);
        }

        public static Integer getId(String className) {
            return classNameClassIds.get(className);
        }

        public static boolean contains(String className) {
            return classNameClassIds.containsKey(className);
        }
    }

    public static void setPlayInfoCache(VodPlayInfo[] vods) {
        for (VodPlayInfo vodPlayInfo : vods) {
            VodPlayUrlInfo[] playUrls = vodPlayInfo.getPlaySet();
            setPlayInfoCache(vodPlayInfo.getFileId(), vodPlayInfo.getFileName(), playUrls);
        }
    }

    public static void setPlayInfoCache(String fileId, String fileName, VodPlayUrlInfo[] playUrls) {
        String originalPlayUrl = null;
        String mp4PlayUrl = null;
        String mp4LowerRate = null;
        String mp4MiddleRate = null;
        String mp4HighRate = null;
        for (VodPlayUrlInfo vodPlayUrlInfo : playUrls) {
            switch (vodPlayUrlInfo.getDefinition()) {
                case 0:
                    originalPlayUrl = vodPlayUrlInfo.getUrl();
                    break;
                case 10:
                    mp4LowerRate = vodPlayUrlInfo.getUrl();
                    break;
                case 20:
                    mp4MiddleRate = vodPlayUrlInfo.getUrl();
                    break;
                case 30:
                    mp4HighRate = vodPlayUrlInfo.getUrl();
                    break;
                default:
                    break;
            }
        }

        if (mp4HighRate != null) {
            mp4PlayUrl = mp4HighRate;
        } else if (mp4MiddleRate != null) {
            mp4PlayUrl = mp4MiddleRate;
        } else {
            mp4PlayUrl = mp4LowerRate;
        }

        QcloudVodPlayInfo info = new QcloudVodPlayInfo(fileId, originalPlayUrl, mp4PlayUrl);
        FileNameCache.put(fileName, info);
    }

}
