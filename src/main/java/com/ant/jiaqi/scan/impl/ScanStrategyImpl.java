package com.ant.jiaqi.scan.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ant.jiaqi.scan.ScanDos;
import com.ant.jiaqi.scan.ScanStrategy;

public class ScanStrategyImpl implements ScanStrategy{
	private static final Logger logger = LoggerFactory.getLogger(ScanStrategyImpl.class);
	
	//默认扫描记录数
	private static final int DEFAULT_SCAN_COUNT = 1000;
	//数据库访问Dos
	private ScanDos scanDos;
	//一次扫描记录数
	private int scanCount;
	
	public void setScanDos(ScanDos scanDos) {
		this.scanDos = scanDos;
	}

	public void setScanCount(int scanCount) {
		this.scanCount = scanCount;
	}

	@Override
	public List<Object> doScan() {
		int offset = scanCount <= 0 ? DEFAULT_SCAN_COUNT : scanCount;
		
		List<Object> records = scanDos.queryForList(0, offset);
		
		return records;
	}
}
