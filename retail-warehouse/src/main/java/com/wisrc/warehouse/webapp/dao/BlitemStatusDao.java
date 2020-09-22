package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.BlitemStatusEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlitemStatusDao {
    @Select("select status_cd,status_desc\n" +
            "  from blitem_status_attr")
    List<BlitemStatusEntity> findAll();

    @Select("select status_cd,status_desc\n" +
            "  from blitem_status_attr\n" +
            "where status_cd = #{statusCd} ")
    BlitemStatusEntity finById(@Param("statusCd") String statusCd);
}
