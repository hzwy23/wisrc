package com.wisrc.warehouse.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class WarehouseManageInfoSQL {
    public static String search(
            @Param("statusCd") int statusCd,
            @Param("typeCd") String typeCd,
            @Param("keyWord") String keyWord
    ) {
        return new SQL() {{
            SELECT(" warehouse_id,warehouse_name,type_cd,status_cd,create_user,create_time,country_cd, province_name,city_name,zip_code, details_addr, sub_warehouse_support, remark, warehouse_contact, warehouse_phone, modify_user, modify_time");
            FROM("warehouse_basis_info");
            if (statusCd != 0) {
                WHERE("status_cd = #{statusCd}");
            }
            if (typeCd != null && !typeCd.trim().isEmpty()) {
                WHERE("type_cd = #{typeCd}");
            }
            if (null != keyWord) {
                WHERE("warehouse_name like concat('%',#{keyWord},'%')");
            }
            ORDER_BY("modify_time desc");
        }}.toString();
    }

    public static String find(
            @Param("idList") String idList
    ) {
        return new SQL() {{
            SELECT("warehouse_id,warehouse_name,type_cd,status_cd,create_user,create_time,country_cd, province_name,city_name,zip_code, details_addr, sub_warehouse_support, remark, warehouse_contact, warehouse_phone, modify_user, modify_time");
            FROM("warehouse_basis_info");
            WHERE("warehouse_id in (" + idList + ")");
        }}.toString();
    }


}