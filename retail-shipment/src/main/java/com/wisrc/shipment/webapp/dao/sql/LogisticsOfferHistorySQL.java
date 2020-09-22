package com.wisrc.shipment.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class LogisticsOfferHistorySQL {
    public static String getShipmentPrice(@Param("offerIdList") final String offerIdList) {
        return new SQL() {{
            SELECT("uuid, offer_id, modify_time, section, unit_price, unit_price_with_oil, delete_status");
            FROM("v_logistics_price_history where delete_status=0 AND offer_id in (" + offerIdList + ")");
        }}.toString();
    }

    public static String batchPriceV2(@Param("offerIdsStr") final String offerIdsStr) {
        return new SQL() {{
            SELECT(
                    "A.uuid,\n" +
                            "  A.offer_id,\n" +
                            "  A.unit_price,\n" +
                            "  A.unit_price_with_oil,\n" +
                            "  A.section,\n" +
                            "  max(A.modify_time)");
            FROM(" ( SELECT *\n" +
                    "      FROM v_logistics_price_history\n" +
                    "      WHERE 1 = 1\n" +
                    "            AND offer_id IN\n" + offerIdsStr +
                    "            AND delete_status = 0\n" +
                    "      ORDER BY modify_time DESC) A ");
            GROUP_BY(" A.uuid");
        }}.toString();
    }
}
