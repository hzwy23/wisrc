package com.wisrc.sys.webapp.dao;


import com.wisrc.sys.webapp.entity.SysUserWorktileEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserWorktileDao {
    @Select("select worktile_id,username,access_token from sys_user_worktile where worktile_id = #{worktileId}")
    SysUserWorktileEntity findById(String worktileId);

    @Insert("insert into sys_user_worktile(worktile_id, username, access_token) values(#{worktileId}, #{username}, #{accessToken})")
    void insert(SysUserWorktileEntity ele);

    @Delete("delete from sys_user_worktile where worktile_id = #{worktileId}")
    void delete(String worktileId);
}
