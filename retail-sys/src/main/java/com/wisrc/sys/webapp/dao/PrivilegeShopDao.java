package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.GatherEntity;
import com.wisrc.sys.webapp.entity.SysPrivilegeShopEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface PrivilegeShopDao {
    @Select("SELECT a.uuid, a.privilege_cd, a.commodity_id , a.create_user, a.create_time, b.user_name FROM sys_privilege_shop a LEFT JOIN sys_user_info b  ON a.create_user = b.user_id WHERE a.privilege_cd = #{authId}")
    List<GatherEntity> getPrivilegeShop(String authId);

    @Select(" SELECT\n" +
            "  a.uuid         AS uuid,\n" +
            "  a.privilege_cd AS privilegeCd,\n" +
            "  a.commodity_id      AS commodityId\n" +
            "FROM sys_privilege_shop a\n" +
            "WHERE a.privilege_cd != #{authId} ")
    List<SysPrivilegeShopEntity> getPrivilegeShopUnAuth(String authId);

    @Insert(" INSERT INTO sys_privilege_shop(uuid, privilege_cd, commodity_id,create_user,create_time) VALUES(#{uuid}, #{privilegeCd}, #{commodityId}, #{createUser}, #{createTime})")
    void insert(SysPrivilegeShopEntity entity);

    @Delete(" DELETE FROM sys_privilege_shop  WHERE commodity_id =  #{commodityId} and  privilege_cd = #{privilegeCd}")
    void deletePrivilegeShop(Map<String, String> map);

    @Update("UPDATE sys_privilege_shop SET create_user = #{createUser}, create_time = #{createTime} WHERE commodity_id =  #{commodityId} and  privilege_cd = #{privilegeCd}")
    void update(SysPrivilegeShopEntity entity);

    @Select("select commodity_id from v_user_responsible_msku where employee_id = #{employeeId}")
    Set<String> getUserChargeMsku(@Param("employeeId") String employeeId);

    @Select("select commodity_id from v_user_responsible_msku")
    Set<String> getAllMsku();
}
