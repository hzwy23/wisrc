package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.ProcessTaskStatusAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProcessTaskStatusAttrDao {
    @Select("select status_cd, status_name from process_task_status_attr where status_cd=#{statusCd}")
    ProcessTaskStatusAttrEntity getStatusDetail(@Param("statusCd") String statusCd);

    @Select("select status_cd, status_name from process_task_status_attr")
    List<ProcessTaskStatusAttrEntity> getStatusList();
}
