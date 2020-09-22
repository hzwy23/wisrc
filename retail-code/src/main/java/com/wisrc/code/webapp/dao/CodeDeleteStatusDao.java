package com.wisrc.code.webapp.dao;

import com.wisrc.code.webapp.entity.CodeDeleteStatusEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CodeDeleteStatusDao {
    @Select(" select delete_status, delete_status_desc from code_delete_status ")
    List<CodeDeleteStatusEntity> findAll();

    @Update(" update code_delete_status set delete_status_desc = #{deleteStatusDesc} where delete_status = #{deleteStatus} ")
    void update(CodeDeleteStatusEntity entity);

    @Insert(" insert into code_delete_status (delete_status, delete_status_desc)values(#{deleteStatus},#{deleteStatusDesc}) ")
    void insert(CodeDeleteStatusEntity entity);

    @Delete(" delete from code_delete_status where delete_status = #{deleteStatus}  ")
    void delete(Integer deleteStatus);
}
