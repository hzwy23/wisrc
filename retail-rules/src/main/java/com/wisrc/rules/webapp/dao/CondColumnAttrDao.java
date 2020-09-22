package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.CondColumnAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CondColumnAttrDao {
    @Select("SELECT cond_column, cond_column_name FROM cond_column_attr")
    List<CondColumnAttrEntity> condColumnSelector() throws Exception;
}
