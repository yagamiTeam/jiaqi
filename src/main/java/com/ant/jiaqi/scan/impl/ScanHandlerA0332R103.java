package com.ant.jiaqi.scan.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ant.jiaqi.mybatis.model.Micr_Dbt_Bsndtl_Rgstbook;
import com.ant.jiaqi.scan.ScanHandler;

public class ScanHandlerA0332R103 implements ScanHandler{

	private static final Logger logger = LoggerFactory.getLogger(ScanHandlerA0332R103.class);
	
	@Override
	public void doHandler(Object record) {
		Micr_Dbt_Bsndtl_Rgstbook micr_Dbt_Bsndtl_Rgstbook = (Micr_Dbt_Bsndtl_Rgstbook)record;
		logger.debug(micr_Dbt_Bsndtl_Rgstbook.getMsgidno() + "---" + micr_Dbt_Bsndtl_Rgstbook.getRmtArId());
	}

}
