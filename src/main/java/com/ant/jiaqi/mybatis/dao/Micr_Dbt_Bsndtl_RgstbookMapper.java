package com.ant.jiaqi.mybatis.dao;

import com.ant.jiaqi.mybatis.model.Micr_Dbt_Bsndtl_Rgstbook;
import com.ant.jiaqi.mybatis.model.Micr_Dbt_Bsndtl_RgstbookKey;

public interface Micr_Dbt_Bsndtl_RgstbookMapper {
    int deleteByPrimaryKey(Micr_Dbt_Bsndtl_RgstbookKey key);

    int insert(Micr_Dbt_Bsndtl_Rgstbook record);

    int insertSelective(Micr_Dbt_Bsndtl_Rgstbook record);

    Micr_Dbt_Bsndtl_Rgstbook selectByPrimaryKey(Micr_Dbt_Bsndtl_RgstbookKey key);

    int updateByPrimaryKeySelective(Micr_Dbt_Bsndtl_Rgstbook record);

    int updateByPrimaryKey(Micr_Dbt_Bsndtl_Rgstbook record);
}