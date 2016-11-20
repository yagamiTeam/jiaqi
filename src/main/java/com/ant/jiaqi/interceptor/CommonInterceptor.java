package com.ant.jiaqi.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ant.jiaqi.swap.SwapAreaConst;
import com.ant.jiaqi.swap.SwapAreaUtils;

public class CommonInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("preHandler");
		
		String ev_trace_id = String.valueOf(new Date().getTime());
		SwapAreaUtils.setValue(SwapAreaConst.EV_TRACE_ID, ev_trace_id);
		MDC.put(SwapAreaConst.EV_TRACE_ID, ev_trace_id);//MDC采用ThreadLocal维护了一个hashtable
		
		/*try {
			BufferedReader bufferedReader = request.getReader();
			String str = "";
			String whole = "";
			while((str = bufferedReader.readLine()) != null) {
				whole += str;
			}
			logger.debug(whole);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.debug("postHandle");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws Exception {
		logger.debug("afterCompletion");
	}

}
