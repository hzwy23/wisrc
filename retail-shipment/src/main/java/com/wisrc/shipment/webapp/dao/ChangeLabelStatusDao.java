package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.entity.ChangeLabelStatusEnity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChangeLabelStatusDao {
    @Select("select operation_status_cd, operation_status_name from change_label_status")
    List<ChangeLabelStatusEnity> getAll();
}
