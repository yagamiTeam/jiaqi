package com.ant.jiaqi.mybatis.dao;

import com.ant.jiaqi.mybatis.model.Pltfrm_Jrnl_Tbl;
import com.ant.jiaqi.mybatis.model.Pltfrm_Jrnl_TblKey;
import com.ant.jiaqi.mybatis.model.Pltfrm_Jrnl_TblWithBLOBs;

public interface Pltfrm_Jrnl_TblMapper {
    int deleteByPrimaryKey(Pltfrm_Jrnl_TblKey key);

    int insert(Pltfrm_Jrnl_TblWithBLOBs record);

    int insertSelective(Pltfrm_Jrnl_TblWithBLOBs record);

    Pltfrm_Jrnl_TblWithBLOBs selectByPrimaryKey(Pltfrm_Jrnl_TblKey key);

    int updateByPrimaryKeySelective(Pltfrm_Jrnl_TblWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(Pltfrm_Jrnl_TblWithBLOBs record);

    int updateByPrimaryKey(Pltfrm_Jrnl_Tbl record);
}