package com.wisrc.zuul.dao;

import com.wisrc.zuul.entity.UserLoginEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginIdentifyDao {
    @Results({
            @Result(column = "status_cd", property = "statusCd")
    })
    @Select("select user_id as username, password, 1 as status_cd from sys_sec_user where user_id=#{name}")
    UserLoginEntity findByUserId(String name);
}
