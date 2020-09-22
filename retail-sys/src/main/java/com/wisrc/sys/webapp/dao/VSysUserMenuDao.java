package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.dao.sql.VSysUserMenusSQL;
import com.wisrc.sys.webapp.entity.VSysMenuUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface VSysUserMenuDao {

    @SelectProvider(type = VSysUserMenusSQL.class, method = "auth")
    List<VSysMenuUserEntity> getAuth(@Param("userId") String userId,
                                     @Param("menuId") String menuId,
                                     @Param("methodCd") Integer methodCd,
                                     @Param("path") String path,
                                     @Param("menuType") Integer menuType);
}
