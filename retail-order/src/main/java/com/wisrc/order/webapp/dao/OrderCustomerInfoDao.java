package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.OrderCustomerInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderCustomerInfoDao {
    @Select("select order_id, customer_id, consignee, zip_code,  contact_one, contact_two,  mailbox,  country_cd,  province_name, city_name,  details_addr, buyer_message from  order_customer_info")
    List<OrderCustomerInfoEntity> findAll();

    @Select("select order_id, customer_id, consignee, zip_code,  contact_one, contact_two,  mailbox,  country_cd,  province_name, city_name,  details_addr, buyer_message from  order_customer_info where order_id = #{orderId}")
    OrderCustomerInfoEntity findAllById(@Param("orderId") String orderId);

    @Update("update order_customer_info set order_id = #{orderId}, customer_id = #{customerId}, consignee = #{consignee}, zip_code = #{zipCode}, contact_one = #{contactOne}, contact_two = #{contactTwo}, mailbox = #{mailbox}, country_cd = #{countryCd}, province_name = #{provinceName}, city_name = #{cityName},  details_addr = #{detailsAddr},  buyer_message = #{buyerMessage} where order_id = #{orderId}")
    void update(OrderCustomerInfoEntity ele);

    @Insert("insert into order_customer_info(order_id, customer_id, consignee, zip_code, contact_one, contact_two, mailbox, country_cd, province_name, city_name, details_addr, buyer_message) values(#{orderId}, #{customerId}, #{consignee}, #{zipCode}, #{contactOne}, #{contactTwo}, #{mailbox}, #{countryCd}, #{provinceName}, #{cityName}, #{detailsAddr}, #{buyerMessage})")
    void insert(OrderCustomerInfoEntity ele);
}
