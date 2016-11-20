package com.ant.jiaqi.scan.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ant.jiaqi.scan.ScanDos;
import com.ant.jiaqi.scan.ScanHandler;

public class ScanRunnable implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(ScanRunnable.class);
	
	//数据库访问Dos
	private ScanDos scanDos;
	//扫描处理
	private ScanHandler scanHandler;
	//一条记录	
	private Object record;
	
	public ScanRunnable(ScanDos scanDos, ScanHandler scanHandler, Object record) {
		this.scanDos = scanDos;
		this.scanHandler = scanHandler;
		this.record = record;
	}
	
	@Override
	public void run() {
		boolean result = scanDos.lockRecord(record); 
		
		if(!result) {
			logger.debug("加锁失败");
			return;
		}
		
		logger.debug("加锁成功");
		scanHandler.doHandler(record);
		
		scanDos.unlockRecord(record);
	}
	
}
