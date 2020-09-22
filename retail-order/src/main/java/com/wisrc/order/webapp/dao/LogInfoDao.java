package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.UpdateDetailEnity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogInfoDao {
    @Insert("insert into order_update_log_info (order_id, update_time, update_user, update_detail) values (#{orderId},#{updateTime},#{updateUser},#{updateDetail})")
    void insert(UpdateDetailEnity updateDetailEnity);

    @Select("select order_id, update_time, update_user, update_detail from order_update_log_info order by update_time desc")
    List<UpdateDetailEnity> getByOrderId(String orderId);
}
