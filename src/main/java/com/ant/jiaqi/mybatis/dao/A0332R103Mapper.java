package com.ant.jiaqi.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.ant.jiaqi.mybatis.model.Micr_Dbt_Bsndtl_Rgstbook;

public interface A0332R103Mapper {
    List<Object> select_01_MicrDbt(Map<String, Object> pageMap);

    int update_01_to_14_MicrDbt(Micr_Dbt_Bsndtl_Rgstbook record);

    int update_14_to_02_MicrDbt(Micr_Dbt_Bsndtl_Rgstbook record);
    
    int update_14_to_01_MicrDbt(Micr_Dbt_Bsndtl_Rgstbook record);
}