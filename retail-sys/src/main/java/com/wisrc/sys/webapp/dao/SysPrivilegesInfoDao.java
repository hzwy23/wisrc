package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysPrivilegesInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysPrivilegesInfoDao {
    @Select("SELECT * FROM sys_privileges_info")
    List<SysPrivilegesInfoEntity> getSysPrivilegesInfo() throws Exception;

    @Insert("INSERT INTO sys_privileges_info(privilege_cd, privilege_name) VALUES(#{privilegeCd}, #{privilegeName})")
    void saveSysPrivilegesInfo(SysPrivilegesInfoEntity sysPrivilegesInfoEntity) throws Exception;

    @Update("UPDATE sys_privileges_info SET privilege_name = #{privilegeName} WHERE privilege_cd = #{privilegeCd}")
    void editSysPrivilegesInfo(SysPrivilegesInfoEntity sysPrivilegesInfoEntity) throws Exception;
}
