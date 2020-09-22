package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.PlanStatusAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlanStatusAttrDao {
    @Select("SELECT status_cd, status_desc FROM plan_status_attr")
    List<PlanStatusAttrEntity> planStatusSelector();

    @Select("SELECT status_desc FROM plan_status_attr WHERE status_cd = #{statusCd}")
    String getPlanStatus(@Param("statusCd") Integer statusCd);
}
