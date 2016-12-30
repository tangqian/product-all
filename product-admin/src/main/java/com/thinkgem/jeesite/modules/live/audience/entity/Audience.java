package com.thinkgem.jeesite.modules.live.audience.entity;

import com.thinkgem.jeesite.modules.live.base.entity.LiveUser;
import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class Audience extends ExDataEntity<Audience> {

	private static final long serialVersionUID = 1L;

	private String company;

	private String country;

	private String province;

	private String address;

	private String name;

	private String job;

	private Integer logoId;

	private Integer sex;

	private String department;

	private String mobilePhone;

	private String telephone;

	private String fax;

	private Integer bizType;

	private LiveUser user;

	private Integer searchType;

	private Integer roomId;

	public void setCompany(String value) {
		this.company = value;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCountry(String value) {
		this.country = value;
	}

	public String getCountry() {
		return this.country;
	}

	public void setProvince(String value) {
		this.province = value;
	}

	public String getProvince() {
		return this.province;
	}

	public void setAddress(String value) {
		this.address = value;
	}

	public String getAddress() {
		return this.address;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setJob(String value) {
		this.job = value;
	}

	public String getJob() {
		return this.job;
	}

	public void setLogoId(Integer value) {
		this.logoId = value;
	}

	public Integer getLogoId() {
		return this.logoId;
	}

	public void setSex(Integer value) {
		this.sex = value;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setDepartment(String value) {
		this.department = value;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setMobilePhone(String value) {
		this.mobilePhone = value;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setTelephone(String value) {
		this.telephone = value;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setFax(String value) {
		this.fax = value;
	}

	public String getFax() {
		return this.fax;
	}

	public void setBizType(Integer value) {
		this.bizType = value;
	}

	public Integer getBizType() {
		return this.bizType;
	}

	public LiveUser getUser() {
		return user;
	}

	public void setUser(LiveUser user) {
		this.user = user;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

}
