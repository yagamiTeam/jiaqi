package com.ant.jiaqi.cache.impl;

public class ConfigCache {
	/** 缓存标识：local、redis、db*/
	private String id;
	
	/** 类型：public、private*/
	private String type;
	
	/** 实现类全路径*/
	private String clazzName;
	
	/** 附加信息*/
	private String args;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}
	
	
}
