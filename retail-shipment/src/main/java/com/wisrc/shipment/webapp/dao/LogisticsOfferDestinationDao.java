package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.vo.DestinationEnityVo;
import com.wisrc.shipment.webapp.entity.LogisticsOfferDestinationEnity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsOfferDestinationDao {

    @Insert("insert into logistics_destination_rel (uuid, offer_id, destination_cd, delete_status, shipment_id, channel_name) values (#{uuid},#{offerId},#{destinationCd},#{deleteStatus},#{shipmentId},#{channelName})")
    void add(LogisticsOfferDestinationEnity ele);

    @Update("update logistics_destination_rel set delete_status=1 where offer_id=#{offerId}")
    void delete(@Param("offerId") String offerId);

    @Select("select a.uuid, a.offer_id, a.destination_cd ,b.partition_name as destinationName from logistics_destination_rel a left join fba_partition_manage b on a.destination_cd = b.partition_id where offer_id=#{offerId} and delete_status=#{deleteStatus}")
    List<LogisticsOfferDestinationEnity> findByCond(@Param("offerId") String offerId, @Param("deleteStatus") int deleteStatus);

    @Delete(("delete from logistics_destination_rel where offer_id=#{offerId}"))
    void physicalDelete(String offerId);

    @Select("select partition_name from fba_partition_manage where partition_id=#{destinationCd}")
    String findDestNameById(String destinationCd);

    @Select("select country_cd as country_name from fba_partition_manage where partition_id=#{destinationCd}")
    String findCounNameById(String destinationCd);

    @Select("select destination_cd from logistics_destination_rel where offer_id=#{offerId}")
    List<String> getDestIdByOfferId(String offerId);

    @Select("select offer_id, destination_cd, shipment_id from logistics_destination_rel where offer_id=#{offerId}")
    List<DestinationEnityVo> getDestVo(String offerId);

    @Select("select offer_id, destination_cd, shipment_id, destination_name, country_name from v_order_destination_rel where offer_id=#{offerId}")
    List<DestinationEnityVo> getDest(String offerId);
}
