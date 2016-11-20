package com.ant.jiaqi.cache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ant.jiaqi.cache.DBLoader;
import com.ant.jiaqi.mybatis.dao.Py_Rte_TblMapper;

public class Py_Rte_TblDBLoader implements DBLoader{
	
	@Autowired
	private Py_Rte_TblMapper py_Rte_TblMapper; 
	
	@Override
	public Object load(Object[] primaryKeyValues) {
		return py_Rte_TblMapper.selectByPrimaryKey(primaryKeyValues[0].toString());
	}

	@Override
	public List<?> load(int startRow, int offset) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("startRow", startRow);
		pageMap.put("offset", offset);
		
		return py_Rte_TblMapper.select4List(pageMap);
	}

}
