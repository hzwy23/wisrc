package com.wisrc.warehouse.webapp.dao.sql;


import com.wisrc.warehouse.webapp.utils.SQLUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class BlitemBasicSQL {
    public static String search(@Param("blitemId") String blitemId,
                                @Param("warehouseId") final String warehouseId,
                                @Param("startDate") final String startDate,
                                @Param("endDate") final String endDate,
                                @Param("skuIds") List<String> skuIds) {
        return new SQL() {{
            SELECT("DISTINCT a.blitem_id, a.warehouse_id, a.blitem_date, a.status_cd, a.operation_user," +
                    " a.operation_date, a.audit_user, a.audit_date, a.delete_status, a.create_time, a.create_user");
            FROM("blitem_info a left join blitem_listing_info b on a.blitem_id = b.blitem_id");
            if (StringUtils.isNotEmpty(blitemId)) {
                WHERE(" a.blitem_id like concat('%',#{blitemId},'%')");
            }
            if (StringUtils.isNotEmpty(warehouseId)) {
                WHERE(" warehouse_id like concat('%',#{warehouseId},'%')");
            }
            if (StringUtils.isNotEmpty(startDate)) {
                WHERE(" DATE_FORMAT(a.blitem_date,'%Y-%m-%d') >= #{startDate}");
            }
            if (StringUtils.isNoneEmpty(endDate)) {
                WHERE(" DATE_FORMAT(a.blitem_date,'%Y-%m-%d') <= #{endDate}");
            }
            if (CollectionUtils.isNotEmpty(skuIds)) {
                WHERE(SQLUtil.forUtil("b.sku_id", skuIds));
            }
            WHERE("a.delete_status = 0");
            ORDER_BY("a.create_time DESC");
        }}.toString();

    }
}
