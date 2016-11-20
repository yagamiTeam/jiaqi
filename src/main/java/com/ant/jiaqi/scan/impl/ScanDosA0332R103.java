package com.ant.jiaqi.scan.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ant.jiaqi.mybatis.dao.A0332R103Mapper;
import com.ant.jiaqi.mybatis.model.Micr_Dbt_Bsndtl_Rgstbook;
import com.ant.jiaqi.scan.ScanDos;

public class ScanDosA0332R103 implements ScanDos{
	private static final Logger logger = LoggerFactory.getLogger(ScanDosA0332R103.class);

	@Autowired
	private A0332R103Mapper a0332R103Mapper;
	
	@Override
	public List<Object> queryForList(int startRow, int offset) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("startRow", startRow);
		pageMap.put("offset", offset);
		
		List<Object> objectList = a0332R103Mapper.select_01_MicrDbt(pageMap);
		
		return objectList;
	}

	@Override
	public boolean lockRecord(Object record) {
		
		int result = a0332R103Mapper.update_01_to_14_MicrDbt((Micr_Dbt_Bsndtl_Rgstbook)record);
		if(result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean unlockRecord(Object record) {
		int result = a0332R103Mapper.update_14_to_02_MicrDbt((Micr_Dbt_Bsndtl_Rgstbook)record);
		if(result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean resetRecord(Object record) {
		int result = a0332R103Mapper.update_14_to_01_MicrDbt((Micr_Dbt_Bsndtl_Rgstbook)record);
		if(result == 1) {
			return true;
		}
		return false;
	}
	
	
}
