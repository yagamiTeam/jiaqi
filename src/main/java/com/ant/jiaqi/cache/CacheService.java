package com.ant.jiaqi.cache;

public interface CacheService {
	/**
	 * 主键查询指定参数表的记录
	 * @param tableId
	 * @param primaryKeyValues
	 * @return
	 */
	public Object findByPrimaryKey(String tableId, Object... primaryKeyValues);
}
