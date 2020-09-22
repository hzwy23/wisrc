package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.vo.OutEnterWaterReturnVO;
import com.wisrc.warehouse.webapp.vo.syncVO.StockReturnVO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WarehouseStockReturnDao {
    @Insert("INSERT into warehouse_stock_manager_sync(warehouse_id, warehouse_name, subWarehouse_id, subWarehouse_name, warehouse_zone_id, warehouse_zone_name, warehouse_position_id, warehouse_position, sku_id, sku_name, fn_sku_id, enter_batch, production_batch, sum_stock, enable_stock_num, freeze_stock_num, assigned_num, wait_up_num, replenishment_wait_down_num, replenishment_wait_up_num, b_id,on_way_stock) " +
            "values(#{warehouseId},#{warehouseName},#{subWarehouseId},#{subWarehouseName},#{warehouseZoneId},#{warehouseZoneName},#{warehousePositionId},#{warehousePosition},#{skuId},#{skuName},#{fnSkuId},#{enterBatch},#{productionBatch},#{sumStock},#{enableStockNum},#{freezeStockNum},#{assignedNum},#{waitUpNum},#{replenishmentWaitDownNum},#{replenishmentWaitUpNum},#{bId},0)")
    boolean insertStock(StockReturnVO vo);

    /**
     * 查询库存信息是否有该条信息
     *
     * @param bid 库存数据的唯一标识
     * @return
     */
    @Select("SELECT count(*) from warehouse_stock_manager_sync WHERE b_id = #{bId}")
    int findStockRecordByCondition(@Param("bId") int bid);


    /**
     * 根据bid更新数据
     *
     * @param stockReturnVO
     */
    @Update("UPDATE warehouse_stock_manager_sync SET warehouse_id = #{warehouseId} ,warehouse_name = #{warehouseName} ,subWarehouse_id = #{subWarehouseId} ,subWarehouse_name = #{subWarehouseName}, warehouse_zone_id = #{warehouseZoneId}," +
            "  warehouse_zone_name = #{warehouseZoneName} ,warehouse_position_id = #{warehousePositionId},warehouse_position = #{warehousePosition},sku_id = #{skuId},sku_name =#{skuName} ,fn_sku_id =#{fnSkuId}, enter_batch = #{enterBatch} ,production_batch = #{productionBatch}," +
            "  sum_stock = #{sumStock},enable_stock_num = #{enableStockNum}, freeze_stock_num = #{freezeStockNum},assigned_num = #{assignedNum}, wait_up_num = #{waitUpNum} , replenishment_wait_down_num = #{replenishmentWaitDownNum}," +
            "  replenishment_wait_up_num = #{replenishmentWaitUpNum} WHERE b_id = #{bId} ")
    void updateStockRecord(StockReturnVO stockReturnVO);


    @Insert("insert into out_enter_warehouse_water " +
            "(mark_id, detail_id, warehouse_id, sub_warehouse_id, sku_id, sku_name, fn_sku_id, warehouse_position, enter_batch, production_batch, enable_sum_stock_num, sum_stock_num, " +
            " change_ago_num, change_num, change_later_num, out_enter_type, source_id, document_type, create_time, create_user, user_code, remark) values " +
            "(#{markId},#{detailId},#{warehouseId},#{subWarehouseId},#{skuId},#{skuName},#{fnSkuId},#{warehousePosition},#{enterBatch},#{productionBatch},#{enableSumStockNum},#{sumStockNum}, " +
            "#{changeAgoNum},#{changeNum},#{changeLaterNum},#{outEnterType},#{sourceId},#{documentType},#{createTime},#{createUser},#{userCode},#{remark})")
    void insertWater(OutEnterWaterReturnVO vo);

    @Delete("truncate table warehouse_stock_manager_temp")
    void deleteAllTempData();

    /**
     * 合并临时库存数据到实际库存数据
     */
    @Update("UPDATE warehouse_stock_manager_sync set enable_stock_num = 0 , sum_stock = 0, freeze_stock_num = 0,assigned_num = 0, wait_up_num = 0, replenishment_wait_up_num = 0,\n" +
            "  replenishment_wait_down_num = 0 WHERE NOT exists(SELECT 1 from warehouse_stock_manager_temp WHERE warehouse_stock_manager_temp.b_id = warehouse_stock_manager_sync.b_id ) and warehouse_stock_manager_sync.warehouse_id not like 'F%'")
    void mergeData();

    /**
     * 插入临时库存数据
     *
     * @param stockReturnVO
     */
    @Insert("INSERT into warehouse_stock_manager_temp(warehouse_id, warehouse_name, subWarehouse_id, subWarehouse_name, warehouse_zone_id, warehouse_zone_name, warehouse_position_id, warehouse_position, sku_id, sku_name, fn_sku_id, enter_batch, production_batch, sum_stock, enable_stock_num, freeze_stock_num, assigned_num, wait_up_num, replenishment_wait_down_num, replenishment_wait_up_num, b_id) " +
            "values(#{warehouseId},#{warehouseName},#{subWarehouseId},#{subWarehouseName},#{warehouseZoneId},#{warehouseZoneName},#{warehousePositionId},#{warehousePosition},#{skuId},#{skuName},#{fnSkuId},#{enterBatch},#{productionBatch},#{sumStock},#{enableStockNum},#{freezeStockNum},#{assignedNum},#{waitUpNum},#{replenishmentWaitDownNum},#{replenishmentWaitUpNum},#{bId})")
    void insertTempStock(StockReturnVO stockReturnVO);
}
