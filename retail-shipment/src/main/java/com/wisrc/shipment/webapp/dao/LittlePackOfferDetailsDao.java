package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.dao.sql.LittlePackOfferDetailsSQL;
import com.wisrc.shipment.webapp.entity.LittlePackOfferDetailsEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface LittlePackOfferDetailsDao {

    @SelectProvider(type = LittlePackOfferDetailsSQL.class, method = "findByCond")
    List<LittlePackOfferDetailsEntity> findByCond(@Param("offerId") String offerId, @Param("deleteStatus") int deleteStatus);

    @Insert("insert into little_package_offer_details (uuid, offer_id, start_charge_section, end_charge_section, first_weight, first_weight_price, peer_weight, peer_weight_price, delete_status) " +
            "values (#{uuid},#{offerId},#{startChargeSection},#{endChargeSection},#{firstWeight},#{firstWeightPrice},#{peerWeight},#{peerWeightPrice},#{deleteStatus})")
    void saveLittlePack(LittlePackOfferDetailsEntity littlePack);

    @Update("update little_package_offer_details set delete_status=1 where offer_id=#{offerId}")
    void deleteLittlePack(@Param("offerId") String offerId);

    @Delete(("delete from little_package_offer_details where offer_id=#{offerId}"))
    void physicalDelete(String offerId);
}
