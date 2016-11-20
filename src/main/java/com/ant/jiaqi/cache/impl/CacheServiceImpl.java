package com.ant.jiaqi.cache.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ant.jiaqi.cache.Cache;
import com.ant.jiaqi.cache.CacheService;
import com.ant.jiaqi.cache.TableInfo;
import com.ant.jiaqi.util.ApplicationContextUtils;

public class CacheServiceImpl implements CacheService{
	private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
	
	/**参数表信息映射*/
	private Map<String, TableInfo> tableMap;
	
	public void setTableMap(Map<String, TableInfo> tableMap) {
		this.tableMap = tableMap;
	}

	@Override
	public Object findByPrimaryKey(String tableId, Object... primaryKeyValues) {
		TableInfo tableInfo = tableMap.get(tableId);
		
		long currentVersion = tableInfo.getVersion();
		int lastIndex = tableInfo.getCaches().length - 1;
		String cacheId = tableInfo.getCaches()[lastIndex];
		Cache cache = (Cache)ApplicationContextUtils.getApplicationContext().getBean(cacheId);
		
		return null;
	}
}
