package com.wisrc.code.webapp.dao;

import com.wisrc.code.webapp.entity.PropertiesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PropertiesDao {
    @Select("<script>"
            + "SELECT name, value FROM properties WHERE"
            + "<foreach item='key' index='index' collection='keys' open=' name IN (' separator=',' close=')'>"
            + "#{key}"
            + "</foreach>"
            + "</script>"
    )
    List<PropertiesEntity> getKey(@Param("keys") List keys);
}
