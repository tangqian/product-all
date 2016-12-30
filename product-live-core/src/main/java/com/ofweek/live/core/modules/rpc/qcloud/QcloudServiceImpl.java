package com.ofweek.live.core.modules.rpc.qcloud;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.utils.DateUtils;
import com.ofweek.live.core.common.utils.StringUtils;
import com.ofweek.live.core.modules.base.entity.MethodInvokeResult;
import com.ofweek.live.core.modules.room.dao.RoomReviewDataDao;
import com.ofweek.live.core.modules.room.entity.RoomReviewData;
import com.ofweek.live.core.modules.rpc.qcloud.dto.QcloudVodPlayInfo;
import com.ofweek.live.core.modules.rpc.qcloud.utils.AsyncTaskMonitor;
import com.ofweek.live.core.modules.rpc.qcloud.utils.QcloudUtils;
import com.ofweek.live.core.modules.rpc.qcloud.utils.VideoUploadTask;
import com.ofweek.live.core.modules.speaker.dao.SpeakerVideoDao;
import com.ofweek.live.core.modules.speaker.entity.SpeakerVideo;
import com.ofweek.live.core.modules.sys.entity.SysAsyncTask;
import com.ofweek.live.core.modules.sys.entity.SysFile;
import com.ofweek.live.core.modules.sys.enums.AsyncTaskStatusEnum;
import com.ofweek.live.core.modules.sys.service.SysAsyncTaskService;
import com.ofweek.live.core.modules.sys.service.SysFileService;
import com.qcloud.api.common.CloudBaseResponse;
import com.qcloud.api.modules.live.dto.CreateLVBChannelResponse;
import com.qcloud.api.modules.live.dto.DescribeLVBChannelListResponse;
import com.qcloud.api.modules.live.dto.DescribeLVBChannelResponse;
import com.qcloud.api.modules.live.service.*;
import com.qcloud.api.modules.vod.dto.CreateClassResponse;
import com.qcloud.api.modules.vod.dto.DescribeAllClassResponse;
import com.qcloud.api.modules.vod.dto.DescribeVodPlayInfoResponse;
import com.qcloud.api.modules.vod.dto.DescribeVodPlayInfoResponse.VodPlayInfo;
import com.qcloud.api.modules.vod.service.CreateClassService;
import com.qcloud.api.modules.vod.service.DescribeAllClassService;
import com.qcloud.api.modules.vod.service.DescribeVodPlayInfoService;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@Service
public class QcloudServiceImpl implements QcloudService {

    private static final Logger logger = LoggerFactory.getLogger(QcloudServiceImpl.class);

    @Resource
    private SysAsyncTaskService sysAsyncTaskService;

    @Resource
    private ExecutorService executorService;

    @Resource
    private SpeakerVideoDao speakerVideoDao;

    @Resource
    private SysFileService sysFileService;

    @Resource
    private RoomReviewDataDao roomReviewDataDao;

    private static boolean initedClass = false;

    private static boolean initedVideo = false;

    private static final int SYNC_SPEAKER_VIDEO = 1;

    private static final int SYNC_REVIEW_VIDEO = 2;

    @PostConstruct
    public void postConstruct() {
        //init();
    }

    @Override
    public QcloudVodPlayInfo getPlayInfo(String fileName) {
        init();
        fileName = QcloudUtils.getFileName(fileName);
        QcloudVodPlayInfo playInfo = QcloudUtils.FileNameCache.get(fileName);
        if (playInfo != null && StringUtils.isNotBlank(playInfo.getMp4PlayUrl())) {
            return playInfo;
        }
        try {
            String searchName = fileName;
            Integer pageNo = 1;
            Integer pageSize = 10;
            DescribeVodPlayInfoResponse response = new DescribeVodPlayInfoService(QcloudUtils.vodCaller).call(searchName, pageNo, pageSize);
            if (response.isSuccess()) {
                VodPlayInfo[] vods = response.getFileSet();
                QcloudUtils.setPlayInfoCache(vods);
            }
        } catch (Exception e) {
            logger.error("qcloud getPlayUrl happen exception!", e);
        }
        return QcloudUtils.FileNameCache.get(fileName);
    }

    private boolean init() {
        if (!initedClass)
            initClass();
        if (!initedVideo)
            initVideo();
        return initedClass && initedVideo;
    }

