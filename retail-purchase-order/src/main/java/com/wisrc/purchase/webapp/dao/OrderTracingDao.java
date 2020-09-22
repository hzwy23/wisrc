package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.OrderTracingSql;
import com.wisrc.purchase.webapp.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface OrderTracingDao {
    @SelectProvider(type = OrderTracingSql.class, method = "findTracingOrderByCond")
    List<OrderTracingDetailEnity> findTracingOrderByCond(@Param("orderId") String orderId,
                                                         @Param("startTime") String startTime,
                                                         @Param("endTime") String endTime,
                                                         @Param("employeeId") String employeeId,
                                                         @Param("supplierId") String supplierId,
                                                         @Param("tiicketOpenCd") int tiicketOpenCd,
                                                         @Param("customsTypeCd") int customsTypeCd,
                                                         @Param("sku") String sku,
                                                         @Param("deliveryTypeCd") int deliveryTypeCd,
                                                         @Param("keywords") String keywords,
                                                         @Param("skuIds") String skuIds);

    @Select("select order_id,rejection_id,sku_id,spare_quantity,rejection_date,reject_quantity from rejection_purchase_rejection_details_info where sku_id=#{skuId} and order_id=#{orderId} and delete_status=0")
    List<OrderTracingRejectionEnity> getTracingRejection(@Param("skuId") String skuId, @Param("orderId") String orderId);

    @Select("select order_id,return_bill,sku_id,return_quantity,spare_quantity,create_date from purchase_purchase_return_info where sku_id=#{skuId} and order_id=#{orderId} and delete_status=0")
    List<OrderTracingReturnEnity> getTracingReturnEnity(@Param("skuId") String skuId, @Param("orderId") String orderId);

    @Select("select order_id,entry_id,sku_id,entry_num,entry_frets,entry_time from warehouse_warehouse_product_info where sku_id=#{skuId} and order_id=#{orderId} and delete_status=0")
    List<OrderTracingWareHouseEnity> getTracingWareHouseEnity(@Param("skuId") String skuId, @Param("orderId") String orderId);

    @Select("select purchase_order_id,arrival_id,sku_id,inspection_quantity,qualified_qualified,inspection_quantity,finish_quantity from arrival_basis_arrival_product_info where sku_id=#{skuId} and purchase_order_id=#{orderId}")
    List<OrderTracingInspectionApplyEnity> getTracingInspectionApply(@Param("skuId") String skuId, @Param("orderId") String orderId);

    @Select("select arrival_id,purchase_order_id As order_id,sku_id,delivery_quantity,receipt_quantity, apply_date from arrival_basis_arrival_product_info where sku_id=#{skuId} and purchase_order_id=#{orderId} and delete_status=0")
    List<OrderTracingArrivalEnity> getTracingArrival(@Param("skuId") String skuId, @Param("orderId") String orderId);

    @Select("select id,delivery_time,number from order_product_delivery_info where id=#{id}")
    List<ProductDeliveryInfoEntity> getDelivery(String id);

    @SelectProvider(type = OrderTracingSql.class, method = "getCount")
    int getCount(@Param("orderId") String orderId,
                 @Param("startTime") String startTime,
                 @Param("endTime") String endTime,
                 @Param("employeeId") String employeeId,
                 @Param("supplierId") String supplierId,
                 @Param("tiicketOpenCd") int tiicketOpenCd,
                 @Param("customsTypeCd") int customsTypeCd,
                 @Param("sku") String sku,
                 @Param("deliveryTypeCd") int deliveryTypeCd,
                 @Param("keywords") String keywords,
                 @Param("skuIds") String skuIds);
}
