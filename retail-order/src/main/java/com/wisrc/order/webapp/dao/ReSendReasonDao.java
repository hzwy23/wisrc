package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.ReSendReasonEnity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReSendReasonDao {
    @Insert("insert into order_redelivery_type_attr (redelivery_type_name) values (#{redeliveryTypeName})")
    void add(ReSendReasonEnity reasonEnity);

    @Select("select redelivery_type_cd,redelivery_type_name from order_redelivery_type_attr")
    List<ReSendReasonEnity> getAll();

    @Delete("delete from order_redelivery_type_attr where redelivery_type_cd=#{reasonId}")
    void deleteById(String reasonId);
}
