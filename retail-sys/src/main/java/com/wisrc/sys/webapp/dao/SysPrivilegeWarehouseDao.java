package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysPrivilegeWarehouseEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysPrivilegeWarehouseDao {
    @Select("SELECT * FROM sys_privilege_warehouse")
    List<SysPrivilegeWarehouseEntity> getSysPrivilegeWarehouse() throws Exception;

    @Insert("INSERT INTO sys_privilege_warehouse(uuid, privilege_cd, warehouse_cd) VALUES(#{uuid}, #{privilegeCd}, #{warehouseCd})")
    void saveSysPrivilegeWarehouse(SysPrivilegeWarehouseEntity sysPrivilegeWarehouseEntity) throws Exception;

    @Update("UPDATE sys_privilege_warehouse SET privilege_cd = #{privilegeCd}, warehouse_cd = #{warehouseCd} WHERE uuid = #{uuid}")
    void editSysPrivilegeWarehouse(SysPrivilegeWarehouseEntity sysPrivilegeWarehouseEntity) throws Exception;
}
