package com.ofweek.live.core.modules.sys.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class HomeBanner extends DataEntity<HomeBanner> {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String fileId;
	
	private String href;
	
	private String createBy;
	
	private String updateBy;

	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setFileId(String value) {
		this.fileId = value;
	}
	
	public String getFileId() {
		return this.fileId;
	}
	
	public void setHref(String value) {
		this.href = value;
	}
	
	public String getHref() {
		return this.href;
	}
	
	public void setCreateBy(String value) {
		this.createBy = value;
	}
	
	public String getCreateBy() {
		return this.createBy;
	}
	
	public void setUpdateBy(String value) {
		this.updateBy = value;
	}
	
	public String getUpdateBy() {
		return this.updateBy;
	}
	
}
