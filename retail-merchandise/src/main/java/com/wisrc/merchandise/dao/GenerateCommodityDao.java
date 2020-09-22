package com.wisrc.merchandise.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;

@Mapper
public interface GenerateCommodityDao {

    /**
     * 更新ID号
     * */
    @Update("update msku_info set id = #{id} where shop_id = #{shopId} and msku_id = #{mskuId}")
    void updateCommodity(@Param("id") String id, @Param("shopId") String shopId , @Param("mskuId") String mskuId);
}
