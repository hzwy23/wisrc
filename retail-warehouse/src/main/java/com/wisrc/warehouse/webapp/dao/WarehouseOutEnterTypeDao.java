package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.WarehouseOutEnterTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WarehouseOutEnterTypeDao {
    @Select("select out_enter_type,out_enter_name from warehouse_out_enter_type_attr")
    List<WarehouseOutEnterTypeEntity> getWarehouseOutEnterType();
}
