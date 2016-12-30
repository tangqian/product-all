package com.thinkgem.jeesite.modules.live.base.persistence;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 数据Entity类
 * @author tangqian
 * @version 2016-02-24
 */
public abstract class ExDataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;
	
	public static int NULL_REFERENCE_ID = -1;
	
	public static String NULL_REFERENCE_STRING_ID = "-1";
	
	/**
	 * 前端业务表通用字段
	 */
	protected Integer status;
	protected Integer creatorId;
	protected Integer lastOperatorId;
	protected Date createTime;
	protected Date lastOperationTime;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getLastOperatorId() {
		return lastOperatorId;
	}

	public void setLastOperatorId(Integer lastOperatorId) {
		this.lastOperatorId = lastOperatorId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastOperationTime() {
		return lastOperationTime;
	}

	public void setLastOperationTime(Date lastOperationTime) {
		this.lastOperationTime = lastOperationTime;
	}

	/**
	 * 后台系统表通用字段
	 */
	protected User createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected User updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	
	public ExDataEntity() {
		super();
	}
	
	public ExDataEntity(String id) {
		super(id);
	}

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
			this.createBy = user;
		}
		this.createDate = this.updateDate = new Date();
		
		//业务表字段初始值
		lastOperatorId = creatorId = 1;
		if (StringUtils.isNotBlank(user.getId())){
			try{
				lastOperatorId = creatorId = Integer.valueOf(user.getId());
			}catch (Exception e){
			}
		}
		createTime = lastOperationTime = createDate;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
		}
		this.updateDate = new Date();
		
		lastOperatorId = 1;
		if (StringUtils.isNotBlank(user.getId())){
			try{
				lastOperatorId = Integer.valueOf(user.getId());
			}catch (Exception e){
			}
		}
		lastOperationTime = updateDate;
	}
	
	@JsonIgnore
	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	/**
	 * 状态标记（1：正常；2：待审核；3：审核通过；4：审核不通过;5：下架）
	 */
    public final static int FLAG_NORMAL = 1;
    public final static int FLAG_AUITI = 2;
    public final static int FLAG_PASS = 3;
    public final static int FLAG_NOPASS = 4;
    public final static int FLAG_UNSHELVE = 5;

}
