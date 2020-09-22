package com.wisrc.code.webapp.dao;

import com.wisrc.code.webapp.entity.CodeCommonStatusAttrEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CodeCommonStatusAttrDao {
    @Select(" SELECT\n" +
            "  a.status_cd,\n" +
            "  a.status_desc\n" +
            " FROM code_common_status_attr a  ")
    List<CodeCommonStatusAttrEntity> findAll();

    @Update(" UPDATE code_common_status_attr\n" +
            " SET\n" +
            "   status_desc = #{statusDesc}\n" +
            " WHERE status_cd = #{statusCd} ")
    void update(CodeCommonStatusAttrEntity entity);

    @Insert(" insert into code_common_status_attr (status_cd,status_desc)values(#{statusCd},#{statusDesc}) ")
    void insert(CodeCommonStatusAttrEntity entity);

    @Delete(" delete from code_common_status_attr where  status_cd = #{statusCd} ")
    void delete(Integer statusCd);
}
