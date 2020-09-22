package com.wisrc.basic.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class BasicPlatformSQL {

    public static String search(@Param("platformName") final String platformName,
                                @Param("statusCd") final String statusCd) {
        return new SQL() {{
            SELECT("plat_id,plat_name,plat_site,api_url,status_cd,modify_user,modify_time,market_place_id");
            FROM("basic_platform_info");
            if (null != platformName) {
                WHERE("plat_name like concat('%',#{platformName},'%')");
            }
            if (statusCd != null) {
                WHERE("status_cd = #{statusCd}");
            }
        }}.toString();
    }
}
