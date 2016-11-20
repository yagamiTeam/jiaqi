package com.ant.jiaqi.swap;

import java.util.HashMap;
import java.util.Map;

public class SwapAreaUtils {
	private static ThreadLocal<Map<String, Object>> threadLocalMap = new ThreadLocal<Map<String, Object>>(){
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};
	
	public static Map<String, Object> getSwapArea() {
		return threadLocalMap.get();
	}
	
	public static void setSwapArea(Map<String, Object> map) {
		threadLocalMap.set(map);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String key, Class<T> clazz) {
		Map<String, Object> map = threadLocalMap.get();
		if(map.containsKey(key)) {
			return (T) map.get(key);
		}
		return null;
	}
	
	public static <T> void setValue(String key, T value) {
		Map<String, Object> map = threadLocalMap.get();
		map.put(key, value);
	}
	
}
