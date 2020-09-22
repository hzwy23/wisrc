package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.InspectionTypeAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InspectionTypeAttrDao {
    @Select("SELECT inspection_type_cd, inspection_type_desc FROM inspection_type_attr")
    List<InspectionTypeAttrEntity> getInspectionTypeAttr() throws Exception;
}
