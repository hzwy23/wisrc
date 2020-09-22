package com.wisrc.sales.webapp.dao;

import com.wisrc.sales.webapp.dao.sql.ReplenishmentEstimateSql;
import com.wisrc.sales.webapp.enity.EstimateDetailEnity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface ReplenishmentEstimateDao {
    @SelectProvider(type = ReplenishmentEstimateSql.class, method = "getNum")
    int getNum(@Param("shopId") String shopId, @Param("mskuId") String mskuId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("select estimate_id  from sales_estimate where shop_id=#{shopId} and msku_id=#{mskuId}")
    String getByMskuAndShopId(@Param("mskuId") String mskuId, @Param("shopId") String shopId);

    @SelectProvider(type = ReplenishmentEstimateSql.class, method = "getEstimateDetailByEstimateIdAndCond")
    List<EstimateDetailEnity> getEstimateDetailByEstimateIdAndCond(@Param("estimateId") String estimateId, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
