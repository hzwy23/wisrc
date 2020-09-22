package com.wisrc.crawler.webapp.dao;

import com.wisrc.crawler.webapp.entity.ShipmentInfoEnity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShipmentInfoDao {

    @Insert("insert into shipment_info (shipment_name, shipment_status, is_partnered, carrier_name, shipment_type, amount_value, destination_fulfillment_center_id, shipment_id, seller_id) values " +
            "(#{shipmentName},#{shipmentStatus},#{isPartnered},#{carrierName},#{shipmentType},#{amountValue},#{destinationFulfillmentCenterId},#{shipmentId},#{sellerId})")
    void insertShipmentInfo(ShipmentInfoEnity shipmentInfoEnity);

    @Insert("insert into shipment_transfer_rel (shipment_id, seller_id, tracing_id) values (#{shipmentId},#{sellerId},#{tracingId})")
    void insertTransferRel(@Param("shipmentId") String shipmentId, @Param("sellerId") String sellerId, @Param("tracingId") String tracingId);

    @Insert("insert into shipment_info_detail (shipment_id, seller_id, msku, fnsku, quantity_shipped, quantity_in_case, quantity_received) values " +
            "(#{shipmentId},#{sellerId},#{sellerSKU},#{fulfillmentNetworkSKU},#{quantityShipped},#{quantityInCase},#{quantityReceived})")
    void insertShipmentDetail(Map map);

    @Select("select shipment_name, shipment_status, is_partnered, carrier_name, shipment_type, amount_value, destination_fulfillment_center_id, shipment_id, seller_id from shipment_info where shipment_id=#{shipmentId} and seller_id=#{sellerId} ")
    ShipmentInfoEnity getByShipmentIdAndSellerId(@Param("shipmentId") String shipmentId, @Param("sellerId") String sellerId);

    @Update("update shipment_info set shipment_status = #{shipmentStatus}")
    void updateShipmentInfo(ShipmentInfoEnity shipmentInfoEnity);

    @Delete("delete from shipment_transfer_rel where shipment_id=#{shipmentId} and seller_id=#{sellerId}")
    void deleteTransfer(@Param("shipmentId") String shipmentId, @Param("sellerId") String sellerId);

    @Delete("delete from shipment_info_detail where shipment_id=#{shipmentId} and seller_id=#{sellerId}")
    void deleteDetail(@Param("shipmentId") String shipmentId, @Param("sellerId") String sellerId);

    @Select("select shipment_id, seller_id, shipment_name, carrier_name, shipment_status, destination_fulfillment_center_id from shipment_info where shipment_status != 'closed'")
    List<ShipmentInfoEnity> getUncomplete();

    @Delete("delete from shipment_info where shipment_id=#{shipmentId} and seller_id=#{sellerId} ")
    void delete(@Param("shipmentId") String shipmentId, @Param("sellerId") String sellerId);

    @Select("select tracking_number as trackingNumber,carrier from shipment_transfer_info where sign_time is null")
    List<Map<String, String>> getNoSignTimeTransfer();
}
