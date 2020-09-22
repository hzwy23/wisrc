package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.WarehouseDocumentTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WarehouseDocumentTypeDao {
    @Select("select document_type,document_type_name from warehouse_document_type_attr")
    List<WarehouseDocumentTypeEntity> findAll();
}
