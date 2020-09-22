package com.wisrc.warehouse.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class HandmadeOutWarehouseBillSql {
    public static String getList(
            @Param("outBillId") String outBillId,
            @Param("warehouseId") String warehouseId,
            @Param("outTypeCd") int outTypeCd,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime) {
        return new SQL() {{

            SELECT("a.out_bill_id, a.warehouse_id,b.warehouse_name,c.type_name, a.out_type_cd, a.create_user, a.create_time");
            FROM("handmade_out_warehouse_bill a left join warehouse_basis_info b on a.warehouse_id=b.warehouse_id left join out_warehouse_type_attr c on a.out_type_cd = c.out_type_cd");
            if (outBillId != null) {
                WHERE("a.out_bill_id like concat('%',#{outBillId},'%')");
            }
            if (warehouseId != null) {
                WHERE("a.warehouse_id = #{warehouseId}");
            }
            if (outTypeCd != 0) {
                WHERE("a.out_type_cd = #{outTypeCd}");
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
}
