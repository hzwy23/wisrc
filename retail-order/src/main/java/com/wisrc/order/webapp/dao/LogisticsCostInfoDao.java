package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.LogisticsCostInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogisticsCostInfoDao {
    @Select("select uuid, batch_number, offer_id, shipment_id, shipment_name, channel_name, logistics_id, freight, create_user, create_time from logistics_cost_info")
    List<LogisticsCostInfoEntity> findAll();

    @Select("select uuid, batch_number, offer_id, shipment_id, shipment_name, channel_name, logistics_id, freight, create_user, create_time from logistics_cost_info where batch_number = #{batchNumber}")
    List<LogisticsCostInfoEntity> findById(@Param("batchNumber") String batchNumber);

    @Insert("insert into logistics_cost_info(uuid, batch_number, offer_id, shipment_id, shipment_name, channel_name, logistics_id, freight, create_user, create_time) values(uuid, #{batchNumber}, #{offerId}, #{shipmentId}, #{shipmentName}, #{channelName}, #{logisticsId}, #{freight}, #{createUser}, #{createTime})")
    void insert(LogisticsCostInfoEntity ele);
}
