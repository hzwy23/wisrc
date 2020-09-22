package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.OrderRedeliveryProductInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderRedeliveryProductInfoDao {
    @Select("select uuid, redelivery_id, commodity_id, redelivery_quantity from order_redelivery_product_info")
    List<OrderRedeliveryProductInfo> findAll(@Param("redelivery_id") String redeliveryId);

    @Insert("insert into order_redelivery_product_info(uuid, redelivery_id, commodity_id, redelivery_quantity) values(#{uuid}, #{redeliveryId}, #{commodityId}, #{redeliveryQuantity})")
    void insert(OrderRedeliveryProductInfo ele);
}
