package com.wisrc.warehouse.webapp.dao.sql;

import com.wisrc.warehouse.webapp.vo.syncVO.StockReturnVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class StockSql {

    public static String getSql(@Param("warehousePositionId") String warehousePositionId,
                                @Param("productionBatch") String productionBatch,
                                @Param("enterBatch") String enterBatch,
                                @Param("fnSkuId") String fnSkuId,
                                @Param("skuId") String skuId) {
        return new SQL() {
            {
                SELECT("count(*)");
                FROM("warehouse_stock_manager_sync");
                if (warehousePositionId == null) {
                    WHERE("warehouse_position_id is #{warehousePositionId}");
                } else {
                    WHERE("warehouse_position_id = #{warehousePositionId}");
                }
                if (productionBatch == null) {
                    WHERE(" production_batch is #{productionBatch}");
                } else {
                    WHERE("production_batch = #{productionBatch}");
                }
                if (enterBatch == null) {
                    WHERE("enter_batch is #{enterBatch}");
                } else {
                    WHERE("enter_batch = #{enterBatch}");
                }
                if (fnSkuId == null) {
                    WHERE("fn_sku_id is #{fnSkuId}");
                } else {
                    WHERE("fn_sku_id = #{fnSkuId}");
                }
                if (skuId == null) {
                    WHERE("sku_id is #{skuId}");
                } else {
                    WHERE("sku_id = #{skuId}");
                }
            }
        }.toString();
    }


    public static String updateSql(StockReturnVO stockReturnVO) {
        return new SQL() {{
            UPDATE("warehouse_stock_manager_sync");
            SET("enter_batch = #{enterBatch} ,production_batch = #{productionBatch} ,sum_stock = #{sumStock} ,enable_stock_num = #{enableStockNum},freeze_stock_num = #{freezeStockNum} ," +
                    "    assigned_num = #{assignedNum},wait_up_num = #{waitUpNum} ,replenishment_wait_down_num = #{replenishmentWaitDownNum} ,replenishment_wait_up_num = #{replenishmentWaitUpNum}");
            if (stockReturnVO.getWarehousePositionId() == null) {
                WHERE("warehouse_position_id is #{warehousePositionId}");
            } else {
                WHERE("warehouse_position_id = #{warehousePositionId}");
            }
            if (stockReturnVO.getProductionBatch() == null) {
                WHERE(" production_batch is #{productionBatch}");
            } else {
                WHERE("production_batch = #{productionBatch}");
            }
            if (stockReturnVO.getEnterBatch() == null) {
                WHERE("enter_batch is #{enterBatch}");
            } else {
                WHERE("enter_batch = #{enterBatch}");
            }
            if (stockReturnVO.getFnSkuId() == null) {
                WHERE("fn_sku_id is #{fnSkuId}");
            } else {
                WHERE("fn_sku_id = #{fnSkuId}");
            }
            if (stockReturnVO.getSkuId() == null) {
                WHERE("sku_id is #{skuId}");
            } else {
                WHERE("sku_id = #{skuId}");
            }
        }}.toString();

    }

    public static String getStockInfo(@Param("skuId") String skuId,
                                      @Param("fnCode") String fnCode,
                                      @Param("subWarehouseId") String subWarehouseId) {
        return new SQL() {{
            SELECT("warehouse_id, warehouse_name, subWarehouse_id, subWarehouse_name, warehouse_zone_id, warehouse_zone_name, warehouse_position_id, warehouse_position, " +
                    "sku_id, sku_name, SUM(sum_stock) AS sum_stock, SUM(on_way_stock) AS on_way_stock, SUM(enable_stock_num) AS enable_stock_num, SUM(freeze_stock_num) AS freeze_stock_num, " +
                    "SUM(assigned_num) AS assigned_num, SUM(wait_up_num) AS wait_up_num, SUM(replenishment_wait_down_num) AS replenishment_wait_down_num, SUM(replenishment_wait_up_num) AS replenishment_wait_up_num");
            FROM("warehouse_stock_manager_sync");
            if (fnCode != null) {
                WHERE("fn_sku_id = #{fnCode}");
            }
            WHERE("sku_id = #{skuId} AND subWarehouse_id = #{subWarehouseId}");
        }}.toString();
    }
}
