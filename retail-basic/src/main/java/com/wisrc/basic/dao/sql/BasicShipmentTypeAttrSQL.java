package com.wisrc.basic.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class BasicShipmentTypeAttrSQL {
    public static String findTypeAttr(@Param("shipmentType") final int shipmentType) {
        return new SQL() {{
            SELECT("shipment_type, shipment_type_desc");
            FROM("shipment_type_attr");
            if (shipmentType != 0) {
                WHERE("shipment_type =#{shipmentType}");
            }
        }}.toString();
    }
}
