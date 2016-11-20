package com.ant.jiaqi.cache;

import java.util.List;

public interface DBLoader {
	/**
	 * 从数据库加载指定版本号和主键值的记录
	 * 
	 * @param primaryKeyValues 主键值数组
	 * @return 找到的记录对象，否则null
	 */
	public Object load(Object[] primaryKeyValues);
	
	/**
	 * 从数据库加载指定版本号的多条记录
	 * @param startRow 起始行
	 * @param offset 偏移量
	 * @return
	 */
	public List<?> load(int startRow, int offset);
}
