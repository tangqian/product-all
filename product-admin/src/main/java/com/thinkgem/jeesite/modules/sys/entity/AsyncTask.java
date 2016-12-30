package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * 异步任务
 * 
 * @author fengjingwei
 *
 */
public class AsyncTask extends ExDataEntity<AsyncTask> {

	private static final long serialVersionUID = 1L;
	private String name;
	private String param;
	private Integer type;// 1:图片 2:文件 3:视频 4:资料
	private String resultMessage;
	private Date executeTime;

	public AsyncTask() {
		super();
	}

	public AsyncTask(String id) {
		super(id);
	}

	@Length(min = 1, max = 50, message = "任务名称长度必须介于 1 和 50 之间")
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

}