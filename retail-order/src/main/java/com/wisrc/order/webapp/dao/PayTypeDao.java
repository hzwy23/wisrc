package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.PayTypeEnity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PayTypeDao {
    @Select("select pay_type_cd, pay_type_desc from order_pay_type_attr")
    List<PayTypeEnity> getAll();
}
