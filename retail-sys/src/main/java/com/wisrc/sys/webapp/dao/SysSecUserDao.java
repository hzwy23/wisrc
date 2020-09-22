package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysSecUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysSecUserDao {
    @Select("SELECT * FROM sys_sec_user")
    List<SysSecUserEntity> getSysSecUser() throws Exception;

    @Select("select user_id, password from sys_sec_user where user_id = #{userId}")
    SysSecUserEntity checkPassword(String userId);

    @Insert("INSERT INTO sys_sec_user(user_id, password, error_cnt) VALUES(#{userId}, #{password}, #{errorCnt})")
    void saveSysSecUser(SysSecUserEntity sysSecUserEntity) throws Exception;

    @Update("UPDATE sys_sec_user SET password = #{password}, error_cnt = #{errorCnt} WHERE user_id = #{userId}")
    void editSysSecUser(SysSecUserEntity sysSecUserEntity) throws Exception;
}
