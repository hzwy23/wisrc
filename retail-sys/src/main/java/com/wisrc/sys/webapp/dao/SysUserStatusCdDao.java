package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysUserStatusCdEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysUserStatusCdDao {
    @Select("SELECT * FROM sys_user_status_cd")
    List<SysUserStatusCdEntity> getSysUserStatusCd() throws Exception;

    @Insert("INSERT INTO sys_user_status_cd(status_cd, status_cd_desc) VALUES(#{statusCd}, #{statusCdDesc})")
    void saveSysUserStatusCd(SysUserStatusCdEntity sysUserStatusCdEntity) throws Exception;

    @Update("UPDATE sys_user_status_cd SET status_cd_desc = #{statusCdDesc} WHERE status_cd = #{statusCd}")
    void editSysUserStatusCd(SysUserStatusCdEntity sysUserStatusCdEntity) throws Exception;
}
