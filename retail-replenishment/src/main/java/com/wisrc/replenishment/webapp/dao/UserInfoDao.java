package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserInfoDao {
    @Select("select id,name,age from test_table")
    List<UserInfoEntity> getAll();
}
