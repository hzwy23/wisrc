package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysEmployeeStatusAttrEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysEmployeeStatusAttrDao {
    @Select("SELECT * FROM sys_employee_status_attr")
    List<SysEmployeeStatusAttrEntity> getSysEmployeeStatusAttr() throws Exception;

    @Insert("INSERT INTO sys_employee_status_attr(status_cd, status_desc) VALUES(#{statusCd}, #{statusDesc})")
    void saveSysEmployeeStatusAttr(SysEmployeeStatusAttrEntity sysEmployeeStatusAttrEntity) throws Exception;

    @Update("UPDATE sys_employee_status_attr SET status_desc = #{statusDesc} WHERE status_cd = #{statusCd}")
    void editSysEmployeeStatusAttr(SysEmployeeStatusAttrEntity sysEmployeeStatusAttrEntity) throws Exception;
}
