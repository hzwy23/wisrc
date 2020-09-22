package com.wisrc.basic.dao.sql;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class BasicShopSQL {
    public static String search(@Param("platformName") final String platformName,
                                @Param("shopId") final String shopId,
                                @Param("shopName") final String shopName,
                                @Param("statusCd") final String statusCd) {
        return new SQL() {{
            SELECT("shop_id, plat_id, shop_name, shop_owner_id, security_key, aws_access_key, status_cd, modify_user, modify_time, plat_name, plat_site");
            FROM("v_shop_details_info");
            if (platformName != null) {
                WHERE("plat_name like concat('%',#{platformName},'%')");
            }

            if (statusCd != null) {
                WHERE("status_cd like concat('%',#{statusCd},'%')");
            }

            if (shopId != null) {
                WHERE("shop_id like concat('%',#{shopId},'%')");
            }

            if (shopName != null) {
                WHERE("shop_name like concat('%',#{shopName},'%')");
            }
            ORDER_BY("status_cd asc");
        }}.toString();
    }
}
