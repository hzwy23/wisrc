package com.wisrc.crawler.webapp.dao;


import com.wisrc.crawler.webapp.entity.RemoveOrderDetailEnity;
import com.wisrc.crawler.webapp.entity.RemoveOrderInfoEnity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface RemoveOrderDao {
    @Insert("insert into remove_order_info (seller_id, order_id, create_time, order_status, apply_remove_num) values " +
            "(#{sellerId}, #{orderId}, #{createTime}, #{orderStatus}, #{applyRemoveNum})")
    void insertRemoveOrderInfo(RemoveOrderInfoEnity removeOrderInfoEnity);

    @Insert("insert into remove_info_detail (msku, fnsku, shipped_quantity, shipment_date, tracking_number, seller_id, order_id, carrier) values" +
            "(#{sku},#{fnsku},#{shippedQuantity},#{shipmentDate},#{trackingNumber},#{sellerId},#{orderId},#{carrier})")
    void insertDetail(Map map);

    @Delete("delete from remove_info_detail where seller_id=#{sellerId} and order_id=#{orderId}")
    void deleteDetail(@Param("sellerId") String sellerId, @Param("orderId") String orderId);

    @Delete("delete from remove_order_info where seller_id=#{sellerId} and order_id=#{orderId}")
    void deleteInfo(@Param("sellerId") String sellerId, @Param("orderId") String orderId);

    @Select("select order_id, tracking_number, carrier, seller_id, fnsku from v_remove_order_shipment where sign_time is null")
    List<RemoveOrderDetailEnity> getNoTransferOrder();

    @Delete("delete from remove_msku_info where seller_id=#{sellerId} and order_id=#{orderId}")
    void deleteMskuInfo(@Param("sellerId") String sellerId, @Param("orderId")String orderId);

    @Insert("insert into remove_msku_info (order_id, seller_id, fnsku, requested_quantity, shipped_quantity, cancelled_quantity, dispose_duantity, inProcess_quantity) values" +
            "(#{orderId},#{sellerId},#{fnsku},#{requestedQuantity},#{shippedQuantity},#{cancelledQuantity},#{disposeDuantity},#{inProcessQuantity})")
    void insertMsku(Map removeOrderMskuEnity);
}
