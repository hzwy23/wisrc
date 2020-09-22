package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.entity.LogisticsChannelAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogisticsChannelAttrDao {
    @Select("select channel_type_cd, channel_type_desc from logistics_channel_attr")
    List<LogisticsChannelAttrEntity> findList();
}
