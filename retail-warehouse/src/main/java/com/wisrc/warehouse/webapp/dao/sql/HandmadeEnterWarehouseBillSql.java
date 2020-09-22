package com.wisrc.warehouse.webapp.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class HandmadeEnterWarehouseBillSql {
    public static String getList(
            @Param("enterBillId") String enterBillId,
            @Param("warehouseId") String warehouseId,
            @Param("enterTypeCd") int enterTypeCd,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime) {
        return new SQL() {{

            SELECT("a.enter_bill_id, a.warehouse_id,b.warehouse_name,c.type_name, a.enter_type_cd, a.create_user, a.create_time , a.status_cd as statusCd , d.status_name as statusName");
            FROM("handmade_enter_warehouse_bill a left join warehouse_basis_info b on a.warehouse_id=b.warehouse_id left join enter_warehouse_type_attr c on a.enter_type_cd = c.enter_type_cd left join handmade_enter_warehouse_attr d on a.status_cd = d.status_cd");
            if (enterBillId != null) {
                WHERE("a.enter_bill_id like concat('%',#{enterBillId},'%')");
            }
            if (warehouseId != null) {
                WHERE("a.warehouse_id = #{warehouseId}");
            }
            if (enterTypeCd != 0) {
                WHERE("a.enter_type_cd = #{enterTypeCd}");
            }
            if (startTime != null) {
                //WHERE("a.create_time between #{startTime} and #{endTime}");
                WHERE("date(a.create_time) >= #{startTime}");
            }
            if (endTime != null) {
                WHERE("date(a.create_time) <= #{endTime}");
            }
            ORDER_BY("create_time desc");
        }}.toString();
    }


    public String findListByCond(@Param("warehouseId") String warehouseId,
                                 @Param("enterTypeCd") Integer enterTypeCd,
                                 @Param("status") Integer status,
                                 @Param("startTime") String startTime,
                                 @Param("endTime") String endTime,
                                 @Param("keyword") String keyword) {
        return new SQL() {{
            SELECT("enter_bill_id,warehouse_id,warehouse_name,enter_type_cd,type_name AS enter_type_name ,status_cd,status_name,create_user,date_format(create_time,'%Y-%m-%d') AS create_time ,date_format(enter_warehouse_time,'%Y-%m-%d') AS enter_warehouse_time");
            FROM("v_handmade_info");
            if (StringUtils.isNotEmpty(warehouseId)) {
                WHERE("warehouse_id = #{warehouseId}");
            }
            if (status != null) {
                WHERE("status_cd = #{status}");
            }
            if (enterTypeCd != null) {
                WHERE("enter_type_Cd = #{enterTypeCd}");
            }
            if (StringUtils.isNotEmpty(startTime)) {
                WHERE("date_format(create_time,'%Y-%m-%d') >= #{startTime}");
            }
            if (StringUtils.isNotEmpty(endTime)) {
                WHERE("date_format(create_time,'%Y-%m-%d') <= #{endTime}");
            }
            if (StringUtils.isNotEmpty(keyword)) {
                WHERE("enter_bill_id LIKE concat('%',#{keyword},'%') OR sku_id LIKE concat('%',#{keyword},'%') OR fn_sku_id LIKE concat('%',#{keyword},'%') OR sku_name_zh LIKE concat('%',#{keyword},'%')");
            }
            GROUP_BY("enter_bill_id");
            ORDER_BY("enter_bill_id desc");
        }}.toString();
    }


}
