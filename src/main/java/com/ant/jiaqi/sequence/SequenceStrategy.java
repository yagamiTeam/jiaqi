package com.ant.jiaqi.sequence;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ant.jiaqi.mybatis.dao.SequenceMapper;
import com.ant.jiaqi.mybatis.model.Sequence;

public class SequenceStrategy {
	private static final Logger logger = LoggerFactory.getLogger(SequenceStrategy.class);
	private static final long MINNUM = 0L;
	private static final long MAXNUM = 9999999999L;
	private static final long CACHE_SIZE = 2000;
	
	private Map<String, Map<String, String>> sequenceMap;
	
	public void setSequenceMap(Map<String, Map<String, String>> sequenceMap) {
		this.sequenceMap = sequenceMap;
	}

	@Autowired
	private SequenceMapper sequenceMapper;
	
	public Region nextRegion(String key) {
		long curSeqValue = 0L;
		long newSeqValue = 0L;
		long start = 0L;
		long end = 0l;
		
		int result = 0;
		while (result == 0) {
			Sequence sequence = sequenceMapper.selectByPrimaryKey(key);
			curSeqValue = sequence.getSeqValue();
			logger.debug("sequence参数表的当前序号: " + curSeqValue);
			
			if(curSeqValue < MINNUM || curSeqValue > MAXNUM) {
				logger.error("当前序号越界，重置为最小值");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("newSeqValue", MINNUM);
				map.put("seqKey", key);
				map.put("curSeqValue", curSeqValue);
				sequenceMapper.updateByMap(map);
				continue;
			}
			
			start = curSeqValue;
			newSeqValue = curSeqValue + CACHE_SIZE;
			if(newSeqValue > MAXNUM) {
				logger.error("新序号超过最大值，重置为最小值");
				newSeqValue = MINNUM;
				end = MAXNUM;
			} else {
				end = newSeqValue - 1;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("newSeqValue", newSeqValue);
			map.put("seqKey", key);
			map.put("curSeqValue", curSeqValue);
			result = sequenceMapper.updateByMap(map);
		}
		
		return new Region(start, end);
	}
}
