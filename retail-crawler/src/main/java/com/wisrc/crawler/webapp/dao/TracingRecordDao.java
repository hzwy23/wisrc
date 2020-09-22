package com.wisrc.crawler.webapp.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface TracingRecordDao {
    @Insert("insert into shipment_transfer_info (tracking_number, carrier, status, sign_time, delivery_date, expect_delivery_date, on_the_way_day, pickup_date, sign_name, tracking_info) values " +
            "(#{trackingNumber},#{carrier},#{status},#{signTime},#{deliveryDate},#{expectDeliveryDate},#{onTheWayDay},#{pickupDate},#{signName},#{trackingInfo})")
    void insert(Map tracingRecordMap);

    @Delete("delete from shipment_transfer_info where tracking_number=#{trackingNumber}")
    void deleteRecord(@Param("trackingNumber") String trackingNumber);
}
