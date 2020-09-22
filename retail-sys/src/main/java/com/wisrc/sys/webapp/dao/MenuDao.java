package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysRoleMenusEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuDao {

    @Select(" SELECT\n" +
            "  a.role_id AS roleId,\n" +
            "  a.menu_id AS menuId,\n" +
            "  a.uuid    AS uuid\n" +
            "FROM sys_role_menus a\n" +
            "WHERE a.role_id = #{roleId}")
    List<SysRoleMenusEntity> getChosenMenu(String roleId);
}
