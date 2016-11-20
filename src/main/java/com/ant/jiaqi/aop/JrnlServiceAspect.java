package com.ant.jiaqi.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ant.jiaqi.mybatis.model.Pltfrm_Jrnl_TblWithBLOBs;

@Aspect
public class JrnlServiceAspect {
	private static final Logger logger = LoggerFactory.getLogger(JrnlServiceAspect.class);
	
	@Pointcut("execution(* com.ant.jiaqi.web.*.*(..))")
	public void aspectController(){}
	
	/*@Pointcut("execution(* org.springframework.web.servlet.DispatcherServlet.doService(..))")
	public void aspectServlet(){}*/
	
	@Before("aspectController()")
	public void addJrnl(JoinPoint joinPoint) {
		
		Pltfrm_Jrnl_TblWithBLOBs pltf = new Pltfrm_Jrnl_TblWithBLOBs();
		Date today = new Date();
		
		pltf.setOvrlSttnEvTrckNo("");
		pltf.setWkdyPrd(today);
		pltf.setPystSvcNm("");
		pltf.setSysRecvTime(today);
		
		SimpleDateFormat sdf17 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String pystDtbsTm = sdf17.format(today);
		pltf.setPystDtbsTm(pystDtbsTm);
		
		Object[] args = joinPoint.getArgs();
		String jsonStr = "";
		if(args.length > 0) {
			for(int i = 0; i < args.length; i ++) {
				jsonStr += JSON.toJSONString(args[i]);
			}
		}
		logger.debug(jsonStr);
		logger.debug("target---" + joinPoint.getTarget());
		logger.debug("method---" + joinPoint.getSignature().getName());
	} 
	
	/*@Around("aspect()")
	public void around(ProceedingJoinPoint pjp) {
		logger.debug("target---" + pjp.getTarget());
		logger.debug("method---" + pjp.getSignature().getName());
	}*/
}
