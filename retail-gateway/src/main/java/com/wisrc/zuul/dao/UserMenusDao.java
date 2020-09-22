package com.wisrc.zuul.dao;

import com.wisrc.zuul.entity.UserMenusEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMenusDao {

    @Select("select count(*) from v_user_menus_info where user_id = #{userId} and path = #{path} and method_cd = #{methodCd}")
    int checkApiAuth(UserMenusEntity entity);


    @Select("select count(*) from v_api_info where path = #{path} and method_cd = #{methodCd} and auth_flag <> 2")
    int isAuth(UserMenusEntity entity);

}
