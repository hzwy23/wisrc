package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.dao.sql.LogisticsAllowLabelRelSQL;
import com.wisrc.shipment.webapp.entity.LogisticsAllowLabelRelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsAllowLabelRelDao {
    @Insert("insert into logistics_allow_label_rel (uuid, offer_id, label_cd,delete_status) values (#{uuid},#{offerId},#{labelCd},#{deleteStatus})")
    void add(LogisticsAllowLabelRelEntity ele);

    @SelectProvider(type = LogisticsAllowLabelRelSQL.class, method = "findByCond")
    List<LogisticsAllowLabelRelEntity> findByCond(@Param("labelCd") int labelCd, @Param("offerId") String offerId, @Param("deleteStatus") int deleteStatus);

    @Select("select uuid, offer_id, label_cd from logistics_allow_label_rel where offer_id=#{offerId} and delete_status=0")
    List<LogisticsAllowLabelRelEntity> get(@Param("offerId") String offerId, @Param("deleteStatus") int deleStatus);

    @Update("update logistics_allow_label_rel set label_cd=#{labelCd} where offer_id=#{offerId}")
    void update(@Param("ele") LogisticsAllowLabelRelEntity ele);

    @Update("update logistics_allow_label_rel set delete_status=1 where offer_id=#{offerId}")
    void delete(@Param("offerId") String offerId);

    @Delete(("delete from logistics_allow_label_rel where offer_id=#{offerId}"))
    void physicalDeleteLable(String offerId);

    @SelectProvider(type = LogisticsAllowLabelRelSQL.class, method = "getShipmentPrice")
    List<LogisticsAllowLabelRelEntity> getShipmentPrice(String endstr);

    @Select("select offer_id from logistics_allow_label_rel where label_cd=#{labelCd}")
    List<String> getOfferIdsByLabelCd(int labelCd);
}
