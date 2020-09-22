package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysRoleInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysRoleInfoDao {
    // 获取页面展示角色信息
    @Select("SELECT * FROM sys_role_info order by create_time desc")
    List<SysRoleInfoEntity> getSysRoleInfo() throws Exception;

    // 获取页面展示角色信息
    @Select("<script> "
            + "SELECT role_id, role_name, create_time, create_user, status_cd FROM sys_role_info WHERE 1=1 "
            + "<if test = 'roleId!=null'>"
            + " AND role_id LIKE concat('%', #{roleId}, '%')  "
            + "</if>"
            + "<if test = 'roleIds!=null'>"
            + "<foreach item='roleIdIn' index='index' collection='roleIds' open=' AND role_id IN (' separator=',' close=')'>"
            + "#{roleIdIn}"
            + "</foreach>"
            + "</if>"
            + "<if test = 'roleName!=null'>"
            + " AND role_name LIKE concat('%', #{roleName}, '%')  "
            + "</if>"
            + "<if test = 'statusCd!=null'>"
            + " AND status_cd = #{statusCd}  "
            + "</if>"
            + "order by create_time desc"
            + "</script>"
    )
    List<SysRoleInfoEntity> getSysRoleInfoPage(@Param("roleId") String roleId, @Param("roleName") String roleName, @Param("statusCd") Integer statusCd,
                                               @Param("roleIds") List roleIds
    ) throws Exception;

    // 根据id获取角色内容
    @Select("SELECT * FROM sys_role_info WHERE role_id = #{roleId}")
    SysRoleInfoEntity getSysRoleInfoById(@Param("roleId") String roleId) throws Exception;

    // 添加角色
    @Insert("INSERT INTO sys_role_info(role_id, role_name, create_time, create_user, status_cd) VALUES(#{roleId}, #{roleName}, #{createTime}, #{createUser}, #{statusCd})")
    void saveSysRoleInfo(SysRoleInfoEntity sysRoleInfoEntity) throws Exception;

    // 编辑角色
    @Update("UPDATE sys_role_info SET role_name = #{roleName}, status_cd = #{statusCd} WHERE role_id = #{roleId}")
    void editSysRoleInfo(SysRoleInfoEntity sysRoleInfoEntity) throws Exception;

    // 删除角色
    @Update("UPDATE sys_role_info SET status_cd = 3 WHERE role_id = #{roleId}")
    void deleteSysRoleInfo(String roleId) throws Exception;

    // 获取新角色Id
    @Select("SELECT MAX(RIGHT(role_id, 4)) as id FROM sys_role_info")
    String getMaxRoleId() throws Exception;

    // 批量禁用角色
    @Update("<script> "
            + "UPDATE sys_role_info SET status_cd = 2 WHERE role_id IN "
            + "<foreach item='roleId' index='index' collection='roleIds' open=' (' separator=',' close=')'>"
            + "#{roleId}"
            + "</foreach>"
            + "</script>"
    )
    void banSysRoleInfoBatch(@Param("roleIds") List roleIds) throws Exception;

    // 启用禁用角色
    @Update("UPDATE sys_role_info SET status_cd = #{statusCd} WHERE role_id = #{roleId}")
    void sysRoleInfoSwitch(@Param("roleId") String roleId, @Param("statusCd") Integer statusCd) throws Exception;
}
