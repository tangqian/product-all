package com.ofweek.live.core.modules.sys.entity;

import com.ofweek.live.core.common.persistence.DataEntity;

/**
 * 
 * @author tangqian
 */
public class Sequence extends DataEntity<Sequence> {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Integer nextId;
	
	private int step = 1;

	public void setName(String value) {
		this.id = this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setNextId(Integer value) {
		this.nextId = value;
	}
	
	public Integer getNextId() {
		return this.nextId;
	}

	@Override
	public void setId(String id) {
		this.id = this.name = id;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	
	
}
