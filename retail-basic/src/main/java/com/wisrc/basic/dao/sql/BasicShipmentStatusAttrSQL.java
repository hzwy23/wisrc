package com.wisrc.basic.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class BasicShipmentStatusAttrSQL {
    public static String findStatusAttr(@Param("statusCd") final int statusCd) {
        return new SQL() {{
            SELECT("status_cd, status_name");
            FROM("shipment_status_attr");
            if (statusCd != 0) {
                WHERE("status_cd =#{statusCd}");
            }
        }}.toString();
    }
}
