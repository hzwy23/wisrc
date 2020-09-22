package com.wisrc.shipment.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class LogisticsAllowLabelRelSQL {
    public static String findByCond(@Param("labelCd") final int labelCd, @Param("offerId") final String offerId, @Param("deleteStatus") int deleteStatus) {
        return new SQL() {{
            SELECT("uuid, offer_id, label_cd,delete_status");
            FROM("logistics_allow_label_rel");
            WHERE("offer_id=#{offerId}");
            WHERE("delete_status=#{deleteStatus}");
            if (labelCd != 0) {
                WHERE(" label_cd=#{labelCd}");
            }
        }}.toString();
    }

    public static String getShipmentPrice(@Param("offerIdList") final String offerIdList) {
        return new SQL() {{
            SELECT("uuid, offer_id, label_cd, delete_status");
            FROM("logistics_allow_label_rel where delete_status=0 AND offer_id in (" + offerIdList + ")");
        }}.toString();
    }

}
