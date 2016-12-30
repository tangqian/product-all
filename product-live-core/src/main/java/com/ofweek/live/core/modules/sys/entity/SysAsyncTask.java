package com.ofweek.live.core.modules.sys.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 异步任务
 *
 * @author tangqian
 */
public class SysAsyncTask extends DataEntity<SysAsyncTask> {

    private static final long serialVersionUID = 1L;
    private String name;
    private String param;
    private Integer type;
    private String resultMessage;
    private Date executeTime;
    private Integer status;

    private String createBy;

    private String updateBy;

    public SysAsyncTask() {
        super();
    }

    public SysAsyncTask(String id) {
        super(id);
    }

    @NotNull(message = "任务名称不能为空")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "任务类型不能为空")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public void preInsert() {
        createBy = updateBy = "1";
        super.preInsert();
    }
}