package com.ant.jiaqi.util;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import com.ant.jiaqi.Exception.PropertiesException;

public class PropertiesUtils implements InitializingBean{
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
	
	private static Map<String, String> propertiesMap = new ConcurrentHashMap<String, String>();
	private static Resource[] locations = null;
	private static final int permits = 1;
	private static final Semaphore semaphore = new Semaphore(permits, true);
	private static Timer timer = new Timer(true);
	private static long delay = 30 * 60 * 1000L;//默认启动刷新延迟30分钟
	private static long period = 30 * 60 * 1000L;//默认刷新的时间间隔30分钟
	
	private TimerTask timerTask = new TimerTask(){
		@Override
		public void run() {
			logger.debug("定时刷新参数");
			reloadProperties();
		}
	};
	
	private PropertiesUtils(long delay, long period) {
		PropertiesUtils.delay = delay;
		PropertiesUtils.period = period;
	}
	
	public void setLocations(Resource[] locations) {
		PropertiesUtils.locations = locations;
	}

	public static String getProperty(String key) {
		if(!StringUtils.isEmpty(key)) {
			String value = propertiesMap.get(key);
			return value;
		}
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
	
	/**
	 * 初始化方法，系统启动时加载
	 */
	private void init(){
		if(propertiesMap != null && !propertiesMap.isEmpty()) {
			return;
		}
		
		try{
			semaphore.acquire(permits);
			propertiesMap = loadProperties();
			startTimer(delay, period);
		} catch(Throwable t) {
			logger.error("初始化异常", t);
		} finally {
			semaphore.release(permits);
		}
		
	}
	
	/**
	 * 加载properties到map
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, String> loadProperties() {
		logger.debug("加载properties到map");
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		
		if(locations != null && locations.length > 0) {
			for (Resource resource : locations) {
				try {
					Properties properties = PropertiesLoaderUtils.loadProperties(resource);
					for (Map.Entry entry : properties.entrySet()) {
						if(map.containsKey(entry.getKey())) {
							logger.error("不同配置文件配置了相同的key: " + entry.getKey());
							throw new PropertiesException("不同配置文件配置了相同的key: " + entry.getKey());
						}
						map.put(entry.getKey().toString(), entry.getValue().toString());
					}
				} catch (IOException e) {
					logger.error("加载properties异常", e);
				}
			}
		}
		
		return map;
	}
	
	private void reloadProperties() {
		try{
			semaphore.acquire(permits);
			propertiesMap = loadProperties();
		} catch(Throwable t) {
			logger.error("定时加载异常", t);
		} finally {
			semaphore.release(permits);
		}
		
	}
	
	private void startTimer(long delay, long period) {
		if(delay > 0 && period > 0) {
			timer.schedule(timerTask, delay, period);
		}
	}
	
}
