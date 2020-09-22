package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.entity.ReturnWarehouseStatusEnity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReturnStatusWarehouseDao {
    @Select("select return_status_cd, return_status_desc  from return_status_code")
    List<ReturnWarehouseStatusEnity> ggetAllReturnStatus();
}
