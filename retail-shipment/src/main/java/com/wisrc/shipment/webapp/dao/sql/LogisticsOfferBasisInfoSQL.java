package com.wisrc.shipment.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class LogisticsOfferBasisInfoSQL {
    public static String findByCond(@Param("channelTypeCd") final Integer channelTypeCd,
                                    @Param("deleteStatus") final int deleteStatus,
                                    @Param("keyword") final String keyword,
                                    @Param("offerTypeCd") int offerTypeCd,
                                    @Param("labelCd") String labelCd,
                                    @Param("shipmentIds") String shipmentIds) {
        String sql = new SQL() {
            {
                SELECT("distinct offer_id, shipment_id, offer_type_cd, channel_name, channel_type_cd, charge_type_cd, remark, logistics_track_url, modify_time, modify_user,delete_status,customs_declaration_fee,discount,port_fee,register_fee");
                FROM("offer_basis_label_rel ");
                if (deleteStatus != 2) {
                    WHERE("delete_status=#{deleteStatus}");
                }
                if (channelTypeCd != 0) {
                    WHERE("channel_type_cd=#{ channelTypeCd}");
                }
                if (offerTypeCd != 0) {
                    WHERE("offer_type_cd=#{ offerTypeCd}");
                }
                if (labelCd != null) {
                    WHERE("label_cd in (" + labelCd + ") ");
                }

                if (keyword != null) {
                    if (shipmentIds != null) {
                        WHERE("( channel_name like concat('%',#{keyword},'%') " +
                                " or remark like concat('%',#{keyword},'%') or shipment_id in (" + shipmentIds + ") )");
                    } else {
                        WHERE("( channel_name like concat('%',#{keyword},'%') " +
                                " or remark like concat('%',#{keyword},'%') )");
                    }
                }
                ORDER_BY(" modify_time desc ");
            }
        }.toString();
        return sql;
    }


    public static String getShipmentPrice(@Param("offerIdList") String offerIdList) {
        return new SQL() {{
            SELECT("offer_id, shipment_id, channel_name, channel_type_cd, charge_type_cd, remark, logistics_track_url, modify_time, modify_user, delete_status, customs_declaration_fee, port_fee, offer_type_cd, register_fee, discount");
            FROM("logistics_offer_basis_info where  offer_id in (" + offerIdList + ")");

        }}.toString();
    }

    public static String getShipMentIDs(@Param("offerIdList") String ids) {
        return new SQL() {{
            SELECT("offer_id, shipment_id");
            FROM("logistics_offer_basis_info where  offer_id in (" + ids + ")");

        }}.toString();
    }

    public static String getAllOffers(@Param("offerTypeCd") Integer offerTypeCd) {
        return new SQL() {{
            SELECT("offer_id, shipment_id,channel_name");
            FROM("logistics_offer_basis_info");
            if (offerTypeCd != 0) {
                WHERE("offer_type_cd = #{offerTypeCd} and delete_status=0");
            }

        }}.toString();
    }


}
