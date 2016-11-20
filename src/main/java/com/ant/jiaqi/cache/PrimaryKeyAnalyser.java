package com.ant.jiaqi.cache;

public interface PrimaryKeyAnalyser {
	/**
	 * 从记录对象中取出各个主键值，分别转换为字符串，然后以数组形式返回
	 * @param tableId	参数表
	 * @param object	记录对象
	 * @return			字符串形式的主键数组
	 */
	public String[] analyse(String tableId, Object object);
	
	/**
	 * 将Object形式的主键数组转换为字符串形式的主键数组
	 * @param tableId
	 * @param primaryKeyValues
	 * @return
	 */
	public String[] analyse(String tableId, Object[] primaryKeyValues);
}
