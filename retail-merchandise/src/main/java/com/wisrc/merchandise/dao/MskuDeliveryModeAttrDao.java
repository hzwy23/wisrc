package com.wisrc.merchandise.dao;

import com.wisrc.merchandise.entity.MskuDeliveryModeAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MskuDeliveryModeAttrDao {
    @Select("SELECT * FROM msku_delivery_mode_attr")
    List<MskuDeliveryModeAttrEntity> getMskuDeliveryModeAttr() throws Exception;


}
