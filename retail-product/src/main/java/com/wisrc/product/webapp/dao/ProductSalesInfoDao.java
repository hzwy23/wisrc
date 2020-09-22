package com.wisrc.product.webapp.dao;

import com.wisrc.product.webapp.entity.ProductSalesInfoEntity;
import com.wisrc.product.webapp.entity.ProductSalesStatusCdAttr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProductSalesInfoDao {
    @Insert(" insert into erp_product_sales_info (sku_id,sales_status_cd,safety_stock_days,international_transport_days)values(#{skuId},#{salesStatusCd},#{safetyStockDays},#{internationalTransportDays}) ")
    void add(ProductSalesInfoEntity productSalesEntity);

    @Update(" update erp_product_sales_info set sales_status_cd = #{ salesStatusCd} ,safety_stock_days = #{ safetyStockDays},international_transport_days = #{internationalTransportDays} where sku_id = #{skuId}  ")
    void update(ProductSalesInfoEntity productSalesEntity);

    @Select(" select sku_id as skuId ,sales_status_cd as salesStatusCd ,safety_stock_days as safetyStockDays,international_transport_days as internationalTransportDays from erp_product_sales_info where sku_id = #{skuId} ")
    ProductSalesInfoEntity findBySkuId(String skuId);

    @Select(" select  sales_status_cd as salesStatusCd,sales_status_desc as  salesStatusDesc from erp_product_sales_status_attr ")
    List<ProductSalesStatusCdAttr> getSalesAttr();

    @Select(" select sku_id as skuId ,sales_status_cd as salesStatusCd ,safety_stock_days as safetyStockDays,international_transport_days as internationalTransportDays from erp_product_sales_info where sku_id in ${skuIds} ")
    List<LinkedHashMap> batchFind(Map<String, String> paraMap);

    @Select(" SELECT\n" +
            "  sku_id                       AS skuId,\n" +
            "  sales_status_cd              AS salesStatusCd,\n" +
            "  safety_stock_days            AS safetyStockDays,\n" +
            "  international_transport_days AS internationalTransportDays\n" +
            "FROM erp_product_sales_info\n" +
            "WHERE sku_id in ${skuId} ")
    List<ProductSalesInfoEntity> batchFindPost(ProductSalesInfoEntity entity);
}
