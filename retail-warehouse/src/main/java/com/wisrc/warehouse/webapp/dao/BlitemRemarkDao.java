package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.BlitemRemarkEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlitemRemarkDao {

    @Insert("insert into blitem_remark(uuid, blitem_id, remark_content, remark_time, remark_user) " +
            "values (#{uuid},#{blitemId},#{remarkContent},#{remarkTime},#{remarkUser})")
    void add(BlitemRemarkEntity remarkEntity);

    @Select("select uuid, blitem_id, remark_content, remark_time, remark_user\n" +
            "from blitem_remark\n" +
            "where blitem_id = #{blitemId} \n" +
            "  order by remark_time desc")
    List<BlitemRemarkEntity> findAllByBlitemId(@Param("blitemId") String blitemId);
}
