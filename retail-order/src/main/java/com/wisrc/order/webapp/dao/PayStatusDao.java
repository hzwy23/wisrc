package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.PayStatusEnity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PayStatusDao {
    @Select("select pay_status_cd, pay_status_name from order_pay_status_attr")
    List<PayStatusEnity> getPayStatus();
}
