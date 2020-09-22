package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.dao.sql.BasicStockSQL;
import com.wisrc.warehouse.webapp.dao.sql.StockSql;
import com.wisrc.warehouse.webapp.vo.stockVO.ProxyVirtual;
import com.wisrc.warehouse.webapp.entity.StockEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseStockManagerSyncEntity;
import com.wisrc.warehouse.webapp.query.GetTotalNumQuery;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface StockDao {

    @SelectProvider(type = BasicStockSQL.class, method = "search")
    List<StockEntity> findStockByCondition(@Param("warehouseId") String warehouseId,
                                           @Param("keyword") String keyword);

    @SelectProvider(type = BasicStockSQL.class, method = "search")
    List<StockEntity> findAll(@Param("warehouseId") String warehouseId,
                              @Param("keyword") String keyword);

    @SelectProvider(type = BasicStockSQL.class, method = "getStockBySku")
    List<StockEntity> getStockBySku(@Param("skuIdList") List skuIdList);


    @Select("select sku_id, sku_name, warehouse_id, warehouse_name, total_sum, on_way_stock, sum_stock, enable_stock_num, last_inout_time from" +
            " v_stock_manager where sku_id = #{skuId} and warehouse_id=#{warehouseId}")
    StockEntity getStockByCond(@Param("skuId") String skuId, @Param("warehouseId") String warehouseId);


    /**
     * 根据分库ID查询是否还有可用库存
     */
    @Select("select count(*) from warehouse_stock_manager_sync where subWarehouse_id = #{subWarehouseId}")
    int hasSkuBySubwarehouseId(String subWarehouseId);


    /**
     * 根据仓库ID查询是否还有可用库存
     */
    /*@Select("select count(*) from warehouse_stock_manager where warehouse_id = #{warehouseId}")
    int hasSkuByWarehouseId(String warehouseId);*/
    @Select("SELECT sku_id, warehouse_id, warehouse_name, virtual_qty as number from warehouse_sku_stock_details_info where sku_id=#{skuId} and as_of_date=#{date} and substr(warehouse_id,1,1)='C' and virtual_qty>0 ")
    List<ProxyVirtual> getVirtualStock(@Param("skuId") String skuId, @Param("date") String date);

    @Select("select sku_id as skuId,sum(total_sum) as totalSum from v_stock_manager where sku_id in (${skuIds}) group by sku_id")
    List<Map> getStockTotalBySku(@Param("skuIds") String skuIds);

    /*@Select("SELECT enable_stock_num FROM warehouse_stock_manager WHERE warehouse_id = #{warehouseId} AND sku_id = #{skuId}")
    Integer getEnableStockByWarehouseAndSku(@Param("warehouseId")String warehouseId,
                                        @Param("skuId")String skuId);

    @Select("SELECT enable_stock_num FROM warehouse_stock_manager WHERE sku_id = #{skuId}")
    int getEnableStockBySku(@Param("skuId")String skuId);*/

    @SelectProvider(type = BasicStockSQL.class, method = "getTotalNum")
    List<WarehouseStockManagerSyncEntity> getTotalNum(GetTotalNumQuery getTotalNumQuery);

    @SelectProvider(type = StockSql.class, method = "getStockInfo")
    WarehouseStockManagerSyncEntity getStockInfo(@Param("skuId") String skuId,
                                                 @Param("fnCode") String fnCode,
                                                 @Param("subWarehouseId") String subWarehouseId);
}
