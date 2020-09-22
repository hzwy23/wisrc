package com.wisrc.shipment.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class LittlePackOfferDetailsSQL {

    public static String findByCond(@Param("offerId") final String offerId, @Param("deleteStatus") int deleteStatus) {
        return new SQL() {{
            SELECT("uuid,offer_id, start_charge_section, end_charge_section, first_weight, first_weight_price, peer_weight, peer_weight_price,delete_status");
            FROM("little_package_offer_details ");
            WHERE("offer_id=#{offerId}");
            WHERE("delete_status=#{deleteStatus}");
        }}.toString();
    }
}
