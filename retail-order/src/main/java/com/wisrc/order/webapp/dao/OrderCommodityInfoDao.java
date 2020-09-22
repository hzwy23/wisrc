package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.OrderCommodityInfo;
import com.wisrc.order.webapp.entity.OrderCommodityInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderCommodityInfoDao {
    @Select("select uuid,order_id,commodity_id,attribute_desc,unit_price,unit_price_currency,quantity,weight,warehouse_id,status_cd,create_time, create_user,modify_user,modify_time, delete_status, msku_id FROM order_commodity_info")
    List<OrderCommodityInfoEntity> findAll();

    @Select("select uuid, order_id, commodity_id,attribute_desc,unit_price,unit_price_currency,quantity,weight,warehouse_id,status_cd,create_time, create_user,modify_user,modify_time, delete_status from order_commodity_info where order_id = #{orderId}")
    List<OrderCommodityInfoEntity> findByOrderId(@Param("orderId") String orderId);

    @Insert("insert into order_commodity_info(uuid, order_id, commodity_id, attribute_desc, unit_price, unit_price_currency, quantity, weight, warehouse_id, status_cd, create_time, create_user, modify_user, modify_time, delete_status, msku_id) values(#{uuid}, #{orderId}, #{commodityId}, #{attributeDesc}, #{unitPrice}, #{unitPriceCurrency}, #{quantity}, #{weight}, #{warehouseId}, #{statusCd}, #{createTime}, #{createUser}, #{modifyUser}, #{modifyTime}, #{deleteStatus}, #{mskuId})")
    void insert(OrderCommodityInfoEntity ele);

    @Update("update order_commodity_info set delete_status = 1 where order_id = #{orderId}")
    void deleteLogicByOrderId(@Param("orderId") String orderId);

    @Update("update order_commodity_info set delete_status = 1 where uuid = #{uuid}")
    void deleteLogicByKey(@Param("uuid") String uuid);

    @Update("update order_commodity_info set status_cd = #{statusCd} where uuid = #{uuid}")
    void changeStatus(OrderCommodityInfoEntity ele);

    @Update("update order_commodity_info set uuid = #{uuid}, order_id = #{orderId}, commodity_id = #{commodityId}, attribute_desc = #{attributeDesc}, unit_price = #{unitPrice}, unit_price_currency = #{unitPriceCurrency}, quantity = #{quantity}, weight = #{weight}, warehouse_id = #{warehouseId}, status_cd = #{statusCd}, modify_user = #{modifyUser}, modify_time = #{modifyTime} where uuid = #{uuid}")
    void update(OrderCommodityInfoEntity ele);

    @Select("select uuid, order_id, commodity_id, attribute_desc, unit_price, unit_price_currency, quantity, weight, warehouse_id, status_cd, create_time, create_user, modify_user, modify_time, delete_status " +
            "from order_commodity_info where order_id = #{orderId}")
    List<OrderCommodityInfoEntity> getEntity(@Param("orderId") String orderId);

    @Select("SELECT commodity_id, attribute_desc, quantity, weight, warehouse_id, status_name FROM order_commodity_info AS soci LEFT JOIN order_commodity_status_attr " +
            " AS scsa ON scsa.status_cd = soci.status_cd WHERE order_id = #{orderId}")
    List<OrderCommodityInfo> getOrderCommodityByOrderId(@Param("orderId") String orderId) throws Exception;

    @Select("SELECT SUM(weight) FROM `order_commodity_info` WHERE order_id = #{orderId}")
    Double getTotalWeight(@Param("orderId") String orderId) throws Exception;

    @Select("select quantity from order_commodity_info where order_id=#{orderId} and commodity_id=#{commodityId}")
    Integer getNumByOrderIDAndCommodityId(@Param("orderId") String orderId, @Param("commodityId") String commodityId);

    @Select("select uuid, order_id, msku_id, commodity_id,attribute_desc,unit_price,unit_price_currency,quantity,weight,warehouse_id,status_cd,delete_status from order_commodity_info where uuid = #{uuid}")
    OrderCommodityInfoEntity getByUUid(String uuid);
}
