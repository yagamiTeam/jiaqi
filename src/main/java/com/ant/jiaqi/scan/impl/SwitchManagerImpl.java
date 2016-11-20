package com.ant.jiaqi.scan.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.ant.jiaqi.scan.SwitchManager;

public class SwitchManagerImpl implements SwitchManager{
	private static final Logger logger = LoggerFactory.getLogger(SwitchManagerImpl.class);
	
	@Value("${scan.switch}")
	private String defaultSwitchKey;
	
	private boolean defaultSwitchValue = true;
	private Map<String, SwitchInfo> switchMap = new ConcurrentHashMap<String, SwitchInfo>();
	
	public SwitchManagerImpl() {
		logger.debug("默认扫描总开关---" + defaultSwitchKey);
		if(defaultSwitchKey != null) {
			this.defaultSwitchValue = !"off".equalsIgnoreCase(defaultSwitchKey);
		}
	}
	
	@Override
	public void register(String id) {
		logger.info("登记扫描服务---" + id);
		SwitchInfo switchInfo = new SwitchInfo();
		switchInfo.setValue(defaultSwitchValue);
		switchInfo.setLastModifyTime(new Date());
		
		switchMap.put(id, switchInfo);
	}

	@Override
	public void turnon(String id) {
		SwitchInfo switchInfo = switchMap.get(id);
		if(switchInfo == null) {
			throw new IllegalArgumentException("不存在扫描服务---" + id);
		}
		
		logger.info("开启扫描服务---" + id);
		synchronized(switchInfo) {
			if(switchInfo.getValue() == true)
				return;
			switchInfo.setValue(true);
			switchInfo.setLastModifyTime(new Date());
		}
	}
	
	@Override
	public void turnoff(String id) {
		SwitchInfo switchInfo = switchMap.get(id);
		if(switchInfo == null) {
			throw new IllegalArgumentException("不存在扫描服务---" + id);
		}
		
		logger.info("关闭扫描服务---" + id);
		synchronized(switchInfo) {
			if(switchInfo.getValue() == false)
				return;
			switchInfo.setValue(false);
			switchInfo.setLastModifyTime(new Date());
		}
	}

	@Override
	public void turnon() {
		logger.info("开启所有扫描服务");
		synchronized(this) {
			for(String id : switchMap.keySet()) {
				this.turnon(id);
			}
		}
	}

	@Override
	public void turnoff() {
		logger.info("关闭所有扫描服务");
		synchronized(this) {
			for(String id : switchMap.keySet()) {
				this.turnoff(id);
			}
		}
	}

	@Override
	public SwitchInfo getSwitch(String id) {
		SwitchInfo switchInfo = switchMap.get(id);
		if(switchInfo == null) {
			throw new IllegalArgumentException("不存在扫描服务---" + id);
		}
		return switchInfo;
	}

	/**对于共享变量，最好返回变量副本*/
	@Override
	public Map<String, SwitchInfo> getSwitch() {
		Map<String, SwitchInfo> result = new HashMap<String, SwitchInfo>();
		
		for(Map.Entry<String, SwitchInfo> entry : switchMap.entrySet()) {
			String id = entry.getKey();
			SwitchInfo switchInfo = entry.getValue();
			result.put(id, switchInfo);
		}
		return result;
	}
}
