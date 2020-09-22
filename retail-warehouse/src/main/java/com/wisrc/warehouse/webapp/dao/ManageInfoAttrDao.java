package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.WarehouseStatusAttrEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseTypeAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ManageInfoAttrDao {
    @Select("select  status_cd,status_name from warehouse_status_attr")
    List<WarehouseStatusAttrEntity> manageStatusAttr();

    @Select("select type_cd, type_name from warehouse_type_attr")
    List<WarehouseTypeAttrEntity> manageTypeAttr();
}
