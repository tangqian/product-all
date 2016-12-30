package com.thinkgem.jeesite.modules.live.speaker.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class SpeakerWaiter extends ExDataEntity<SpeakerWaiter> {

	private static final long serialVersionUID = 1L;

	private Speaker speaker;

	private String name;

	private String job;

	private String logoId;

	private Integer sex;

	private String department;

	private String mobilePhone;

	private String telephone;

	private String fax;

	private String account;

	private String password;

	private String email;

	public SpeakerWaiter() {

	}

	public SpeakerWaiter(String speakerId) {
		setSpeakerId(speakerId);
	}

	public void setSpeakerId(String value) {
		if (speaker == null)
			speaker = new Speaker();
		speaker.setId(value);
	}

	public String getSpeakerId() {
		return speaker != null ? speaker.getId() : null;
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

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void preInsert() {
		setSpeakerId(getCurrentUser() == null ? "0" : getCurrentUser().getId());
		super.preInsert();
	}

}
