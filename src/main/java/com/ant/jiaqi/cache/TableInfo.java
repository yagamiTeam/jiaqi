package com.ant.jiaqi.cache;

public class TableInfo {
	/**主键*/
	private String[] primaryKeys;
	
	/**参数表的实现类全路径*/
	private String objectClazz;
	
	/**多级缓存*/
	private String[] caches;

	/**版本号*/
	private volatile long version;
	
	public String[] getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(String[] primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public String getObjectClazz() {
		return objectClazz;
	}

	public void setObjectClazz(String objectClazz) {
		this.objectClazz = objectClazz;
	}

	public String[] getCaches() {
		return caches;
	}

	public void setCaches(String[] caches) {
		this.caches = caches;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}
