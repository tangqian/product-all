package com.thinkgem.jeesite.modules.live.speaker.entity;

import java.util.List;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class Speaker extends ExDataEntity<Speaker> {

	private static final long serialVersionUID = 1L;

	private String company;

	private String address;

	private String industry;

	private String name;

	private String job;

	private Integer logoId;

	private Integer sex;

	private String department;

	private String mobilePhone;

	private String telephone;

	private String fax;

	private String account;

	private String password;

	private String email;

	private List<String> industriesIdList;

	private Integer source;

	private String url;

	public void setCompany(String value) {
		this.company = value;
	}

	public String getCompany() {
		return this.company;
	}

	public void setAddress(String value) {
		this.address = value;
	}

	public String getAddress() {
		return this.address;
	}

	public void setIndustry(String value) {
		this.industry = value;
	}

	public String getIndustry() {
		return this.industry;
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

	public List<String> getIndustriesIdList() {
		return industriesIdList;
	}

	public void setIndustriesIdList(List<String> industriesIdList) {
		this.industriesIdList = industriesIdList;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
