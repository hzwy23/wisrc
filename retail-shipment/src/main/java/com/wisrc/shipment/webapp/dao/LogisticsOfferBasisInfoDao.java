package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.dao.sql.LogisticsOfferBasisInfoSQL;
import com.wisrc.shipment.webapp.entity.*;
import com.wisrc.shipment.webapp.vo.LogisticsOfferVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogisticsOfferBasisInfoDao {
    @Insert("insert into logistics_offer_basis_info (offer_id, shipment_id, channel_name, channel_type_cd, charge_type_cd, remark, logistics_track_url, modify_time, modify_user,delete_status, customs_declaration_fee, port_fee ,offer_type_cd, register_fee ,discount) " +
            "values (#{offerId},#{shipmentId},#{channelName},#{channelTypeCd},#{chargeTypeCd},#{remark},#{logisticsTrackUrl},#{modifyTime},#{modifyUser},#{deleteStatus},#{customsDeclarationFee},#{portFee},#{offerTypeCd},#{register},#{discount})")
    void add(LogisticsOfferBasisInfoEntity ele);

    @SelectProvider(type = LogisticsOfferBasisInfoSQL.class, method = "findByCond")
    List<LogisticsOfferBasisInfoEntity> findByCond(@Param("channelTypeCd") int channelTypeCd,
                                                   @Param("deleteStatus") int deleteStatus,
                                                   @Param("keyword") String keyword,
                                                   @Param("offerTypeCd") int offerTypeCd,
                                                   @Param("labelCd") String labelCd,
                                                   @Param("shipmentIds") String shipmentIds);

    @Select("select offer_id, shipment_id, channel_name, channel_type_cd, charge_type_cd, remark, logistics_track_url, modify_time, modify_user,delete_status,customs_declaration_fee,port_fee,offer_type_cd,register_fee as register,discount from logistics_offer_basis_info " +
            "where offer_id=#{offerId}")
    LogisticsOfferBasisInfoEntity get(@Param("offerId") String offerId);

    @Update("update logistics_offer_basis_info set delete_status=1,modify_time=#{modifyTime},modify_user=#{userId} where offer_id=#{offerId}")
    void delete(@Param("offerId") String offerId,
                @Param("userId") String userId,
                @Param("modifyTime") String modifyTime);

    @Select("select offer_id, shipment_id, channel_name, channel_type_cd, charge_type_cd, remark, logistics_track_url, modify_time, modify_user,delete_status from logistics_offer_basis_info " +
            "where channel_name=#{channelName} and delete_status=0")
    List<LogisticsOfferBasisInfoEntity> findByChannelName(@Param("channelName") String channelName);

    @Select("select offer_id from logistics_offer_basis_info " +
            "where shipment_id=#{shipmentId}")
    List<String> findByshipmentId(@Param("shipmentId") String shipmentId);

    @Update("update logistics_offer_basis_info set delete_status=1 where offer_id=#{offerId}")
    void deleteByOfferId(String offerId);

    @Insert("insert into logistics_offer_basis_info (offer_id, shipment_id, channel_name, channel_type_cd, charge_type_cd, remark, logistics_track_url, modify_time, modify_user, delete_status, customs_declaration_fee, port_fee, offer_type_cd) " +
            "values (#{offerId},#{shipmentId},#{channelName},#{channelTypeCd},#{chargeTypeCd},#{remark},#{logisticsTrackUrl},#{modifyTime},#{modifyUser},#{deleteStatus},#{customsDeclarationFee},#{portFee},#{offerTypeCd})")
    void addFba(LogicOfferBasisInfoFbaEnity ele);

    @Insert("insert into logistics_offer_basis_info (offer_id, shipment_id, channel_name, channel_type_cd, charge_type_cd, remark, modify_time, modify_user, delete_status, customs_declaration_fee, register_fee, discount, offer_type_cd) " +
            "values (#{offerId},#{shipmentId},#{channelName},#{channelTypeCd},#{chargeTypeCd},#{remark},#{modifyTime},#{modifyUser},#{deleteStatus},#{customsDeclarationFee},#{register},#{discount},#{offerTypeCd})")
    void addLittle(LogicOfferBasisInfoLittilEnity ele);

    @Update("update logistics_offer_basis_info set shipment_id=#{shipmentId},channel_name=#{channelName},channel_type_cd=#{channelTypeCd},charge_type_cd=#{chargeTypeCd},remark=#{remark}, " +
            "logistics_track_url=#{logisticsTrackUrl},customs_declaration_fee=#{customsDeclarationFee},port_fee=#{portFee},modify_time=#{modifyTime},modify_user=#{modifyUser} where offer_id=#{offerId}")
    void updateFba(LogicOfferBasisInfoFbaEnity ele);

    @Update("update logistics_offer_basis_info set shipment_id=#{shipmentId},channel_name=#{channelName},channel_type_cd=#{channelTypeCd},charge_type_cd=#{chargeTypeCd},remark=#{remark}, " +
            "customs_declaration_fee=#{customsDeclarationFee},register_fee=#{register},discount=#{discount},modify_time=#{modifyTime},modify_user=#{modifyUser} where offer_id=#{offerId}")
    void updatelittle(LogicOfferBasisInfoLittilEnity ele);

    @SelectProvider(type = LogisticsOfferBasisInfoSQL.class, method = "getShipmentPrice")
    List<LogisticsOfferBasisInfoEntity> getShipmentPrice(@Param("offerIdList") String offerIdList);

    @Select("select offer_id,channel_name from logistics_offer_basis_info " +
            "where shipment_id=#{shipMentId} and offer_type_cd=#{offerTypeCd} and delete_status=0")
    List<LogisticsOfferVo> findBOfferByshipMentId(@Param(value = "shipMentId") String shipMentId, @Param(value = "offerTypeCd") int offerTypeCd);

    @SelectProvider(type = LogisticsOfferBasisInfoSQL.class, method = "getShipMentIDs")
    List<Map<String, String>> getShipMentIDs(String ids);

    @Select("select offer_id from logistics_offer_basis_info where shipment_id=#{shipMentId} and channel_name=#{channelName} and delete_status=0 and offer_type_cd=#{offerTypeCd}")
    List<String> getByNameAndShipId(@Param("channelName") String channelName, @Param("shipMentId") String shipMentId, @Param("offerTypeCd") int offerTypeCd);

    @SelectProvider(type = LogisticsOfferBasisInfoSQL.class, method = "getAllOffers")
    List<SimpleOfferBasisInfoEnity> getAllOffers(int offerTypeCd);

    @Select("select delete_status from logistics_offer_basis_info where offer_id=#{offerId}")
    Integer getStatusByOfferId(String offerId);

    @Select("select offer_id, logistics_track_url from logistics_offer_basis_info where delete_status=0")
    List<CarrierEnity> getAllCarriers(int offerTypeCd, int i);

    @Select("select offer_id, channel_name from logistics_offer_basis_info where delete_status=0")
    List<ChannelNameEnity> getAllChannleName();
}
