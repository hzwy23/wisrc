package com.wisrc.code.webapp.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class AreaSQL {
    public static String search(@Param("keyword") String keyword, @Param("countryCd") String countryCd) {
        return new SQL() {{
            SELECT("uuid, country_cd, country_name, country_english, province_name_en, province_name_cn, city_name_en, city_name_cn, modify_time, type_cd");
            FROM("v_administrative_division_info");
            if (countryCd != null && !countryCd.trim().isEmpty()) {
                WHERE("country_cd = #{countryCd}");
            }
            if (keyword != null && !keyword.isEmpty()) {
                WHERE("country_name like concat('%', #{keyword}, '%')");
                OR();
                WHERE("province_name_cn like concat('%', #{keyword}, '%')");
                OR();
                WHERE("city_name_cn like concat('%', #{keyword}, '%')");
                OR();
                WHERE("country_cd like concat('%', #{keyword}, '%')");
                OR();
                WHERE("province_name_en like concat('%', #{keyword}, '%')");
                OR();
                WHERE("city_name_en like concat('%', #{keyword}, '%')");
                OR();
                WHERE("country_english like concat('%', #{keyword}, '%')");
            }
            ORDER_BY(" modify_time desc ");
        }}.toString();
    }
}
