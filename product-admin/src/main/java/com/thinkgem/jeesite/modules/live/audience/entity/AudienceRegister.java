package com.thinkgem.jeesite.modules.live.audience.entity;

import java.util.Date;

import com.thinkgem.jeesite.modules.live.base.entity.LiveUser;
import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;
import com.thinkgem.jeesite.modules.live.room.entity.Room;

/**
 * @version 1.0
 * @author tangqian
 */
public class AudienceRegister extends ExDataEntity<AudienceRegister> {

	private static final long serialVersionUID = 1L;

	private Integer roomId;

	private Integer audienceId;

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

	private Integer isSentEmail;

	private Integer searchType;

	private Date registerTimeFrom;

	private Date registerTimeTo;

	private LiveUser user;

	private Room room;

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(Integer audienceId) {
		this.audienceId = audienceId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getLogoId() {
		return logoId;
	}

	public void setLogoId(Integer logoId) {
		this.logoId = logoId;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Integer getIsSentEmail() {
		return isSentEmail;
	}

	public void setIsSentEmail(Integer isSentEmail) {
		this.isSentEmail = isSentEmail;
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

	public LiveUser getUser() {
		return user;
	}

	public void setUser(LiveUser user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}
