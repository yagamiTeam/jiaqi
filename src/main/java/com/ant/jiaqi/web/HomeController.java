package com.ant.jiaqi.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ant.jiaqi.service.HomeService;
import com.ant.jiaqi.util.PropertiesUtils;
import com.ant.jiaqi.vo.HomeReqVo;

@RestController
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private HomeService homeService;
	
	@RequestMapping(value="/home", method=RequestMethod.POST)
	public String home(@RequestBody HomeReqVo homeReqVo) {
		
		logger.debug("付款人名称: " + homeReqVo.getPyPsnNm());
		logger.debug("付款人账号: " + homeReqVo.getPyPsnAccno());
		logger.debug("汇款金额: " + homeReqVo.getRmtAmt());
		logger.debug("工作日期: " + homeReqVo.getWkdyPrd());
		
		String value = PropertiesUtils.getProperty("master.driver");
		logger.debug("value: " + value);
		
		return "home";
	}
	
}
