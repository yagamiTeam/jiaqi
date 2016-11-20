package com.ant.jiaqi.service.impl;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ant.jiaqi.mybatis.dao.Micr_Dbt_Bsndtl_RgstbookMapper;
import com.ant.jiaqi.mybatis.dao.Pltfrm_Jrnl_TblMapper;
import com.ant.jiaqi.mybatis.model.Micr_Dbt_Bsndtl_Rgstbook;
import com.ant.jiaqi.mybatis.model.Pltfrm_Jrnl_TblWithBLOBs;
import com.ant.jiaqi.service.HomeService;
import com.ant.jiaqi.util.RedisUtils;

@Service
public class HomeServiceImpl implements HomeService{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);
	
	@Autowired
	private Micr_Dbt_Bsndtl_RgstbookMapper micr_Dbt_Bsndtl_RgstbookMapper;
	
	@Autowired
	private Pltfrm_Jrnl_TblMapper pltfrm_Jrnl_TblMapper;
	
	@Autowired
	private RedisUtils jedisUtils;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void doService() {
		Micr_Dbt_Bsndtl_Rgstbook micr_Dbt_Bsndtl_Param = new Micr_Dbt_Bsndtl_Rgstbook();
		micr_Dbt_Bsndtl_Param.setDtlIdNo("2016091900000000");
		micr_Dbt_Bsndtl_Param.setIttPcprBrno("105100000017");
		micr_Dbt_Bsndtl_Param.setMsgidno("2016091900000000");
		micr_Dbt_Bsndtl_Param.setMsgrpEntrstDt(new Date());
		micr_Dbt_Bsndtl_Param.setMsgrpRcvSndIdcd("01");
		micr_Dbt_Bsndtl_Param.setMsgrpPcsst("02");
		
		micr_Dbt_Bsndtl_RgstbookMapper.updateByPrimaryKeySelective(micr_Dbt_Bsndtl_Param);
		
		/*jedisUtils.set("name", "yagami");
		String value = jedisUtils.get("name");
		logger.debug("name---" + value);*/
		
		/*Pltfrm_Jrnl_TblWithBLOBs pltfrm_Jrnl_TblWithBLOBs = new Pltfrm_Jrnl_TblWithBLOBs();
		pltfrm_Jrnl_TblWithBLOBs.setOvrlSttnEvTrckNo("0123456789012345678901234");
		pltfrm_Jrnl_TblWithBLOBs.setWkdyPrd(new Date());
		pltfrm_Jrnl_TblWithBLOBs.setPystSvcNm("A0332R103");
		pltfrm_Jrnl_TblWithBLOBs.setRmtArId("01234567890123456789012");
		pltfrm_Jrnl_TblWithBLOBs.setSysRecvTime(new Date());
		pltfrm_Jrnl_TblMapper.insertSelective(pltfrm_Jrnl_TblWithBLOBs);*/
	}

}
