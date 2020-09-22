package com.wisrc.order.webapp.dao;


import com.wisrc.order.webapp.dao.sql.SaleLogisticsInfoSql;
import com.wisrc.order.webapp.entity.CountSaleLogisticsEntity;
import com.wisrc.order.webapp.entity.OrderLogisticsInfo;
import com.wisrc.order.webapp.query.GetCountByLogisticsIdQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderLogisticsInfoDao {
    @Select("select order_id,   delivery_channel_cd, offer_id, logistics_id, logistics_cost, weight, delivery_date, delivery_remark from order_logistics_info")
    List<OrderLogisticsInfo> findAll();

    @Select("select order_id, delivery_channel_cd, offer_id, logistics_id, logistics_cost, weight, delivery_date, delivery_remark, shipment_id from order_logistics_info where order_id=#{orderId}")
    OrderLogisticsInfo findAllById(@Param("orderId") String orderId);

    @Update("update order_logistics_info set delivery_channel_cd = #{deliveryChannelCd}, offer_id = #{offerId}, logistics_id = #{logisticsId}, logistics_cost = #{logisticsCost}, weight = #{weight}, delivery_date = #{deliveryDate}, delivery_remark = #{deliveryRemark} where order_id = #{orderId}\n")
    void update(OrderLogisticsInfo ele);

    @Insert("insert into  order_logistics_info(order_id, delivery_channel_cd, offer_id, logistics_id, logistics_cost, weight, delivery_date, delivery_remark, shipment_id) values(#{orderId}, #{deliveryChannelCd}, #{offerId}, #{logisticsId}, #{logisticsCost}, #{weight}, #{deliveryDate}, #{deliveryRemark},#{shipMentId})")
    void insert(OrderLogisticsInfo ele);

    @Select("select order_id, delivery_channel_cd, offer_id, logistics_id, logistics_cost, weight, delivery_date, delivery_remark from order_logistics_info " +
            "where order_id=#{orderId}")
    OrderLogisticsInfo get(@Param("orderId") String orderId);

    @SelectProvider(type = SaleLogisticsInfoSql.class, method = "getCountByLogisticsId")
    List<CountSaleLogisticsEntity> getCountByLogisticsId(GetCountByLogisticsIdQuery hetCountByLogisticsIdQuery) throws Exception;

    @SelectProvider(type = SaleLogisticsInfoSql.class, method = "getDistinctOffer")
    List<CountSaleLogisticsEntity> getDistinctOffer(GetCountByLogisticsIdQuery hetCountByLogisticsIdQuery) throws Exception;

    @SelectProvider(type = SaleLogisticsInfoSql.class, method = "editSaleLogistics")
    void editSaleLogistics(OrderLogisticsInfo saleLogisticsInfoEntity) throws Exception;
}
