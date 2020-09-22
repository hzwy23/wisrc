package com.wisrc.shipment.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashSet;

public class ReplenishmentRemindSQL {

    public String selectRemind(@Param("shopId") String shopId, @Param("warningType") Integer warningType,
                               @Param("mskuIds") HashSet<String> mskuIds, @Param("sort") String sort) {
        return new SQL() {
            {
                SELECT("replenishment_id, msku_id, shop_id, fba_warehouse_stock, fba_warehouse_available_days, fba_way_stock, fba_way_available_days, warning_type_cd, estimated_daily_sales, fba_available_days, safe_stock_days, replenishment_quantity");
                FROM("fba_replenishment_remind");
                if (shopId != null) {
                    WHERE("shop_id = #{shopId}");
                }
                if (warningType != null) {
                    WHERE("warning_type_cd = #{warningType}");
                }
                if (!mskuIds.isEmpty()) {
                    for (String msku : mskuIds) {
                        OR();
                        WHERE("msku_id = '" + msku.replaceAll("('|,)", "") + "'");
                    }
                }
                if (sort != null) {
                    ORDER_BY(sort);
                }
            }
        }.toString();
    }

    public String selectSchemed(@Param("uuid") String uuid, @Param("replenishmentId") String replenishmentId) {
        return new SQL() {
            {
                SELECT("uuid, replenishment_id, originating_warehouse_id, delivering_amount, ship_type_cd, shipment_id, effective_days, unit_price, forecast_freight, total_price");
                FROM("proposal_scheme");
                if (uuid != null) {
                    WHERE("uuid = #{uuid}");
                }
                if (replenishmentId != null) {
                    WHERE("replenishment_id = #{replenishmentId}");
                }
            }
        }.toString();
    }

}
