package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.InspectionProductDetailsStatusAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InspectionProductDetailsStatusDao {
    @Select("SELECT status_cd, status_desc FROM inspection_product_details_status_attr")
    List<InspectionProductDetailsStatusAttrEntity> detailsStatusSelector() throws Exception;

    @Select("SELECT status_cd from arrival_product_details_info WHERE arrival_id = #{arrivalId} and sku_id = #{skuId}")
    Integer getStatusByArrivalIdAndSkuId(@Param("arrivalId") String arrivalId,
                                         @Param("skuId") String skuId);
}


