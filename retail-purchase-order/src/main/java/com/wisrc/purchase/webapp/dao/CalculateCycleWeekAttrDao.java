package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.CalculateCycleWeekAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CalculateCycleWeekAttrDao {
    @Select("SELECT calculate_cycle_week_cd, calculate_cycle_week_desc FROM calculate_cycle_week_attr")
    List<CalculateCycleWeekAttrEntity> weekAttrSelector();
}
