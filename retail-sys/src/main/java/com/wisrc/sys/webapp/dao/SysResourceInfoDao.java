package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysResourceInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Mapper
public interface SysResourceInfoDao {
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
    CopyOnWriteArrayList<SysResourceInfoEntity> findAll();


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
    @Select("select t.menu_id, menu_name,path,method_cd,component,redirect,always_show,meta_title,meta_icon, hidden,meta_no_cache,parent_id,status_cd,menu_type,sort_number from sys_resource_info t inner join v_user_menus_info i on t.menu_id = i.menu_id where i.user_id = #{userId} order by sort_number")
    CopyOnWriteArrayList<SysResourceInfoEntity> findAllFilterByUserId(String userId);


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
    @Select("select menu_id, menu_name,path,method_cd,component,redirect,always_show,meta_title,meta_icon, hidden,meta_no_cache,parent_id,status_cd,menu_type,sort_number from sys_resource_info where menu_type in (1,3) order by sort_number")
    CopyOnWriteArrayList<SysResourceInfoEntity> findAllNodes();


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
    @Select("select t.menu_id, menu_name,path,method_cd,component,redirect,always_show,meta_title,meta_icon, hidden,meta_no_cache,parent_id,status_cd,menu_type,sort_number from sys_resource_info t inner join v_user_menus_info i on t.menu_id = i.menu_id where i.user_id = #{userId} and menu_type in (1,3) order by sort_number")
    CopyOnWriteArrayList<SysResourceInfoEntity> findAllNodesByUserId(String userId);


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
    @Select("select menu_id, menu_name,path,method_cd,component,redirect,always_show,meta_title,meta_icon, hidden,meta_no_cache,parent_id,status_cd,menu_type,sort_number from sys_resource_info where parent_id = '-1' and status_cd = 1 order by sort_number")
    List<SysResourceInfoEntity> findMainMenus();

    @Select("select menu_id from v_user_menus_info i  where i.user_id = #{userId}")
    Set<String> findUserMenus(String userId);


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
    @Select("select menu_id, menu_name,path,method_cd,component,redirect,always_show,meta_title,meta_icon, hidden,meta_no_cache,parent_id,status_cd,menu_type,sort_number from sys_resource_info where menu_type in ( '2','3' ) order by sort_number")
    List<SysResourceInfoEntity> findPage();


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
    @Select("select t.menu_id, menu_name,path,method_cd,component,redirect,always_show,meta_title,meta_icon, hidden,meta_no_cache,parent_id,status_cd,menu_type,sort_number from sys_resource_info t inner join v_user_menus_info i on t.menu_id = i.menu_id where menu_type in ( '2','3' ) and i.user_id = #{userId} order by sort_number")
    List<SysResourceInfoEntity> findPageById(@Param("userId") String userId);

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
    @Select("select menu_id, menu_name,path,method_cd,component,redirect,always_show,meta_title,meta_icon, hidden,meta_no_cache,parent_id,status_cd,menu_type,sort_number from sys_resource_info where menu_id = #{menuId}")
    SysResourceInfoEntity getMenuDetails(String menuId);


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

    @Delete("delete from sys_resource_info where menu_id = #{menuId}")
    void delete(String menuId);

    @Insert("insert into sys_resource_info(menu_id, menu_name, path, component, redirect, method_cd, always_show, meta_title, meta_icon, meta_no_cache, parent_id, status_cd, menu_type, hidden, sort_number) values(#{menuId},#{menuName}, #{path}, #{component}, #{redirect}, #{methodCd}, #{alwaysShow}, #{metaTitle}, #{metaIcon}, #{metaNoCache}, #{parentId}, #{statusCd}, #{menuType}, #{hidden}, #{sortNumber})")
    void insert(SysResourceInfoEntity sysResourceInfoEntity);

    @Update({
            "UPDATE sys_resource_info SET",
            "menu_name = #{menuName}, path = #{path}, component = #{component}, redirect = #{redirect}, method_cd = #{methodCd}, always_show = #{alwaysShow}, meta_title = #{metaTitle}, meta_icon = #{metaIcon}, meta_no_cache = #{metaNoCache}, parent_id = #{parentId}, status_cd = #{statusCd}, menu_type = #{menuType}, hidden = #{hidden}, sort_number = #{sortNumber}",
            "WHERE menu_id = #{menuId}"
    })
    boolean update(SysResourceInfoEntity sysResourceInfoEntity);
}