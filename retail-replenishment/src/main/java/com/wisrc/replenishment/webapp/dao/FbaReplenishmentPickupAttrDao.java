package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.FbaReplenishmentPickupAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FbaReplenishmentPickupAttrDao {
    /**
     * 查询补货单提货方式状态码集合
     *
     * @return
     */
    @Select("select pickup_type_cd,pickup_type_name from replenishment_pickup_attr")
    List<FbaReplenishmentPickupAttrEntity> findAllPickupAttr();

    /**
     * 通过补货单提货方式id查询提货方式状态码信息
     *
     * @param pickupTypeCd
     * @return
     */
    @Select("select pickup_type_cd,pickup_type_name from replenishment_pickup_attr where pickup_type_cd=#{pickupTypeCd}")
    FbaReplenishmentPickupAttrEntity findByPickupCd(@Param("pickupTypeCd") int pickupTypeCd);
}
