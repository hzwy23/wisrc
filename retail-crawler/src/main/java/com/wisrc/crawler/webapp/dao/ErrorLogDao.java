package com.wisrc.crawler.webapp.dao;

import com.wisrc.crawler.webapp.entity.ErrorLogEnity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ErrorLogDao {

    @Select("select error_id, error_flag from error_log where error_flag=1")
    List<ErrorLogEnity> getLogEnity();

    @Update("update error_log set error_flag=#{flag} where error_id=#{errorId}")
    void updateLog(@Param("flag") int flag, @Param("errorId") int errorId);

    @Insert("insert into error_log values (8,0)")
    void insert(int i, int i1);
}
