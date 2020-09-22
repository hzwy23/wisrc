package com.wisrc.warehouse.webapp.dao.sql;

import com.wisrc.warehouse.webapp.query.GetTotalNumQuery;
import com.wisrc.warehouse.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class BasicStockSQL {
    public static String search(
            @Param("warehouseId") final String warehouseId,
            @Param("keyword") final String keyword) {
        return new SQL() {{

            SELECT("a.sku_id as sku_id, b.sku_name_zh as sku_name, a.warehouse_id as warehouse_id,c.warehouse_name as warehouse_name," +
                    "a.total_sum as total_sum,a.on_way_stock as on_way_stock,a.sum_stock as sum_stock,a.enable_stock_num as enable_stock_num," +
                    "a.last_inout_time as last_inout_time");
            FROM("v_stock_manager a left join erp_product.erp_product_define b on a.sku_id =b.sku_id left join warehouse_basis_info c on a.warehouse_id = c.warehouse_id");
            if (warehouseId != null) {
                WHERE("a.warehouse_id = #{warehouseId}");
            }
            if (keyword != null) {
                WHERE("(a.sku_id like concat('%',#{keyword},'%') OR b.sku_name_zh like concat('%',#{keyword},'%'))");
            }
            ORDER_BY("a.last_inout_time DESC");
        }}.toString();
    }


    public static String getStockBySku(@Param("skuIdList") List skuIdList) {
        return SQLUtil.forUtil("select sku_id, sku_name, warehouse_id, warehouse_name, total_sum, on_way_stock, sum_stock, enable_stock_num, last_inout_time from " +
                " v_stock_manager" +
                " where sku_id", skuIdList);
    }

    public static String getTotalNum(GetTotalNumQuery getTotalNumQuery) {
        return new SQL() {{
            SELECT("sku_id", "fn_sku_id", "subWarehouse_id", "warehouse_id", "SUM(sum_stock) AS sum_stock");
            FROM("warehouse_stock_manager_sync");
            WHERE("subWarehouse_id = #{warehouseId}");
            WHERE(SQLUtil.forUtil("sku_id", getTotalNumQuery.getSkuIds()));
            GROUP_BY("sku_id", "fn_sku_id", "warehouse_id");
        }}.toString();
    }
}
