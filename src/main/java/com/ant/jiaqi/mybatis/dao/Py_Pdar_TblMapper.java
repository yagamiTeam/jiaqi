package com.ant.jiaqi.mybatis.dao;

import com.ant.jiaqi.mybatis.model.Py_Pdar_Tbl;

public interface Py_Pdar_TblMapper {
    int deleteByPrimaryKey(String rmtArId);

    int insert(Py_Pdar_Tbl record);

    int insertSelective(Py_Pdar_Tbl record);

    Py_Pdar_Tbl selectByPrimaryKey(String rmtArId);

    int updateByPrimaryKeySelective(Py_Pdar_Tbl record);

    int updateByPrimaryKey(Py_Pdar_Tbl record);
}