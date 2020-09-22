package com.wisrc.order.webapp.dao;


import com.wisrc.order.webapp.entity.OrderLabelInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderLabelInfoDao {
    @Select("select uuid, order_id, except_type_cd from order_label_info")
    List<OrderLabelInfo> findAll();

    @Insert("insert into order_label_info(uuid, order_id, except_type_cd) values(#{uuid},#{orderId},#{exceptTypeCd})")
    void insert(OrderLabelInfo ele);

    @Delete("delete from order_label_info where uuid = #{uuid}")
    void delete(@Param("uuid") String uuid);

    @Select("select uuid, order_id, except_type_cd from order_label_info where order_id=#{orderId}")
    List<OrderLabelInfo> getById(String orderId);
}
