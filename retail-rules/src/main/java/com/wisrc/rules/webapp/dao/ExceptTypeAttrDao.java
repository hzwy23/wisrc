package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.ExceptTypeAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExceptTypeAttrDao {
    @Select("SELECT except_type_cd, except_type_name FROM except_type_attr ")
    List<ExceptTypeAttrEntity> exceptTypeAll() throws Exception;
}
