package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysPrivilegeWarehouseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WarehouseDao {
    @Select(" SELECT\n" +
            "  a.uuid         AS uuid,\n" +
            "  a.privilege_cd AS privilegeCd,\n" +
            "  a.warehouse_cd  AS warehouseCd\n" +
            "FROM sys_privilege_warehouse a\n" +
            "WHERE a.privilege_cd = #{authId} ")
    List<SysPrivilegeWarehouseEntity> getPrivilegeWarehouse(String authId);

    @Select(" SELECT\n" +
            "  a.uuid         AS uuid,\n" +
            "  a.privilege_cd AS privilegeCd,\n" +
            "  a.warehouse_cd  AS warehouseCd\n" +
            "FROM sys_privilege_warehouse a\n" +
            "WHERE a.privilege_cd != #{authId} ")
    List<SysPrivilegeWarehouseEntity> getPrivilegeWarehouseUnAuth(String authId);
}
