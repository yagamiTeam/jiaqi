package com.ant.jiaqi.cache.impl;

import java.util.Map;

import com.ant.jiaqi.cache.Cache;
import com.ant.jiaqi.cache.DBLoader;

public class DBCache extends Cache{

	private Map<String, DBLoader> dbLoaderMap;
	
	public void setDbLoaderMap(Map<String, DBLoader> dbLoaderMap) {
		this.dbLoaderMap = dbLoaderMap;
	}
	
	@Override
	public void put(String tableId, long version, Object object) {}

	@Override
	public Object get(String tableId, long version, Object[] primaryKeyValues) {
		DBLoader dbloader = this.dbLoaderMap.get(tableId);
		return dbloader.load(primaryKeyValues);
	}

}
