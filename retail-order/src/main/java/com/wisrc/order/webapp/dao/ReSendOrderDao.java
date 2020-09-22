package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.ReSendOrderEnity;
import com.wisrc.order.webapp.entity.ReSendOrderProductDetaiEnity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReSendOrderDao {


    @Insert("insert into order_redelivery_info (redelivery_id,order_id,redelivery_type_cd,offer_id,logistics_id,create_user,create_time, modify_time, modify_user, logistics_cost) values " +
            "(#{redeliveryId},#{orderId},#{redeliveryTypeCd},#{offerId},#{logisticsId},#{createUser},#{createTime}, #{modifyTime}, #{modifyUser}, #{logisticsCost})")
    void insertOrder(ReSendOrderEnity reEnity);

    @Insert("insert into order_redelivery_product_info (uuid, redelivery_id, commodity_id, redelivery_quantity) values (#{uuid}, #{redeliveryId}, #{commodityId}, #{redeliveryQuantity})")
    void insertPrdocutDetail(ReSendOrderProductDetaiEnity reSendOrderProductDetaiEnity);
}
