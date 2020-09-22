package com.wisrc.basic.dao;

import com.wisrc.basic.entity.ShipmentCompanyCodeAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShipmentCompanyCodeAttrDao {
    @Select("SELECT * FROM shipment_company_code_attr")
    List<ShipmentCompanyCodeAttrEntity> shipmentCodeList();
}
