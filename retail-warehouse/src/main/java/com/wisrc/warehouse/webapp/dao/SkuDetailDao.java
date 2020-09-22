package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.vo.stockVO.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SkuDetailDao {
    @Select("SELECT sku_id, warehouse_id, warehouse_name, oversea_qty as number from warehouse_sku_stock_details_info where sku_id=#{skuId} and as_of_date=#{date} and substr(warehouse_id,1,1)='B' and oversea_qty>0")
    List<ProxyVirtual> getSkuStockOversea(@Param("skuId") String skuId, @Param("date") String date);

    @Select("SELECT sku_id, warehouse_id, warehouse_name, local_qty as number from warehouse_sku_stock_details_info where sku_id=#{skuId} and as_of_date=#{date} and substr(warehouse_id,1,1)='A' and local_qty>0")
    List<ProxyVirtual> getLocalStockDetail(@Param("skuId") String skuId, @Param("date") String date);

    @Select("select batch_number, start_warehouse, end_warehouse, delivery_number, delivery_time, estimate_date, shipment_channel, logistics_id from " +
            " warehouse_fba_onway_details_info where sku_id=#{skuId} and msku_id=#{mskuId} and as_of_date=#{date}")
    List<FbaOnwayDetailVO> getFbaOnwayDetail(@Param("skuId") String skuId, @Param("date") String date, @Param("mskuId") String mskuId);

    @Select("select order_id as transfer_order_cd, start_warehouse_id, start_warehouse_name as start_warehouse, end_warehouse_id, end_warehouse_name as end_warehouse, transfer_quantity, delivery_time, estimate_date, shipment_channel, logistics_id " +
            " from warehouse_oversea_transfer_detail_info where sku_id=#{skuId} and as_of_date=#{date}")
    List<OverseaTransferDetailVO> getTransferDetail(@Param("skuId") String skuId, @Param("date") String date);

    @Select("select document_cd as order_id,document_type_desc as order_type,warehouse_name,estimate_arrival_date as estimate_time,onway_num from " +
            "warehouse_sku_onway_details_info where sku_id=#{skuId} and as_of_date=#{date} and warehouse_type_cd='A'")
    List<LocalOnwayDetailVO> getLocalOnwayDetail(@Param("skuId") String skuId, @Param("date") String date);

    @Select("SELECT order_id, sku_id, delivery_quantity, quantity, delivery_time, number, undelivery_quantity " +
            "from warehouse_sku_production_details_info where sku_id=#{skuId} and as_of_date=#{date}")
    List<ProductDetailVO> getProductDetail(@Param("skuId") String skuId, @Param("date") String date);

    @Select("select order_id,  warehouse_id, warehouse_name, msku_id, fn_sku_id, apply_return_num, out_num, sign_num, onway_num " +
            " from warehouse_fba_return_detail_info where sku_id=#{skuId} and msku_id=#{mskuId} and  as_of_date=#{date}")
    List<FbaReturnDetailVO> getFbaReturnDetail(@Param("skuId") String skuId, @Param("date") String date, @Param("mskuId") String mskuId);
}
