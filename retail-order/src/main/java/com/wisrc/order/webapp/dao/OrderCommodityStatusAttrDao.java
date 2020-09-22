package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.OrderCommodityStatusAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderCommodityStatusAttrDao {
    @Select("select status_cd, status_name from order_commodity_status_attr")
    List<OrderCommodityStatusAttrEntity> findAll();
}
