package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysResourceInfoEntity;
import com.wisrc.sys.webapp.entity.SysRoleMenusEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mapper
public interface OperateDao {

    @Select(" SELECT\n" +
            "  a.uuid    AS uuid,\n" +
            "  a.role_id AS roleId,\n" +
            "  a.menu_id AS menuId\n" +
            " FROM sys_role_menus a\n" +
            " WHERE a.role_id = #{roleId} ")
    List<SysRoleMenusEntity> getRoleMenusList(SysRoleMenusEntity map);

    @Results({
            @Result(column = "menu_id", property = "menuId"),
            @Result(column = "menu_name", property = "menuName"),
            @Result(column = "method_cd", property = "methodCd"),
            @Result(column = "always_show", property = "alwaysShow"),
            @Result(column = "meta_title", property = "metaTitle"),
            @Result(column = "meta_icon", property = "metaIcon"),
            @Result(column = "meta_no_cache", property = "metaNoCache"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "status_cd", property = "statusCd"),
            @Result(column = "menu_type", property = "menuType"),
            @Result(column = "sort_number", property = "sortNumber"),
    })
    @Select("select menu_id, menu_name,path,method_cd,component,redirect,always_show,meta_title,meta_icon, hidden,meta_no_cache,parent_id,status_cd,menu_type,sort_number from sys_resource_info order by sort_number")
    CopyOnWriteArrayList<SysResourceInfoEntity> findAllResource();


    @Results({
            @Result(column = "menu_id", property = "menuId"),
            @Result(column = "menu_name", property = "menuName"),
            @Result(column = "method_cd", property = "methodCd"),
            @Result(column = "always_show", property = "alwaysShow"),
            @Result(column = "meta_title", property = "metaTitle"),
            @Result(column = "meta_icon", property = "metaIcon"),
            @Result(column = "meta_no_cache", property = "metaNoCache"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "status_cd", property = "statusCd"),
            @Result(column = "menu_type", property = "menuType"),
            @Result(column = "sort_number", property = "sortNumber"),
    })
    @Select("select menu_id, menu_name,path,method_cd,component,redirect,always_show,meta_title,meta_icon, hidden,meta_no_cache,parent_id,status_cd,menu_type,sort_number from sys_resource_info where menu_type = '4' and parent_id = #{menuId} order by sort_number")
    CopyOnWriteArrayList<SysResourceInfoEntity> getPageHandle(String menuId);
}
