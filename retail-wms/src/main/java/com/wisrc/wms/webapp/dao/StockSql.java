package com.wisrc.wms.webapp.dao;

import com.wisrc.wms.webapp.vo.ReturnVO.StockReturnVO;
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
                FROM("warehouse_stock_manager");
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
            UPDATE("warehouse_stock_manager");
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
}
