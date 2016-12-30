package com.thinkgem.jeesite.modules.live.base.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class HomeBanner extends ExDataEntity<HomeBanner> {

	private static final long serialVersionUID = 1L;

	private String name;

	private String fileId;
	
	private Integer sort;

	private String href;

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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
