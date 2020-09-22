package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.dao.sql.SysUerPrivilegeSQL;
import com.wisrc.sys.webapp.entity.SysPrivilegesInfoEntity;
import com.wisrc.sys.webapp.entity.SysRolePrivilegeEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysRolePrivilegeDao {
    @Select("SELECT * FROM sys_user_privilege")
    List<SysRolePrivilegeEntity> getSysUserPrivilege() throws Exception;

    @Insert("INSERT INTO sys_user_privilege(uuid, privilege_cd, role_id) VALUES(#{uuid}, #{privilegeCd}, #{roleId})")
    void saveSysUserPrivilege(SysRolePrivilegeEntity sysRolePrivilegeEntity) throws Exception;

    @Update("UPDATE sys_user_privilege SET privilege_cd = #{privilegeCd}, role_id = #{roleId} WHERE uuid = #{uuid}")
    void editSysUserPrivilege(SysRolePrivilegeEntity sysRolePrivilegeEntity) throws Exception;

    @Select("SELECT privilege_cd FROM sys_user_privilege WHERE role_id = #{roleId}")
    List<String> getPrivilegeIdsByUserId(@Param("roleId") String roleId) throws Exception;

    @Delete("<script> "
            + "DELETE FROM sys_user_privilege WHERE role_id = #{roleId} AND privilege_cd IN "
            + "<foreach item='privilegeCd' index='index' collection='privilegeCds' open='(' separator=',' close=')'>"
            + "#{privilegeCd}"
            + "</foreach>"
            + "</script>"
    )
    void deleteSysRoleMenusByMenuIds(@Param("roleId") String roleId, @Param("privilegeCds") List privilegeCds) throws Exception;

    @SelectProvider(type = SysUerPrivilegeSQL.class, method = "getSysUserPrivilege")
    List<SysPrivilegesInfoEntity> getSysUserPrivilegeByUserId(@Param("roleId") String roleId) throws Exception;
}
