package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysPrivilegeShopEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysPrivilegeShopDao {
    @Select("SELECT * FROM sys_privilege_shop")
    List<SysPrivilegeShopEntity> getSysPrivilegeShop() throws Exception;

    @Insert("INSERT INTO sys_privilege_shop(uuid, privilege_cd, shop_cd) VALUES(#{uuid}, #{privilegeCd}, #{shopId})")
    void saveSysPrivilegeShop(SysPrivilegeShopEntity sysPrivilegeShopEntity) throws Exception;

    @Update("UPDATE sys_privilege_shop SET privilege_cd = #{privilegeCd}, shop_cd = #{shopId} WHERE uuid = #{uuid}")
    void editSysPrivilegeShop(SysPrivilegeShopEntity sysPrivilegeShopEntity) throws Exception;
}
