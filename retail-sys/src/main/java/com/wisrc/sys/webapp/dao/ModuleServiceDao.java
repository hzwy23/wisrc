package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysResourceInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ModuleServiceDao {

    /**
     * 通过menuType查询
     *
     * @param menuType
     * @return
     */
    @Select(" SELECT\n" +
            "  a.menu_id    AS value,\n" +
            "  a.meta_title AS label\n" +
            "FROM sys_resource_info a\n" +
            "WHERE menu_type = #{menuType} ")
    List<Map<String, Object>> getResourceByMenuType(int menuType);


    /**
     * roleId,menuType查询
     */
    @Select(" SELECT\n" +
            "  a.menu_id    AS menuId,\n" +
            "  a.meta_title AS metaTitle\n" +
            "FROM sys_resource_info a,\n" +
            "  sys_role_menus b\n" +
            "WHERE\n" +
            "  1 = 1\n" +
            "  AND a.menu_type = #{menuType}\n" +
            "  AND a.menu_id = b.menu_id\n" +
            "  AND b.role_id = #{roleId} ")
    List<SysResourceInfoEntity> getModuleByRoleId(Map<String, Object> map);
}
