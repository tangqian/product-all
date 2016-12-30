package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;
import com.thinkgem.jeesite.modules.live.base.config.LiveEnv;

/**
 * 
 * @author tangqian
 */
public class SysFile extends ExDataEntity<SysFile> {

	private static final long serialVersionUID = 1L;
	
	private String uri;
	
	private String originalName;
	
	private Integer size;
	
	private String ext;
	
	private Integer isTemp;
	
	private Integer type;
	
	private Integer subjectType;

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
		return this.size != null ? (int)(this.size / 1048576) : 0;
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
	
	public String getUrl(){
		return LiveEnv.getUploadUrlPrefix() + uri;
	}
}
