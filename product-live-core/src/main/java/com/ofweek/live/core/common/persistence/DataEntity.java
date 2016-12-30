package com.ofweek.live.core.common.persistence;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.ofweek.live.core.common.utils.Validate;

/**
 * 数据Entity类
 * @author tangqian
 * 
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;

	public static String NULL_REFERENCE_ID = "0";
	
	protected Date createDate;	// 创建日期
	protected Date updateDate;	// 更新日期
	
	public DataEntity() {
		super();
	}
	
	public DataEntity(String id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		Validate.checkNotNull(id, "Id is required for insert!");
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		this.updateDate = new Date();
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