    private synchronized void initClass() {
        if (!initedClass) {
            try {
                Integer pid = QcloudUtils.getParentClass();
                if (pid != null) {
                    DescribeAllClassResponse response = new DescribeAllClassService(QcloudUtils.vodCaller).call();
                    if (response.isSuccess()) {
                        DescribeAllClassResponse.ClassDirInfo[] classs = response.getData();
                        for (DescribeAllClassResponse.ClassDirInfo classDirInfo : classs) {
                            if (pid.equals(classDirInfo.getInfo().getId())) {
                                for (DescribeAllClassResponse.ClassDirInfo subClassDirInfo : classDirInfo.getSubclass()) {
                                    DescribeAllClassResponse.ClassNodeInfo node = subClassDirInfo.getInfo();
                                    QcloudUtils.ClassNameCache.put(node.getName(), node.getId());
                                }
                            }
                        }
                        initedClass = true;
                    } else {
                        logger.error("live qcloud init class cache fail");
                    }
                }
            } catch (Exception e) {
                logger.error("live qcloud init class happen exception!", e);
            }
        }
    }

    private synchronized void initVideo() {
        if (!initedVideo) {
            try {
                String searchName = QcloudUtils.NAME_PREFIX;
                Integer pageNo = 1;
                Integer pageSize = 50;

                int remainingCount = Integer.MAX_VALUE;
                int failCount = 0;
                while (remainingCount > 0 && failCount < 3) {
                    try {
                        DescribeVodPlayInfoResponse response = new DescribeVodPlayInfoService(QcloudUtils.vodCaller).call(searchName, pageNo,
                                pageSize);
                        if (remainingCount == Integer.MAX_VALUE) {// 初始获取视频总数
                            remainingCount = response.getTotalCount();
                        }
                        if (response.isSuccess()) {
                            VodPlayInfo[] vods = response.getFileSet();
                            QcloudUtils.setPlayInfoCache(vods);
                            remainingCount = remainingCount - vods.length;
                            pageNo++;
                            if (remainingCount <= 0)
                                initedVideo = true;
                        } else {
                            failCount++;
                        }
                    } catch (Exception e) {
                        failCount++;
                        logger.error("init qcloud getPlayUrl loop happen exception!", e);
                    }
                }
            } catch (Exception e) {
                logger.error("init qcloud getPlayUrl happen exception!", e);
            }
        }
    }

    public CreateLVBChannelResponse releaseLive(Integer boothId) {
        String channelName = "B" + boothId;
        String streamName = "S" + boothId;
        return new CreateLVBChannelService(QcloudUtils.liveCaller).call(channelName, streamName, 1);
    }

    public CloudBaseResponse deleteLive(String channelId) {
        List<String> lists = new ArrayList<>();
        lists.add(channelId);
        CloudBaseResponse response = new StopLVBChannelService(QcloudUtils.liveCaller).call(lists);
        if (response.isSuccess()) {
            response = new DeleteLVBChannelService(QcloudUtils.liveCaller).call(lists);
        }
        return response;
    }

    @Override
    public DescribeLVBChannelListResponse getChannels(Integer status) {
        DescribeLVBChannelListResponse response = new DescribeLVBChannelListService(QcloudUtils.liveCaller).call(status, 1, 100);
        if (!response.isSuccess()) {// 第一次获取失败，继续获取一次
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            response = new DescribeLVBChannelListService(QcloudUtils.liveCaller).call(status, 1, 100);
        }
        return response;
    }

    @Override
    public DescribeLVBChannelResponse getChannel(String channelId) {
        DescribeLVBChannelResponse response = new DescribeLVBChannelService(QcloudUtils.liveCaller).call(channelId);
        return response;
    }

    @Override
    public synchronized MethodInvokeResult<T> syncVideo() {
        syncReviewVideo();
        String checkStr = checkTask(SYNC_SPEAKER_VIDEO);
        if (checkStr != null) {
            return new MethodInvokeResult<>(false).setMsg(checkStr);
        }

        return doSyncTask(SYNC_SPEAKER_VIDEO, getSpeakerVideoTask());
    }

