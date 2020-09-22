package com.wisrc.order.webapp.dao;


import com.wisrc.order.webapp.entity.OrderRedeliveryInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderRedeliveryInfoDao {
    @Select("select redelivery_id,  order_id,  redelivery_type_cd,  offer_id,  logistics_id,  redelivery_number,  create_user,  create_time,  modify_user,  modify_time from order_redelivery_info")
    List<OrderRedeliveryInfo> findAll();

    @Select("select redelivery_id, order_id, redelivery_type_cd, offer_id, logistics_id, redelivery_number,  create_user,  create_time,  modify_user,  modify_time from order_redelivery_info where order_id = #{orderId}")
    List<OrderRedeliveryInfo> findByOrderId(@Param("orderId") String orderId);

    @Select("select redelivery_id, order_id, redelivery_type_cd, offer_id, logistics_id, redelivery_number,  create_user,  create_time,  modify_user,  modify_time from order_redelivery_info where order_id = #{orderId}")
    List<OrderRedeliveryInfo> findByRedelivery(@Param("redeliveryId") String redeliveryId);

    @Insert("insert into order_redelivery_info(redelivery_id,  order_id,  redelivery_type_cd,  offer_id,  logistics_id,  redelivery_number,  create_user,  create_time,  modify_user,  modify_time) values(#{redeliveryId},  #{orderId},  #{redeliveryTypeCd},  #{offerId},  #{logisticsId},  #{redeliveryNumber},  #{createUser},  #{createTime},  #{modifyUser},  #{modifyTime})")
    void insert(OrderRedeliveryInfo ele);

    @Update("update order_redelivery_info set redelivery_id = #{redeliveryId}, order_id = #{orderId}, redelivery_type_cd = #{redeliveryTypeCd}, offer_id = #{offerId}, logistics_id = #{logisticsId}, redelivery_number = #{redeliveryNumber}, modify_user = #{modifyUser}, modify_time = #{modifyTime}")
    void update(OrderRedeliveryInfo ele);
}
