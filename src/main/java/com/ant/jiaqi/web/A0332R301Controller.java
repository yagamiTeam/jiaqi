package com.ant.jiaqi.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ant.jiaqi.interceptor.CommonInterceptor;
import com.ant.jiaqi.service.A0332R301Service;
import com.ant.jiaqi.util.ApplicationContextUtils;
import com.ant.jiaqi.vo.A0332R301ReqVo;

@Controller
public class A0332R301Controller extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(A0332R301Controller.class);
	
	@Autowired
	private A0332R301Service a0332R301Service;
	
	@RequestMapping(value = "/A0332R301", method = RequestMethod.POST)
	public ResponseEntity<String> a0332R301(@RequestBody A0332R301ReqVo a0332R301ReqVo) {
		logger.debug("A0332R301---¿ªÊ¼");
		
		return this.returnSuccessMsg();
	}
	
	@RequestMapping(value = "/A0332R301", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> a0332R301(@RequestParam(value="userId") String userId, 
											@RequestParam(value="rmtAmt", required=false) BigDecimal rmtAmt, 
											@RequestParam(value="wkdyPrd", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date wkdyPrd, 
											HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String[]> map = request.getParameterMap();
		for(Map.Entry<String, String[]> entry : map.entrySet()) {
			logger.debug("key: " + entry.getKey());
			for(int i = 0; i < entry.getValue().length; i ++) {
				logger.debug("value" + i + ": " + entry.getValue()[i]);
			}
		}
		
		A0332R301ReqVo a0332R301ReqVo = new A0332R301ReqVo();
		a0332R301Service.doService(a0332R301ReqVo);
		
		return this.returnSuccessMsg();
	}
}
