package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.entity.LogisticsChargeTypeAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogisticsChargeTypeAttrDao {

    @Select("select charge_type_cd, channel_type_cd, charge_type_desc  from logistics_charge_type_attr where channel_type_cd=#{channelTypeCd};")
    List<LogisticsChargeTypeAttrEntity> findList(@Param("channelTypeCd") int channelTypeCd);

    @Select("select charge_type_desc  from logistics_charge_type_attr where charge_type_cd=#{chargeTypeCd};")
    String findChargeNameById(@Param("chargeTypeCd") int chargeTypeCd);
}
