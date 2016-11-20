package com.ant.jiaqi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ant.jiaqi.service.A0332R301Service;
import com.ant.jiaqi.vo.A0332R301ReqVo;
import com.ant.jiaqi.vo.A0332R301RespVo;

@Service
public class A0332R301ServiceImpl implements A0332R301Service{
	private static final Logger logger = LoggerFactory.getLogger(A0332R301ServiceImpl.class);
	
	@Override
	public A0332R301RespVo doService(A0332R301ReqVo a0332R301ReqVo) {
		A0332R301RespVo a0332R301RespVo = new A0332R301RespVo();
		
		/*logger.debug("汇款金额---" + a0332R301ReqVo.getRmtAmt());
		if(a0332R301ReqVo.getRmtAmt() != null) {
			a0332R301RespVo = new A0332R301RespVo();
			if(a0332R301ReqVo.getRmtAmt().compareTo(new BigDecimal(50000)) > 0) {
				logger.debug("金额大于50000走大额");
				a0332R301RespVo.setExgWayCd("301");
			} else {
				logger.debug("金额小于等于50000走小额");
				a0332R301RespVo.setExgWayCd("302");
			}
		}*/
		a0332R301RespVo.setExgWayCd("301");
		
		return a0332R301RespVo;
	}
}
