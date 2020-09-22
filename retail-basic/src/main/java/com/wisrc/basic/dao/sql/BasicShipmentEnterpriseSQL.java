package com.wisrc.basic.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class BasicShipmentEnterpriseSQL {
    public static String findByCond(@Param("statusCd") final String statusCd,
                                    @Param("keyword") final String keyword) {
        return new SQL() {{
            SELECT("shipment_id, shipment_name, shipment_addr, contact, phone, qq,shipment_type, status_cd, modify_time, modify_user, create_time, create_user,country_en,country_name,province_en,province_name,city_en,city_name");
            FROM("shipment_enterprise");
            if (statusCd != null) {
                WHERE("status_cd = #{statusCd}");
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                WHERE("( shipment_name like concat('%',#{keyword},'%') OR contact like concat('%',#{keyword},'%') )");
            }
            ORDER_BY("status_cd asc, modify_time desc");
        }}.toString();
    }

    public static String findByListId(@Param("idList") final String idList) {
        return new SQL() {{
            SELECT("shipment_id, shipment_name, shipment_addr, contact, phone, qq,shipment_type, status_cd, modify_time, modify_user, create_time, create_user,country_en,country_name,province_en,province_name,city_en,city_name");
            FROM("shipment_enterprise");
            WHERE("shipment_id in (" + idList + ")");
        }}.toString();
    }


    public static String findByName(@Param("statusCd") int statusCd,
                                    @Param("shipmentName") String shipmentName,
                                    @Param("contact") String contact) {
        return new SQL() {{
            SELECT("shipment_id, shipment_name, shipment_addr, contact, phone, qq,shipment_type, status_cd, modify_time, modify_user, create_time, create_user,country_en,country_name,province_en,province_name,city_en,city_name");
            FROM("shipment_enterprise");
            if (statusCd != 0) {
                WHERE("status_cd=#{statusCd}");
            }
            if (shipmentName != null) {
                WHERE("shipment_name like concat('%',#{shipmentName},'%')");
            }

            if (contact != null) {
                WHERE("contact like concat('%',#{contact},'%')");
            }
            ORDER_BY("status_cd asc, modify_time desc");
        }}.toString();
    }

}
