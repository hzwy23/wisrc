package com.wisrc.crawler.webapp.dao;

import com.wisrc.crawler.webapp.dao.sql.MskuSaleSql;
import com.wisrc.crawler.webapp.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShipmentInfoLocalDao {
    @Select("select tracking_number, carrier, status, sign_time, delivery_date, expect_delivery_date, on_the_way_day, pickup_date, sign_name, tracking_info from shipment_transfer_info where tracking_number=#{shipmentId} and carrier=#{shipmentType}")
    TracingRecordEnity getShipmentTracingRecord(@Param("shipmentId") String shipmentId, @Param("shipmentType") String shipmentType);

    @Select("select seller_id, order_id, create_time, order_status, apply_remove_num from remove_order_info where order_id=#{removeOrderId} and seller_id=#{sellerId}")
    RemoveOrderInfoEnity getRemoveOrderInfo(@Param("removeOrderId") String removeOrderId, @Param("sellerId") String sellerId);

    @Select("select msku, fnsku, shipped_quantity, shipment_date, tracking_number, seller_id, order_id, carrier from remove_info_detail where order_id=#{removeOrderId} and seller_id=#{sellerId}")
    List<RemoveOrderDetailEnity> getRemoveOrderDetail(@Param("removeOrderId") String removeOrderId, @Param("sellerId") String sellerId);

    @Select("select shipment_name, shipment_status, is_partnered, carrier_name, shipment_type, amount_value, destination_fulfillment_center_id, shipment_id, seller_id from shipment_info where shipment_id=#{shipmentId} and seller_id=#{sellerId}")
    ShipmentInfoEnity getShipmentInfo(@Param("shipmentId") String shipmentId, @Param("sellerId") String sellerId);

    @Select("select shipment_id, seller_id, msku, fnsku, quantity_shipped, quantity_in_case, quantity_received from shipment_info_detail where shipment_id=#{shipmentId} and seller_id=#{sellerId}")
    List<ShipmentInfoDetailEnity> getShipmentInfoDetail(@Param("shipmentId") String shipmentId, @Param("sellerId") String sellerId);

    @Select("select tracing_id from shipment_transfer_rel where shipment_id=#{shipmentId} and seller_id=#{sellerId}")
    List<String> getTracingId(@Param("shipmentId") String shipmentId, @Param("sellerId") String sellerId);

    @Select("select seller_id, msku, data_time as dataDt, one_days_ago_sales as lastNum, two_days_ago_sales as lastTwoNum, three_days_ago_sales as lastThreeNum from msku_sale_info where seller_id=#{sellerId} and msku=#{mskuId} and data_time=#{nowDate}")
    MskuSaleNumEnity getMskuSaleNum(@Param("mskuId") String mskuId, @Param("sellerId") String sellerId, @Param("nowDate") String nowDate);

    @Select("select seller_id, msku, data_time, reserved_customer_orders, reserved_fc_transfers, reserved_fc_processing, afn_fulfillable_quantity, afn_unsellable_quantity from msku_stock_info where seller_id=#{sellerId} and msku=#{mskuId}")
    ShipmentStockEnity getMskuStockInfo(@Param("mskuId") String mskuId, @Param("sellerId") String sellerId);

    @Select("select tracking_info from shipment_transfer_info where tracking_number=#{trackingNumber} and carrier=#{carrier}")
    String getTrackByNumAndCar(@Param("trackingNumber") String trackingNumber, @Param("carrier") String carrier);

    @SelectProvider(type = MskuSaleSql.class, method = "getMskuSaleNumCond")
    List<RecentSaleNumEnity> getMskuSaleNumCond(@Param("stratTime") String stratTime, @Param("endTime") String endTime, @Param("sellerId") String sellerId, @Param("msku") String msku);

    @Select("select seller_id, msku, data_time as dataDt, one_days_ago_sales as lastNum from msku_sale_info where msku=#{mskuId} and seller_id=#{sellerId}  and one_days_ago_sales is not null order by data_time desc limit 0,7")
    List<RecentSaleNumEnity> getPreSevenDaySaleNum(@Param("mskuId") String mskuId, @Param("sellerId") String sellerId);

    @Update("update shipment_transfer_info set sign_time=#{signTime},status=#{status} where tracking_number=#{trackingNumber} and carrier=#{carrier}")
    void updateSignTime(@Param("trackingNumber") String trackingNumber, @Param("carrier") String carrier, @Param("signTime") String signTime, @Param("status") String status);

    @Select("select fnsku, seller_id, sum(shipped_quantity) as shipped_quantity, order_id from remove_msku_info where order_id=#{removeOrderId} group by fnsku,seller_id")
    List<TransferShipmentEnity> getShipmentNum(String removeOrderId);

    @Select("select msku, seller_id, sum(shipped_quantity) as sign_num, order_id from v_remove_order_shipment where order_id=#{removeOrderId} and sign_time is not null group by msku,seller_id")
    List<TransferSignEnity> getSignNum(String removeOrderId);

    @Select("select sign_time as signTime,carrier from shipment_transfer_info where tracking_number=#{tracingId}")
    Map getSignTime(String tracingId);

    @Select("select shipment_id as shipmentId, seller_id as sellerId from shipment_transfer_rel where tracing_id=#{trackingNumber} limit 0,1")
    Map getShipmentAndSellerId(String trackingNumber);

    @Select("select tracking_number from shipment_transfer_info where tracking_number in (${tracingIds}) and sign_time is null")
    List<String> getByIds(@Param("tracingIds") String tracingIds);

    @Select("select tracing_id from shipment_transfer_rel where shipment_id=#{batchNumber}")
    List<String> getTracingIdByBachNumber(String batchNumber);

    @Select("select msku, fnsku, shipped_quantity, shipment_date, tracking_number, seller_id, order_id from remove_info_detail where tracking_number=#{trackingNumber} and fnsku=#{fnsku}")
    List<RemoveOrderDetailEnity> getRemoveOrderDetailEnity(@Param("trackingNumber")String trackingNumber, @Param("fnsku")String fnsku);

    @Update("update shipment_transfer_info set sign_time=#{signTime},status=#{status} where tracking_number=#{trackingNumber}")
    void updateTime(@Param("trackingNumber") String trackingNumber, @Param("signTime") String signTime, @Param("status") String status);

    @Select("select msku, fnsku, shipped_quantity, shipment_date, tracking_number, seller_id, order_id from remove_info_detail where tracking_number=#{trackingNumber}")
    List<RemoveOrderDetailEnity> getRemoveOrderDetailEnityList(String trackingNumber);
}
