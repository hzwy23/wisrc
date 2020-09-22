package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.EnterWarehouseRemarkEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnterWarehouseRemarkDao {

    @Insert("insert into enter_warehouse_remark (uuid, enter_bill_id, remark, create_user, create_time) values " +
            "(#{uuid},#{enterBillId},#{remark},#{createUser},#{createTime})")
    void add(EnterWarehouseRemarkEntity remarkEntity);

    @Select("select uuid, enter_bill_id, remark, create_user,date_format(create_time,'%Y-%m-%d %H:%m:%s') AS create_time from enter_warehouse_remark where enter_bill_id=#{enterBillId} ")
    List<EnterWarehouseRemarkEntity> getRemarkList(@Param("enterBillId") String enterBillId);
}
