package com.ofweek.live.nio.handlers.room.dto;

import com.ofweek.live.core.modules.room.entity.Room;
import com.ofweek.live.core.modules.speaker.entity.Speaker;

import java.util.Date;

/**
 * Created by tangqian on 2016/8/27.
 */
public class RoomContacterResponse {

    private String name;

    private Integer sex;

    private String job;

    private String department;

    private String mobilePhone;

    private String telephone;

    private String fax;

    private String address;

    public RoomContacterResponse(Speaker speaker) {
        name = speaker.getName();
        sex = speaker.getSex();
        job = speaker.getJob();
        department = speaker.getDepartment();
        mobilePhone = speaker.getMobilePhone();
        telephone = speaker.getTelephone();
        fax = speaker.getFax();
        address = speaker.getAddress();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
