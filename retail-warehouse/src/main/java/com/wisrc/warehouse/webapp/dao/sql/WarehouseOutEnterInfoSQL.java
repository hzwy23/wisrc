package com.wisrc.warehouse.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Timestamp;

public class WarehouseOutEnterInfoSQL {
    public static String search(@Param("documentType") final String documentType,
                                @Param("outEnterType") final int outEnterType,
                                @Param("warehouseId") final String warehouseId,
                                @Param("createTimeBegin") final Timestamp createTimeBegin,
                                @Param("createTimeEnd") final Timestamp createTimeEnd,
                                @Param("keyWord") final String keyWord) {
        return new SQL() {{
            SELECT("sku_id,sku_name,fn_sku_id, a.warehouse_id,b.warehouse_name,warehouse_position,enter_batch,production_batch,change_ago_num,change_num,change_later_num,   " +
                    "   source_id,document_type,a.create_time,a.create_user,a.remark  ");
            FROM("out_enter_warehouse_water a left join warehouse_basis_info b on a.warehouse_id = b.warehouse_id");
            if (documentType != null) {
                WHERE("a.document_type = #{documentType}");
            }
            if (outEnterType == 1) {
                WHERE("a.change_num > 0");
            }
            if (outEnterType == 2) {
                WHERE("a.change_num < 0");
            }
            if (warehouseId != null) {
                WHERE("a.warehouse_id = #{warehouseId}");
            }
            if (createTimeBegin != null) {
                WHERE("a.create_time > #{createTimeBegin}");
            }
            if (createTimeEnd != null) {
                WHERE("a.create_time < #{createTimeEnd}");
            }
            if (null != keyWord) {
                WHERE("(a.fn_sku_id like concat('%',#{keyWord},'%') or a.sku_id like concat('%',#{keyWord},'%') or a.source_id like concat('%',#{keyWord},'%') or a.sku_name like concat('%',#{keyWord},'%') or a.remark like concat('%',#{keyWord},'%') )");
            }
            ORDER_BY("a.create_time desc");
        }}.toString();
    }
}
