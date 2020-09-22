package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.dao.sql.LogisticsOfferEffectiveSQL;
import com.wisrc.shipment.webapp.entity.LogisticsOfferEffectiveEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsOfferEffectiveDao {
    @Insert("insert into logistics_offer_effective (uuid, offer_id, start_days, end_days, time_remark, delete_status) values " +
            " (#{uuid},#{offerId},#{startDays},#{endDays},#{timeRemark},#{deleteStatus})")
    void add(LogisticsOfferEffectiveEntity ele);

    @SelectProvider(type = LogisticsOfferEffectiveSQL.class, method = "findByCond")
    List<LogisticsOfferEffectiveEntity> findByCond(@Param("offerId") String offerId);

    @Select("select uuid, offer_id, start_days, end_days, time_remark, delete_status from logistics_offer_effective where offer_id=#{offerId}")
    List<LogisticsOfferEffectiveEntity> get(@Param("offerId") String offerId, @Param("deleStatus") int deleStatus);

    @Update("update logistics_offer_effective set delete_status=1 where offer_id=#{offerId}")
    void delete(@Param("offerId") String offerId);

    @Delete(("delete from logistics_offer_effective where offer_id=#{offerId}"))
    void phisycalDeleteByOffId(String offerId);

    @SelectProvider(type = LogisticsOfferEffectiveSQL.class, method = "getShipmentPrice")
    List<LogisticsOfferEffectiveEntity> getShipmentPrice(String endstr);
}
