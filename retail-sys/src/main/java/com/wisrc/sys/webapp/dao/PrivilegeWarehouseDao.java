package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysPrivilegeWarehouseEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PrivilegeWarehouseDao {
    @Insert("INSERT INTO sys_privilege_warehouse(uuid, privilege_cd, warehouse_cd,create_user,create_time) VALUES(#{uuid}, #{privilegeCd}, #{warehouseCd}, #{createUser}, #{createTime})")
    void insert(SysPrivilegeWarehouseEntity entity);

    @Update(" UPDATE sys_privilege_warehouse SET create_user = #{createUser}, create_time = #{createTime} WHERE privilege_cd = #{privilegeCd} and  warehouse_cd = #{warehouseCd}")
    void updateOnly(SysPrivilegeWarehouseEntity entity);

    @Delete(" DELETE FROM sys_privilege_warehouse WHERE privilege_cd = #{privilegeCd} and  warehouse_cd = #{warehouseCd}")
    void deletePrivilegeWarehouse(SysPrivilegeWarehouseEntity entity);

    @Select("select uuid, warehouse_cd, privilege_cd, create_time, create_user from sys_privilege_warehouse where privilege_cd = #{privilegeCd}")
    List<SysPrivilegeWarehouseEntity> findAll(@Param("privilegeCd") String privilegeCd);
}
