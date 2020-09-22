package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.StatusAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StatusAttrDao {
    int type = 1;

    @Select("SELECT status_cd, status_desc FROM status_attr WHERE type = " + type)
    List<StatusAttrEntity> statusSelector() throws Exception;
}
