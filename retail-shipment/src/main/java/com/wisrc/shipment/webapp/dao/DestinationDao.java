package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.entity.FbaDestinationEnity;
import com.wisrc.shipment.webapp.entity.LittlePacketDestEnity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DestinationDao {
    @Select("select partition_id as destination_cd, partition_name as destination_name from fba_partition_manage where countryCd = #{countryCd}")
    List<FbaDestinationEnity> getAllFbaDest(@Param("countryCd") String countryCd);

    @Select("select partition_id as destination_cd, country_cd as country_name from fba_partition_manage")
    List<LittlePacketDestEnity> getAllLittleDest();
}
