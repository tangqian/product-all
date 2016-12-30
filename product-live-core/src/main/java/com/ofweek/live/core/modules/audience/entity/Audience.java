package com.ofweek.live.core.modules.audience.entity;

import java.util.Date;

import com.ofweek.live.core.common.persistence.DataEntity;
import com.ofweek.live.core.modules.room.entity.RoomChat;
import com.ofweek.live.core.modules.speaker.entity.SpeakerData;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.User;
import com.ofweek.live.core.modules.sys.enums.SexEnum;

/**
 * 
 * @author tangqian
 */
public class Audience extends DataEntity<Audience> implements GeneralUser {

	private static final long serialVersionUID = 1L;

	private User user;

	private String company;

	private String country;

	private String province;

	private String address;

	private String name;

	private String job;

	private String logoId;

	private Integer sex;

	private String department;

	private String mobilePhone;

	private String telephone;

	private String fax;

	// 1自注册,2会员,3非会员,4推广
	private Integer bizType;

	private Integer searchType;

	private Date enterDate;

	private SpeakerData speakerData;

	private RoomChat roomChat;

	@Override
	public String getAccount() {
		return user.getAccount();
	}

	@Override
	public String getEmail() {
		return user.getEmail();
	}

	@Override
	public Integer getType() {
		return user.getType();
	}

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

	public void setLogoId(String value) {
		this.logoId = value;
	}

	public String getLogoId() {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 1自注册,2会员,3非会员,4推广
	 * 
	 * @return
	 */
	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	@Override
	public void preInsert() {
		super.preInsert();
		if (sex == null)
			sex = SexEnum.SECRET.getCode();
		if (bizType == null)
			bizType = 1;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public SpeakerData getSpeakerData() {
		if (speakerData == null)
			speakerData = new SpeakerData();
		return speakerData;
	}

	public void setSpeakerData(SpeakerData speakerData) {
		this.speakerData = speakerData;
	}

	public RoomChat getRoomChat() {
		if (roomChat == null)
			roomChat = new RoomChat();
		return roomChat;
	}

	public void setRoomChat(RoomChat roomChat) {
		this.roomChat = roomChat;
	}

	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

}
