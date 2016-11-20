package com.ant.jiaqi.enums;

public enum CacheType {
	PUBLIC("public"),
	PRIVATE("private");
	
	private String value;
	
	private CacheType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
