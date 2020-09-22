package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.vo.FnSkuStockVO;
import com.wisrc.warehouse.webapp.vo.StockDetailVO;
import com.wisrc.warehouse.webapp.entity.FbaStockDetailEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockDetailDao {

    @Select("select msku_id, fn_sku_id, a.shop_id as shop_id,b.shop_name as shop_name , sum_stock,freeze_stock_num as unable_quantity, enable_stock_num from warehouse_stock_manager_sync a left join erp_operation.basic_shop_info b on a.shop_id = b.shop_id where " +
            "  sku_id=#{skuId} and warehouse_id=#{warehouseId} order by sum_stock desc")
    List<FbaStockDetailEntity> getFbaDetailById(@Param("skuId") String skuId, @Param("warehouseId") String warehouseId);

    @Select("select fn_sku_id,subWarehouse_name,warehouse_position_id,enter_batch,production_batch,ifnull(sum_stock,0) as sum_stock,0 as distribute_stock_num, " +
            "(ifnull(freeze_stock_num,0)+ifnull(assigned_num+wait_up_num,0)+ifnull(wait_up_num,0)+ifnull(replenishment_wait_down_num,0)+ifnull(replenishment_wait_up_num,0)) as unable_stock, " +
            "ifnull(enable_stock_num,0) as enable_stock_num  from warehouse_stock_manager_sync  where sku_id=#{skuId} and warehouse_id=#{warehouseId}")
    List<StockDetailVO> getDetailById(@Param("skuId") String skuId, @Param("warehouseId") String warehouseId);

    @Select("select fn_sku_id,sku_id,sku_name,warehouse_id,warehouse_name,warehouse_position_id as warehouse_position,enter_batch," +
            "production_batch,sum_stock as on_warehouse_stock_num,enable_stock_num,subWarehouse_id,subWarehouse_name from warehouse_stock_manager_sync where warehouse_id=#{warehouseId} and fn_sku_id=#{fnSkuId} order by enter_batch asc")
    List<FnSkuStockVO> getFnSkuStock(@Param("fnSkuId") String fnSkuId, @Param("warehouseId") String warehouseId);

    @Update("update warehouse_fba_stock_details set stock_total_quantity=#{stockTotalQuantity},unable_quantity=#{unableQuantity},useable_quantity=#{useableQuantity} where shop_id=#{shopId} and msku_id=#{msku}")
    void updateFbaStockDetail(@Param("shopId") String shopId, @Param("msku") String msku, @Param("stockTotalQuantity") int stockTotalQuantity, @Param("useableQuantity") int useableQuantity, @Param("unableQuantity") int unableQuantity);

    @Update("update ws_rmp_warehouse_stock_sum set fba_stock_qty=#{stockTotalQuantity} where seller_id=#{sellerId} and msku=#{msku}")
    void updateWarehouseStock(@Param("sellerId") String sellerId, @Param("msku") String msku, @Param("stockTotalQuantity") int stockTotalQuantity);

    @Select("select sku_id,sku_name,fn_sku_id ,warehouse_id,warehouse_name,subWarehouse_id,subWarehouse_name,warehouse_position_id,enter_batch,production_batch,enable_stock_num  from warehouse_stock_manager_sync where sku_id=#{skuId} and warehouse_id like concat('%','B','%')")
    List<FnSkuStockVO> getFnSkuStockOversea(@Param("skuId") String skuId);

    @Select("select fn_sku_id,sku_id,sku_name,warehouse_id,warehouse_name,subWarehouse_id,subWarehouse_name,warehouse_position_id as warehouse_position,enter_batch," +
            "production_batch,sum_stock as on_warehouse_stock_num,enable_stock_num from warehouse_stock_manager_sync where warehouse_id=#{warehouseId} and sku_id=#{skuId} order by enter_batch asc")
    List<FnSkuStockVO> getSkuStock(@Param("skuId") String skuId, @Param("warehouseId") String warehouseId);

    @Delete("delete from warehouse_stock_manager_sync where warehouse_id=#{warehouseId} and sku_id=#{skuId} and fn_sku_id=#{fnsku} and shop_id=#{shopId}")
    void deleteWarehouse(@Param("warehouseId") String warehouseId, @Param("fnsku") String fnsku, @Param("skuId") String skuId, @Param("shopId") String shopId);

    @Insert("insert into warehouse_stock_manager_sync (warehouse_id, sku_id, fn_sku_id, sum_stock, enable_stock_num, freeze_stock_num, shop_id, msku_id) values (#{warehouseId},#{skuId},#{fnsku},#{stockTotalQuantity},#{useableQuantity},#{unableQuantity},#{shopId},#{mskuId})")
    void insertWarehouse(@Param("warehouseId") String warehouseId, @Param("fnsku") String fnsku, @Param("skuId") String skuId, @Param("stockTotalQuantity") int stockTotalQuantity, @Param("useableQuantity") int useableQuantity, @Param("unableQuantity") int unableQuantity, @Param("shopId") String shopId, @Param("mskuId") String mskuId);

    @Select("select warehouse_id, warehouse_name from warehouse_stock_manager_sync where warehouse_id=#{warehouseId} and sku_id=#{skuId} and fn_sku_id=#{fnsku} and shop_id=#{shopId}")
    List<Map> getByCond(@Param("warehouseId") String warehouseId, @Param("fnsku") String fnsku, @Param("skuId") String skuId, @Param("shopId") String shopId);

    @Update("update warehouse_stock_manager_sync set sum_stock=#{stockTotalQuantity}, enable_stock_num=#{useableQuantity}, freeze_stock_num=#{unableQuantity} where warehouse_id=#{warehouseId} and sku_id=#{skuId} and fn_sku_id=#{fnsku} and shop_id=#{shopId} and msku_id=#{mskuId}")
    void updateStockDetail(@Param("warehouseId") String warehouseId, @Param("fnsku") String fnsku, @Param("skuId") String skuId, @Param("stockTotalQuantity") int stockTotalQuantity, @Param("useableQuantity") int useableQuantity, @Param("unableQuantity") int unableQuantity, @Param("shopId") String shopId, @Param("mskuId") String mskuId);

    @Delete("delete from warehouse_stock_manager_sync where warehouse_id=#{warehouseId} and sku_id=#{skuId} and shop_id=#{shopId} and fn_sku_id=#{fnsku}")
    void deleteWareByCond(@Param("warehouseId") String warehouseId, @Param("skuId") String skuId, @Param("shopId") String shopId, @Param("fnsku") String fnsku);
}
