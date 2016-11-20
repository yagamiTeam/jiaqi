package com.ant.jiaqi.mybatis.dao;

import java.util.Map;

import com.ant.jiaqi.mybatis.model.Sequence;

public interface SequenceMapper {
    int deleteByPrimaryKey(String seqKey);

    int insert(Sequence record);

    int insertSelective(Sequence record);

    Sequence selectByPrimaryKey(String seqKey);

    int updateByPrimaryKeySelective(Sequence record);

    int updateByPrimaryKey(Sequence record);
    
    int updateByMap(Map<String, Object> map);
}