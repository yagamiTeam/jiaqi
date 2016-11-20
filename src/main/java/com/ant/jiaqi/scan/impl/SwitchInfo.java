package com.ant.jiaqi.scan.impl;

import java.util.Date;

public class SwitchInfo {
	private volatile boolean value;
	
	private Date lastModifyTime;

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
}
