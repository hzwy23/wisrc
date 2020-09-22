package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysRoleMenusEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysRoleMenusDao {
    @Select("SELECT * FROM sys_role_menus")
    List<SysRoleMenusEntity> getSysRoleMenus() throws Exception;

    @Insert("INSERT INTO sys_role_menus(uuid, menu_id, role_id) VALUES(#{uuid}, #{menuId}, #{roleId})")
    void saveSysRoleMenus(SysRoleMenusEntity sysRoleMenusEntity);

    @Update("UPDATE sys_role_menus SET menu_id = #{menuId}, role_id = #{roleId} WHERE uuid = #{uuid}")
    void editSysRoleMenus(SysRoleMenusEntity sysRoleMenusEntity) throws Exception;

    @Select("SELECT menu_id FROM sys_role_menus WHERE role_id = #{roleId}")
    List<String> getMenuIdsByRoleId(@Param("roleId") String roleId) throws Exception;

    @Select("select t.uuid, t.role_id, t.menu_id, i.menu_type from sys_role_menus t inner join sys_resource_info i on t.menu_id = i.menu_id  where t.role_id = #{roleId}")
    List<SysRoleMenusEntity> findByRoleId(@Param("roleId") String roleId);


    @Delete("DELETE FROM sys_role_menus WHERE role_id = #{roleId} AND menu_id = #{menuId}")
    void deleteSysRoleMenusByMenuIds(@Param("roleId") String roleId, @Param("menuId") String menuId);
}
