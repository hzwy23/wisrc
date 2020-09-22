package com.wisrc.shipment.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class LogisticsOfferDestionSQL {
    public static String findByCond(@Param("offerId") final String offerId) {
        return new SQL() {{
            SELECT("uuid, offer_id, destination_cd,delete_status");
            FROM("logistics_destination_rel");
            WHERE("offer_id=#{offerId}");
        }}.toString();
    }
}
