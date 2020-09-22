package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.OrderRemarkInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderRemarkInfoDao {

    @Insert("insert into order_remark_info (uuid, order_id, remark, create_user, create_time) values " +
            "(#{uuid},#{orderId},#{remark},#{createUser},#{createTime})")
    void add(OrderRemarkInfoEntity entity);

    @Select("select uuid, order_id, remark, create_time, create_user from order_remark_info where order_Id=#{orderId}")
    List<OrderRemarkInfoEntity> getByOrderId(String orderId);

}
