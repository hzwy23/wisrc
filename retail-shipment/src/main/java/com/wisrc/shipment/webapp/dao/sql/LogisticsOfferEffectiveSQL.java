package com.wisrc.shipment.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class LogisticsOfferEffectiveSQL {
    public static String findByCond(@Param("offerId") final String offerId) {
        return new SQL() {{
            SELECT("uuid, offer_id, start_days, end_days, time_remark, delete_status");
            FROM("logistics_offer_effective");
            WHERE("offer_id=#{offerId}");
        }}.toString();
    }

    public static String getShipmentPrice(@Param("offerIdList") final String offerIdList) {
        return new SQL() {{
            SELECT("uuid, offer_id, effetive_zone, time_remark, delete_status");
            FROM("v_logistics_effective where delete_status=0 AND offer_id in (" + offerIdList + ")");
        }}.toString();
    }
}
