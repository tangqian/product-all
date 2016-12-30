package com.thinkgem.jeesite.modules.live.audience.form;

import java.io.Serializable;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.live.audience.vo.AudienceRegisterVo;

public class AudienceRegisterPageForm implements Serializable {

	private static final long serialVersionUID = -5223889932875716866L;
	private String id;
	private Integer roomId;
	private Integer status;
	private String name;
	private Integer searchType;
	private Date registerTimeFrom;
	private Date registerTimeTo;

	private Page<AudienceRegisterVo> page;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public Date getRegisterTimeFrom() {
		return registerTimeFrom;
	}

	public void setRegisterTimeFrom(Date registerTimeFrom) {
		this.registerTimeFrom = registerTimeFrom;
	}

	public Date getRegisterTimeTo() {
		return registerTimeTo;
	}

	public void setRegisterTimeTo(Date registerTimeTo) {
		this.registerTimeTo = registerTimeTo;
	}

	public Page<AudienceRegisterVo> getPage() {
		return page;
	}

	public void setPage(Page<AudienceRegisterVo> page) {
		this.page = page;
	}

}