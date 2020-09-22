package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.OrderDeliveryChannelAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDeliveryChannelAttrDao {

    @Select("select delivery_channel_cd, delivery_channel_name from order_delivery_channel_attr")
    List<OrderDeliveryChannelAttrEntity> findAll();
}
