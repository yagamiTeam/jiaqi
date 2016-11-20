package com.ant.jiaqi.cache;


public abstract class Cache {
	
	/** 类型：public、private*/
	protected String type;
	
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 向指定参数表，添加参数记录
	 * @param tableId	参数表标识
	 * @param version	版本号
	 * @param object	参数记录
	 */
	public abstract void put(String tableId, long version, Object object);
	
	/**
	 * 使用主键，获取指定参数表的参数记录
	 * @param tableId
	 * @param version
	 * @param primaryKeyValues
	 * @return
	 */
	public abstract Object get(String tableId, long version, Object[] primaryKeyValues);
}
