package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.OrderPayStatusAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderPayStatusAttrDao {
    @Select("select pay_status_cd, pay_status_name from order_pay_status_attr")
    List<OrderPayStatusAttr> findAll();
}
