package com.ant.jiaqi.cache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ant.jiaqi.cache.DBLoader;
import com.ant.jiaqi.mybatis.dao.Py_Rte_Brno_Rel_TblMapper;
import com.ant.jiaqi.mybatis.model.Py_Rte_Brno_Rel_TblKey;

public class Py_Rte_Brno_Rel_TblDBLoader implements DBLoader{

	@Autowired
	private Py_Rte_Brno_Rel_TblMapper py_Rte_Brno_Rel_TblMapper; 
	
	@Override
	public Object load(Object[] primaryKeyValues) {
		Py_Rte_Brno_Rel_TblKey py_Rte_Brno_Rel_TblKey = new Py_Rte_Brno_Rel_TblKey();
		py_Rte_Brno_Rel_TblKey.setPcpBrno(primaryKeyValues[0].toString());
		py_Rte_Brno_Rel_TblKey.setPyRteCd(primaryKeyValues[1].toString());
		
		return py_Rte_Brno_Rel_TblMapper.selectByPrimaryKey(py_Rte_Brno_Rel_TblKey);
	}

	@Override
	public List<?> load(int startRow, int offset) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("startRow", startRow);
		pageMap.put("offset", offset);
		
		return py_Rte_Brno_Rel_TblMapper.select4List(pageMap);
	}

}
