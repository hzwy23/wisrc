package com.wisrc.order.webapp.dao;


import com.wisrc.order.webapp.entity.OrderPayTypeAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderPayTypeAttrDao {

    @Select("select pay_type_cd, pay_type_desc from order_pay_type_attr")
    List<OrderPayTypeAttr> findAll();
}
