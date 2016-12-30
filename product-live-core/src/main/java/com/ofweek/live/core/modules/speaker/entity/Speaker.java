package com.ofweek.live.core.modules.speaker.entity;

import com.ofweek.live.core.common.persistence.DataEntity;
import com.ofweek.live.core.modules.sys.entity.EnterpriseUser;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.User;

/**
 * 
 * @author tangqian
 */
public class Speaker extends DataEntity<Speaker> implements GeneralUser , EnterpriseUser {

	private static final long serialVersionUID = 1L;

	private User user;
	
	private String company;
	
	private String address;
	
	private String industry;
	
	private String name;
	
	private String job;
	
	private String logoId;
	
	private Integer sex;
	
	private String department;
	
	private String mobilePhone;
	
	private String telephone;
	
	private String fax;

	private String email;

	private String url;
	
	public Speaker() {
		
	}
	
	public Speaker(String id) {
		this.id = id;
	}

	public void setCompany(String value) {
		this.company = value;
	}

	@Override
	public String getAccount() {
		return user.getAccount();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getEmail() {
		if(email == null){
			return user.getEmail();
		}
		return email;
	}

	@Override
	public Integer getType() {
		return user.getType();
	}

	@Override
	public String getSpeakerId() {
		return id;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
