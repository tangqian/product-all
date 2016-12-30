package com.ofweek.live.core.modules.speaker.entity;

import com.ofweek.live.core.common.persistence.DataEntity;
import com.ofweek.live.core.modules.sys.entity.EnterpriseUser;
import com.ofweek.live.core.modules.sys.entity.GeneralUser;
import com.ofweek.live.core.modules.sys.entity.User;

/**
 * @author tangqian
 */
public class SpeakerWaiter extends DataEntity<SpeakerWaiter> implements GeneralUser, EnterpriseUser {

    private static final long serialVersionUID = 1L;

    private User user;

    private Speaker speaker;

    private String name;

    private String job;

    private String logoId;

    private Integer sex;

    private String department;

    private String mobilePhone;

    private String telephone;

    private String fax;
    
    private String uri;

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

    @Override
    public String getCompany() {
        return speaker.getCompany();
    }

    @Override
    public String getAddress() {
        return speaker.getAddress();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
	@Override
	public void preInsert() {
		setSpeakerId(getCurrentUser() == null ? "0" : getCurrentUser().getId());
		super.preInsert();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
