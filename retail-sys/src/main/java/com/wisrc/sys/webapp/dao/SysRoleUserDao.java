package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.dao.sql.SysRoleUserSQL;
import com.wisrc.sys.webapp.entity.SysRoleUserEntity;
import com.wisrc.sys.webapp.vo.SysRoleUserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysRoleUserDao {
    @Select("SELECT uuid,role_id,user_id,create_time,create_user FROM sys_role_user")
    List<SysRoleUserEntity> getSysRoleUser();

    @Insert("INSERT INTO sys_role_user(uuid, role_id, user_id, create_time, create_user) VALUES(#{uuid}, #{roleId}, #{userId}, #{createTime}, #{createUser})")
    void saveSysRoleUser(SysRoleUserEntity sysRoleUserEntity) throws Exception;

    @Update("UPDATE sys_role_user SET role_id = #{roleId}, user_id = #{userId} WHERE uuid = #{uuid}")
    void editSysRoleUser(SysRoleUserEntity sysRoleUserEntity) throws Exception;

    @Select("SELECT uuid,role_id,user_id,create_time,create_user FROM sys_role_user WHERE user_id = #{userId}")
    List<SysRoleUserEntity> getSysRoleUserByUserId(String userId);

    @Select("SELECT role_id FROM sys_role_user WHERE user_id = #{userId}")
    List<String> getRoleIdByUserId(String userId) throws Exception;

    @Select("select count(*) from sys_role_user where user_id = #{userId} and role_id = #{roleId}")
    int checkAuth(@Param("userId") String userId, @Param("roleId") String roleId);

    @Delete("DELETE FROM sys_role_user WHERE user_id = #{userId}")
    void deleteSysRoleUserByUserId(String userId) throws Exception;

    @Delete("<script> "
            + "DELETE FROM sys_role_user WHERE user_id = #{userId} AND role_id IN "
            + "<foreach item='roleId' index='index' collection='roleIds' open='(' separator=',' close=')'>"
            + "#{roleId}"
            + "</foreach>"
            + "</script>"
    )
    void deleteSysRoleUserByRoleIds(@Param("userId") String userId, @Param("roleIds") List roleIds) throws Exception;

    @Select("<script> "
            + "SELECT uuid,role_id,user_id,create_time,create_user FROM sys_role_user WHERE user_id IN "
            + "<foreach item='userId' index='index' collection='userIds' open='(' separator=',' close=')'>"
            + "#{userId}"
            + "</foreach>"
            + "</script>"
    )
    List<SysRoleUserEntity> getSysRoleUserBatchByUserId(@Param("userIds") List userIds) throws Exception;


    @SelectProvider(type = SysRoleUserSQL.class, method = "search")
    List<SysRoleUserVO> findAllByIdList(@Param("userIds") String userIds);
}
