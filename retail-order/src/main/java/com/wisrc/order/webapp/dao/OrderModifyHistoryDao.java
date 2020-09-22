package com.wisrc.order.webapp.dao;


import com.wisrc.order.webapp.entity.OrderModifyHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderModifyHistoryDao {

    @Insert("insert into order_modify_history(uuid, order_id, handle_time, handle_user, modify_column, old_value, new_value) values(#{uuid},#{orderId}, #{handleTime}, #{handleUser}, #{modifyColumn}, #{oldValue}, #{newValue})")
    void insert(OrderModifyHistory ele);

    @Select("select uuid,order_id, handle_time, handle_user, modify_column, old_value, new_value from order_modify_history where order_id = #{orderId}")
    List<OrderModifyHistory> findByOrderId(@Param("orderId") String orderId);
}
