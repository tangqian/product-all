package com.ofweek.live.core.modules.rpc.qcloud.utils;

import com.google.common.collect.Lists;
import com.ofweek.live.core.common.utils.SpringContextHolder;
import com.ofweek.live.core.modules.sys.entity.SysAsyncTask;
import com.ofweek.live.core.modules.sys.enums.AsyncTaskStatusEnum;
import com.ofweek.live.core.modules.sys.service.SysAsyncTaskService;

import java.util.Date;
import java.util.List;

/**
 * Created by tangqian on 2016/9/8.
 */
public class AsyncTaskMonitor {

    private static SysAsyncTaskService sysAsyncTaskService = SpringContextHolder.getBean(SysAsyncTaskService.class);

    private SysAsyncTask asyncTask;

    private int count;

    private List<VideoUploadTask> finishedTasks = Lists.newArrayList();

    public AsyncTaskMonitor(SysAsyncTask asyncTask, int count) {
        this.asyncTask = asyncTask;
        this.count = count;
    }

    public void addFinishedTask(VideoUploadTask task) {
        finishedTasks.add(task);
        if (finishedTasks.size() == count) {
            asyncTask.setStatus(AsyncTaskStatusEnum.SUCCESS.getCode());
            asyncTask.setResultMessage(getAllFeedbackMsg());
            asyncTask.setExecuteTime(new Date());
            sysAsyncTaskService.save(asyncTask);
        }
    }

    private String getAllFeedbackMsg() {
        int allCount = finishedTasks.size();
        int successCount = 0;

        StringBuilder failMsg = new StringBuilder();
        for (VideoUploadTask uploadTask : finishedTasks) {
            if (uploadTask.isSuccess()) {
                successCount++;
            } else {
                failMsg.append(uploadTask.getFileName()).append("同步失败原因：").append(uploadTask.getFeedback()).append("<br/>");
            }
        }

        StringBuilder sb = new StringBuilder("执行完毕，共上传文件").append(allCount).append("个,");
        sb.append("成功").append(successCount).append("个，失败").append(allCount - successCount).append("个。");
        if (failMsg.length() > 0) {
            sb.append("失败详细如下<br/>").append("=================================<br/>");
            sb.append(failMsg);
        }
        return sb.toString();
    }
}
