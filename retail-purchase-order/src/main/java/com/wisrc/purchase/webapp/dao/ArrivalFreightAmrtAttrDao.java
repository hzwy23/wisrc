package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.ArrivalFreightAmrtAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArrivalFreightAmrtAttrDao {
    @Select("SELECT freight_apportion_cd, freight_apportion_desc FROM arrival_freight_amrt_attr")
    List<ArrivalFreightAmrtAttrEntity> freightAmrtTypeSelector() throws Exception;
}
