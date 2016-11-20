package com.ant.jiaqi.scan.impl;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.ant.jiaqi.scan.ScanDos;
import com.ant.jiaqi.scan.ScanHandler;
import com.ant.jiaqi.scan.ScanService;
import com.ant.jiaqi.scan.ScanStrategy;
import com.ant.jiaqi.scan.SwitchManager;
import com.ant.jiaqi.util.ApplicationContextUtils;

public class ScanServiceImpl implements ScanService, InitializingBean, BeanNameAware{
	private static final Logger logger = LoggerFactory.getLogger(ScanServiceImpl.class);
	
	//线程池
	private ThreadPoolExecutor threadPoolExecutor;
	//扫描服务标识号
	private String id;
	//扫描策略
	private ScanStrategy scanStrategy;
	//记录的处理
	private ScanHandler scanHandler;
	//数据库访问Dos
	private ScanDos scanDos;
	//扫描开关管理器
	private SwitchManager switchManager;
	
	public void setId(String id) {
		this.id = id;
	}

	public void setScanStrategy(ScanStrategy scanStrategy) {
		this.scanStrategy = scanStrategy;
	}

	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

	public void setScanHandler(ScanHandler scanHandler) {
		this.scanHandler = scanHandler;
	}

	public void setScanDos(ScanDos scanDos) {
		this.scanDos = scanDos;
	}

	@Override
	public void doScanService() {
		if(!this.checkSwitch())
			return;
		
		List<Object> records = scanStrategy.doScan();
		for(Object record : records) {
			threadPoolExecutor.execute(new ScanRunnable(scanDos, scanHandler, record));
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}
	
	@Override
	public void setBeanName(String arg0) {
		
	}
	
	private void init() {
		this.switchManager = ApplicationContextUtils.getApplicationContext().getBean(SwitchManagerImpl.class);
		this.switchManager.register(this.id);
	}
	
	private boolean checkSwitch() {
		SwitchInfo switchInfo = this.switchManager.getSwitch(this.id);
		logger.debug("扫描服务【" + this.id + "】的当前状态---" + switchInfo.getValue());
		return switchInfo.getValue();
		
	}
}
