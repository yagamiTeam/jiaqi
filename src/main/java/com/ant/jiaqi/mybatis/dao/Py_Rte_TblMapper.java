package com.ant.jiaqi.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.ant.jiaqi.mybatis.model.Py_Rte_Tbl;

public interface Py_Rte_TblMapper {
    int deleteByPrimaryKey(String pyRteCd);

    int insert(Py_Rte_Tbl record);

    int insertSelective(Py_Rte_Tbl record);

    Py_Rte_Tbl selectByPrimaryKey(String pyRteCd);
    
    List<Object> select4List(Map<String, Object> pageMap);

    int updateByPrimaryKeySelective(Py_Rte_Tbl record);

    int updateByPrimaryKey(Py_Rte_Tbl record);
}