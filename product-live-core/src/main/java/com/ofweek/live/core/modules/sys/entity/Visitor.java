package com.ofweek.live.core.modules.sys.entity;

import com.ofweek.live.core.common.persistence.DataEntity;
import com.ofweek.live.core.common.utils.StringUtils;

/**
 * 
 * @author tangqian
 */
public class Visitor extends DataEntity<Visitor> {

	private static final long serialVersionUID = 1L;
	
	private String ip;

	public void setIp(String value) {
		this.ip = value;
	}
	
	public String getIp() {
		return this.ip;
	}

	public String getName(){
		String padId = StringUtils.leftPad(String.valueOf(id), 5, '0');
		return "游客" + padId;
	}
	
}
