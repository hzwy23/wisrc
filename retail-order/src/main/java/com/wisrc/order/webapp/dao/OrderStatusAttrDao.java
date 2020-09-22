package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.OrderStatusAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderStatusAttrDao {
    @Select("select status_cd, status_name from order_status_attr")
    List<OrderStatusAttr> findAll();
}
