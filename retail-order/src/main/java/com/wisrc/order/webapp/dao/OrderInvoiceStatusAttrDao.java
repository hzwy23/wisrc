package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.OrderInvoiceStatusAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderInvoiceStatusAttrDao {
    @Select("select status_cd, status_desc from order_invoice_status_attr")
    List<OrderInvoiceStatusAttrEntity> findAll();
}
