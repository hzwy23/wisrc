package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.OutWarehouseRemarkEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OutWarehouseRemarkDao {
    @Insert("insert into out_warehouse_remark (uuid, out_bill_id, remark, create_user, create_time) values " +
            "(#{uuid},#{outBillId},#{remark},#{createUser},#{createTime})")
    void add(OutWarehouseRemarkEntity entity);

    @Select("select uuid, out_bill_id, remark, create_user, create_time from out_warehouse_remark where out_bill_id=#{outBillId}")
    List<OutWarehouseRemarkEntity> getRemarkList(@Param("outBillId") String outBillId);
}
