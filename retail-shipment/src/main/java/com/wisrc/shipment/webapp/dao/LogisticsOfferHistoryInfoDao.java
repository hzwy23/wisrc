package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.dao.sql.LogisticsOfferHistorySQL;
import com.wisrc.shipment.webapp.entity.LogisticsOfferHistoryInfoEntity;
import com.wisrc.shipment.webapp.vo.LogisticHistoryChargeVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsOfferHistoryInfoDao {
    @Insert("insert into logistics_offer_history_info (uuid, offer_id, modify_time, modify_user, start_charge_section, end_charge_section, unit_price,unit_price_with_oil,delete_status) values " +
            "(#{uuid},#{offerId},#{modifyTime},#{modifyUser},#{startChargeSection},#{endChargeSection},#{unitPrice},#{unitPriceWithOil},#{deleteStatus})")
    void add(LogisticsOfferHistoryInfoEntity ele);

    @Select("select uuid, offer_id, modify_time, modify_user, start_charge_section, end_charge_section, unit_price, unit_price_with_oil,delete_status from logistics_offer_history_info where offer_id=#{offerId}")
    List<LogisticsOfferHistoryInfoEntity> findOffer(@Param("offerId") String offerId);

    @Select("select offer_id, modify_time,section,unit_price,unit_price_with_oil from v_logistics_price_history where offer_id=#{offerId}")
    List<LogisticHistoryChargeVO> findHistoryOffer(@Param("offerId") String offerId);

    @Select("select section from v_logistics_price_history where offer_id=#{offerId} group by section")
    List<String> findHeader(@Param("offerId") String offerId);

    @Deprecated
    @Select("<script>" +
            "select A.uuid, A.offer_id,A.unit_price,A.unit_price_with_oil,A.section, max(A.modify_time)" +
            " from (select * from v_logistics_price_history " +
            " where 1=1" +
            " <if test='offerIds!=null'>" +
            "   <foreach item='offerId' index='index' collection='offerIds' open='AND offer_id IN (' separator=',' close=')'>" +
            "       #{offerId}" +
            "   </foreach>" +
            "  </if>" +
            " and delete_status=0 order by modify_time desc) A group by A.section" +
            "</script>")
    List<LogisticHistoryChargeVO> batchPrice(@Param("offerIds") String[] offerIds);

    @Select("SELECT uuid,offer_id,modify_time,modify_user, start_charge_section,end_charge_section, unit_price,unit_price_with_oil,MAX(modify_time) FROM logistics_offer_history_info where offer_id=#{offerId} and delete_status=0 group by start_charge_section,end_charge_section")
    List<LogisticsOfferHistoryInfoEntity> get(@Param("offerId") String offerId, @Param("deleteStatus") int deleteStatus);

    @Update("update logistics_offer_history_info set delete_status=1 where uuid=#{uuid}")
    void deletePrice(String uuid);


    @Update("update logistics_offer_history_info set delete_status=1,modify_time=#{modifyTime},modify_user=#{userId} where offer_id=#{offerId}")
    void delete(@Param("offerId") String offerId,
                @Param("userId") String userId,
                @Param("modifyTime") String modifyTime);

    @Update("update logistics_offer_history_info set delete_status=1 where offer_id=#{offerId}")
    void deleteByOfferId(String offerId);

    @Delete(("update logistics_offer_history_info set delete_status =1 where offer_id=#{offerId}"))
    void physicalDelete(String offerId);

    @SelectProvider(type = LogisticsOfferHistorySQL.class, method = "getShipmentPrice")
    List<LogisticsOfferHistoryInfoEntity> getShipmentPrice(String endstr);

    @SelectProvider(type = LogisticsOfferHistorySQL.class, method = "batchPriceV2")
    List<LogisticHistoryChargeVO> batchPriceV2(String offerIdsStr);
}
