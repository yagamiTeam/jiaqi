package com.ant.jiaqi.cache.impl;

public class ConfigTable {
	/**参数表名*/
	private String id;
	
	/**主键，以逗号分隔*/
	private String primaryKey;
	
	/**参数表的实现类全路径*/
	private String objectClazzName;
	
	/**参数表loader实现类的全路径*/
	private String loaderClazzName;
	
	/**缓存标识*/
	private String location;
	
	/**参数表在redis中的名称*/
	private String redisCacheName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectClazzName() {
		return objectClazzName;
	}

	public void setObjectClazzName(String objectClazzName) {
		this.objectClazzName = objectClazzName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getLoaderClazzName() {
		return loaderClazzName;
	}

	public void setLoaderClazzName(String loaderClazzName) {
		this.loaderClazzName = loaderClazzName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRedisCacheName() {
		return redisCacheName;
	}

	public void setRedisCacheName(String redisCacheName) {
		this.redisCacheName = redisCacheName;
	}
	
	
}
