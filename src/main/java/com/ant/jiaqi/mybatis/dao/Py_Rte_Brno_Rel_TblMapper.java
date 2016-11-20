package com.ant.jiaqi.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.ant.jiaqi.mybatis.model.Py_Rte_Brno_Rel_Tbl;
import com.ant.jiaqi.mybatis.model.Py_Rte_Brno_Rel_TblKey;

public interface Py_Rte_Brno_Rel_TblMapper {
    int deleteByPrimaryKey(Py_Rte_Brno_Rel_TblKey key);

    int insert(Py_Rte_Brno_Rel_Tbl record);

    int insertSelective(Py_Rte_Brno_Rel_Tbl record);

    Py_Rte_Brno_Rel_Tbl selectByPrimaryKey(Py_Rte_Brno_Rel_TblKey key);

    List<Object> select4List(Map<String, Object> pageMap);
    
    int updateByPrimaryKeySelective(Py_Rte_Brno_Rel_Tbl record);

    int updateByPrimaryKey(Py_Rte_Brno_Rel_Tbl record);
}