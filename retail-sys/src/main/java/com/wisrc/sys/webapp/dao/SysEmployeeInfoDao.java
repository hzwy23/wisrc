package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.dao.sql.SysEmployeeSQL;
import com.wisrc.sys.webapp.entity.SysEmployeeInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysEmployeeInfoDao {
    @Select("SELECT * FROM sys_employee_info")
    List<SysEmployeeInfoEntity> getSysEmployeeInfo() throws Exception;

    @Insert("INSERT INTO sys_employee_info(employee_id, employee_name, status_cd, position_cd) VALUES(#{employeeId}, #{employeeName}, #{statusCd}, #{positionCd})")
    void saveSysEmployeeInfo(SysEmployeeInfoEntity sysEmployeeInfoEntity) throws Exception;

    @Update("UPDATE sys_employee_info SET employee_name = #{employeeName}, status_cd = #{statusCd}, position_cd = #{positionCd} WHERE employee_id = #{employeeId}")
    void editSysEmployeeInfo(SysEmployeeInfoEntity sysEmployeeInfoEntity) throws Exception;

    @SelectProvider(type = SysEmployeeSQL.class, method = "search")
    List<SysEmployeeInfoEntity> search(String ids);
}
