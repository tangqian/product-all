package com.thinkgem.jeesite.modules.live.audience.vo;

import java.io.Serializable;
import java.util.Date;

import com.thinkgem.jeesite.modules.live.base.entity.LiveUser;
import com.thinkgem.jeesite.modules.live.room.entity.Room;

public class AudienceRegisterVo implements Serializable {

	private static final long serialVersionUID = -6863918948300543306L;
	private String id;
	private Integer roomId;
	private Integer audienceId;
	private String company;
	private LiveUser user;
	private Room room;
	private String country;
	private String province;
	private String address;
	private String name;
	private String job;
	private Integer sex;
	private String department;
	private String mobilePhone;
	private String telephone;
	private String fax;
	private Date createDate;
	private Date updateDate;

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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}