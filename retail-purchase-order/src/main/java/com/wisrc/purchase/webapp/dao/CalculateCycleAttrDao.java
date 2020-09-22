package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.CalculateCycleAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CalculateCycleAttrDao {
    @Select("SELECT calculate_cycle_cd, calculate_cycle_desc FROM calculate_cycle_attr")
    List<CalculateCycleAttrEntity> dayWeekSelector();
}