    @Override
    public synchronized MethodInvokeResult<T> syncReviewVideo() {
        /*String checkStr = checkTask(SYNC_REVIEW_VIDEO);
        if (checkStr != null) {
            return new MethodInvokeResult<>(false).setMsg(checkStr);
        }

        return doSyncTask(SYNC_REVIEW_VIDEO, getReviewVideoTask());*/
        return new MethodInvokeResult(true);
    }

    private MethodInvokeResult<T> doSyncTask(int type, List<VideoUploadTask> tasks) {
        MethodInvokeResult result = new MethodInvokeResult(true);
        if (tasks.isEmpty()) {
            result.setMsg("全部文件已同步!");
        } else {
            SysAsyncTask asyncTask = addTask(type);
            result.setMsg("任务已添加，待同步" + tasks.size() + "个文件,请到“异步任务管理”菜单下查看进度");
            AsyncTaskMonitor monitor = new AsyncTaskMonitor(asyncTask, tasks.size());
            for (VideoUploadTask uploadTask : tasks) {
                uploadTask.setMonitor(monitor);
                executorService.submit(uploadTask);
            }
        }
        return result;
    }

    /**
     * 检查任务执行前置条件是否满足
     *
     * @param type 任务类型
     * @return
     */
    private String checkTask(int type) {
        int unendTask = sysAsyncTaskService.countUnendTask(type);
        if (unendTask > 0)
            return "已有同步任务在执行中，请稍候再点击同步";

        if (!init())
            return "腾讯服务器网络出问题，请稍候再试";
        return null;
    }

    private List<VideoUploadTask> getSpeakerVideoTask() {
        List<SpeakerVideo> vedios = speakerVideoDao.findAllRoomVideoList();
        List<String> files = Lists.newArrayList();
        vedios.forEach(v -> files.add(v.getFileId()));
        return newTasks(files);
    }

    private List<VideoUploadTask> getReviewVideoTask() {
        RoomReviewData search = new RoomReviewData();
        search.setType(1);
        List<RoomReviewData> datas = roomReviewDataDao.findList(search);
        List<String> files = Lists.newArrayList();
        datas.forEach(d -> files.add(d.getFileId()));
        return newTasks(files);
    }

    private List<VideoUploadTask> newTasks(List<String> files) {
        List<VideoUploadTask> tasks = Lists.newArrayList();
        Set<String> addedUrls = Sets.newHashSet();
        for (String fileId : files) {
            SysFile fileInfo = sysFileService.get(fileId);
            if (fileInfo == null)
                continue;

            File file = new File(LiveEnv.getUploadRoot(), fileInfo.getUri());
            if (file.isFile()) {
                String fileName = file.getName();
                if (addedUrls.contains(fileName)) {
                    continue;
                } else {
                    addedUrls.add(fileName);
                }

                if (QcloudUtils.FileNameCache.contains(fileName)) {
                    continue;
                }
                Integer classId = getCloudClassId(fileInfo.getCreateDate());
                tasks.add(new VideoUploadTask(file, classId));
            }
        }
        return tasks;
    }


    /**
     * @param createTime
     * @return
     */
    private Integer getCloudClassId(Date createTime) {
        String className = DateUtils.formatDate(createTime, "yyyy-MM");
        if (QcloudUtils.ClassNameCache.contains(className)) {
            return QcloudUtils.ClassNameCache.getId(className);
        } else {
            try {
                CreateClassResponse response = new CreateClassService(QcloudUtils.vodCaller).call(className, QcloudUtils.getParentClass());
                if (response.isSuccess()) {
                    Integer classId = Integer.valueOf(response.getNewClassId());
                    QcloudUtils.ClassNameCache.put(className, classId);
                    return classId;
                }
            } catch (Exception e) {
                logger.error("live qcloud create class happen exception!", e);
            }
        }
        return null;
    }

    private SysAsyncTask addTask(int type) {
        SysAsyncTask task = new SysAsyncTask();
        String name = null;
        if (type == SYNC_REVIEW_VIDEO) {
            name = "同步回放视频到云上[" + DateUtils.formatDate(new Date(), null) + "]";
        } else {
            name = "同步主播视频到云上[" + DateUtils.formatDate(new Date(), null) + "]";
        }
        task.setName(name);
        task.setType(type);
        task.setStatus(AsyncTaskStatusEnum.EXECUTING.getCode());
        sysAsyncTaskService.save(task);
        return task;
    }

}