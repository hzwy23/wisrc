package com.wisrc.order.webapp.dao;


import com.wisrc.order.webapp.entity.OrderRedeliveryTypeAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderRedeliveryTypeAttrDao {
    @Select("select redelivery_type_cd, redelivery_type_name from order_redelivery_type_attr")
    List<OrderRedeliveryTypeAttr> findAll();
}
