package com.ofweek.live.core.modules.sys.entity;

import com.ofweek.live.core.common.config.LiveEnv;
import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class SysFile extends DataEntity<SysFile> {

	private static final long serialVersionUID = 1L;
	
	private String uri;
	
	private String originalName;
	
	private Integer size;
	
	private String ext;
	
	private Integer isTemp;
	
	private Integer type;

	private Integer sort;

	private String parentId;
	
	private Integer subjectType;
	
	private String createBy;

	public void setUri(String value) {
		this.uri = value;
	}
	
	public String getUri() {
		return this.uri;
	}
	
	public void setOriginalName(String value) {
		this.originalName = value;
	}
	
	public String getOriginalName() {
		return this.originalName;
	}
	
	public void setSize(Integer value) {
		this.size = value;
	}
	
	public Integer getSize() {
		return this.size;
	}
	
	public void setExt(String value) {
		this.ext = value;
	}
	
	public String getExt() {
		return this.ext;
	}
	
	public void setIsTemp(Integer value) {
		this.isTemp = value;
	}
	
	public Integer getIsTemp() {
		return this.isTemp;
	}
	
	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}

	public void setSubjectType(Integer value) {
		this.subjectType = value;
	}
	
	public Integer getSubjectType() {
		return this.subjectType;
	}
	
	public void setCreateBy(String value) {
		this.createBy = value;
	}
	
	public String getCreateBy() {
		return this.createBy;
	}

	public String getUrl(){
		return LiveEnv.getUploadUrlPrefix() + uri;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public void preInsert() {
		User user = getCurrentUser();
		setCreateBy(user == null ? "-1" : user.getId());
		super.preInsert();
	}
}
