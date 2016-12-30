package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.modules.live.base.persistence.ExDataEntity;

/**
 * @version 1.0
 * @author tangqian
 */
public class Sequence extends ExDataEntity<Sequence> {

	private static final long serialVersionUID = 1L;

	private Integer nextId;

	public Integer getNextId() {
		return nextId;
	}

	public void setNextId(Integer nextId) {
		this.nextId = nextId;
	}

}
