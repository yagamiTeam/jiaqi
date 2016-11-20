package com.ant.jiaqi.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisCluster;

public class RedisUtils {
	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
	
	@Resource(name="jedisCluster")
	private JedisCluster jedisCluster;
	
	/**String*/
	public void set(String key, String value) {
		jedisCluster.set(key, value);
	}
	public String get(String key) {
		return jedisCluster.get(key);
	}
	
	/**List*/
	public void set(String key, List<String> list) {
		for(String item : list) {
			jedisCluster.rpush(key, item);
		}
	}
	public String get(String key, long index) {
		return jedisCluster.lindex(key, index);
	}
	
	/**Set*/
	public void set(String key, Set<String> set) {
		jedisCluster.sadd(key, (String[])set.toArray());
	}
	
	/**Map*/
	public void set(String key, Map<String, String> map) {
		for(Map.Entry<String, String> entry : map.entrySet()) {
			jedisCluster.hset(key, entry.getKey(), entry.getValue());
		}
	}
}
