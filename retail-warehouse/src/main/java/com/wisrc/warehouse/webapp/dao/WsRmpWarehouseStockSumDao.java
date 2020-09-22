package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.dao.sql.WsRmpWarehouseStockSumSQL;
import com.wisrc.warehouse.webapp.vo.DetailVO;
import com.wisrc.warehouse.webapp.vo.MskuFbaVO;
import com.wisrc.warehouse.webapp.vo.SkuWarehouseVo;
import com.wisrc.warehouse.webapp.vo.TotalDetailVO;
import com.wisrc.warehouse.webapp.vo.stockVO.SkuStockDetailVO;
import com.wisrc.warehouse.webapp.vo.stockVO.SkuVO;
import com.wisrc.warehouse.webapp.entity.MskuToSkuEntity;
import com.wisrc.warehouse.webapp.entity.WsRmpWarehouseStockSumEntity;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Set;

@Mapper
public interface WsRmpWarehouseStockSumDao {
    @SelectProvider(type = WsRmpWarehouseStockSumSQL.class, method = "search")
    List<WsRmpWarehouseStockSumEntity> getStockDetail(@Param("date") String date,
                                                      @Param("skuIdList") List skuIdList,
                                                      @Param("orderFlag") int orderFlag
    );

    @SelectProvider(type = WsRmpWarehouseStockSumSQL.class, method = "getTotal")
    List<TotalDetailVO> getTotal(@Param("date") String date, @Param("skuIds") List skuIds);

    @SelectProvider(type = WsRmpWarehouseStockSumSQL.class, method = "getMskuTotal")
    List<TotalDetailVO> getMskuTotal(@Param("date") String date, @Param("skuIds") List skuIds);

    @Select("select sku_id, fba_return_qty, fba_transport_qty, fba_stock_qty, msku_id,fn_sku_id, shop_name from v_fba_total where sku_id=#{skuId}")
    List<MskuToSkuEntity> getMskuToSkuList(@Param("skuId") String skuId);

    @Insert("insert into ws_rmp_warehouse_stock_sum(data_dt,store_sku,cn_name,product_qty,transport_qty,panyu_stock_qty,virtual_stock_qty,overseas_transport_qty,overseas_stock_qty,total_qty)" +
            "values(#{dataDt},#{skuId},#{skuName},#{productQty},#{transportQty},#{panyuStockQty},#{virtualStockQty},#{overseasTransportQty},#{overseasStockQty},#{totalQty})")
    void addStockSum(SkuVO vo);

    @Insert("insert into fba_warehouse_stock_sum(store_sku,fba_return_qty,fba_transport_qty,fba_stock_qty,msku,shop_name) values(#{storeSku},#{fbaReturnQty},#{fbaTransportQty},#{fbaStockQty},#{msku},#{shopName})")
    void addMskuToSku(MskuToSkuEntity mskuToSkuEntity);

    @Select("select order_id, entry_time, onway_num from v_onway_detail where sku_id=#{skuId} and warehouse_id=#{warehouseId}")
    List<DetailVO> getDetail(@Param("skuId") String skuId, @Param("warehouseId") String warehouseId);

    @Select("select sku_id, sku_name, product_qty, local_onway_qty, local_stock_qty, virtual_stock_qty, overseas_transport_qty, overseas_stock_qty " +
            " from v_sku_stock_total")
    List<WsRmpWarehouseStockSumEntity> getAllRecord();

    @Insert("insert into fba_warehouse_stock_sum (data_dt,store_sku,fba_return_qty,fba_transport_qty,fba_stock_qty,msku,fn_sku_id,shop_name) values " +
            "(#{dataDt},#{skuId},#{fbaReturnQty},#{fbaTransportQty},#{fbaStockQty},#{mskuId},#{fnSkuId},#{shopName})")
    void addMsukStock(MskuToSkuEntity msku);

    @Select("select store_sku as sku_id, fba_return_qty, fba_transport_qty, fba_stock_qty, msku as msku_id,fn_sku_id, shop_name from fba_warehouse_stock_sum where store_sku=#{skuId} and data_dt=#{date}")
    List<MskuToSkuEntity> getMskuList(@Param("skuId") String skuId, @Param("date") String date);

    @Select("select sku_id as skuId, sum(fba_return_qty) as fbaReturnQty, sum(fba_transport_qty) as fbaTransportQty, sum(fba_stock_qty) as fbaStockQty from fba_warehouse_stock_sum where sku_id in (${skuIds}) group by sku_id")
    List<MskuToSkuEntity> getMskuBySkuList(@Param("skuIds") String skuIds);

    @Select("select sku_id as skuId, sum(panyu_stock_qty) as panyuQuantity from v_local_total where sku_id in (${skuIds}) group by sku_id")
    List<Map> getPanyuNum(@Param("skuIds") String skuIds);

    @Select("select store_sku as sku_id,total_qty from ws_rmp_warehouse_stock_sum where store_sku=#{skuId} and data_dt=#{date} group by store_sku")
    WsRmpWarehouseStockSumEntity getStockDetailBySkuId(@Param("skuId") String skuId, @Param("date") String date);

    @Select("select store_sku as sku_id, sum(fba_return_qty) as fbaReturnQty, sum(fba_transport_qty) as fbaTransportQty, sum(fba_stock_qty) as fbaStockQty from fba_warehouse_stock_sum where store_sku=#{skuId} and data_dt=#{date} group by sku_id")
    MskuToSkuEntity getMskuBySkuAndDate(@Param("skuId") String skuId, @Param("date") String date);

    @Delete("DELETE FROM ws_rmp_warehouse_stock_sum WHERE data_dt=#{dataDt}")
    void deleteSkuRecord(@Param("dataDt") String dataDt);

    @Delete("DELETE FROM fba_warehouse_stock_sum WHERE data_dt=#{dataDt}")
    void deleteMskuRecord(@Param("dataDt") String dataDt);

    @Select("select enable_stock_num from v_stock_manager where sku_id=#{skuId} and warehouse_id=#{warehouseId}")
    Double getStockNum(SkuWarehouseVo skuWarehouseVo);

    @Delete("DELETE FROM warehouse_sku_stock_details_info WHERE as_of_date=#{dataDt}")
    void deleteStockDetail(String dataDt);

    @Select("select sku_id, warehouse_id, warehouse_name, local_qty, oversea_qty, virtual_qty from v_sku_stock_detail_info")
    List<SkuStockDetailVO> getStockDetailVO();

    @Insert("insert into warehouse_sku_stock_details_info (as_of_date, sku_id, warehouse_id, warehouse_name, local_qty, oversea_qty, virtual_qty) values " +
            "(#{asOfDate},#{skuId},#{warehouseId},#{warehouseName},#{localQty},#{overseaQty},#{virtualQty})")
    void addSkuStockDetail(SkuStockDetailVO entity);

    @Select({"call proc_warehouse_details_sync(#{dataDt,mode=IN,jdbcType=DATE})"})
    @Options(statementType = StatementType.CALLABLE)
    void batchInsert(Date dataDt);


    @SelectProvider(type = WsRmpWarehouseStockSumSQL.class, method = "getSkuId")
    List<String> getSkuId(@Param("date") String date, @Param("keyword") String keyword);


    @SelectProvider(type = WsRmpWarehouseStockSumSQL.class, method = "getMskuSku")
    List<String> getMskuSku(@Param("date") String date, @Param("keyword") String keyword, @Param("shopName") String shopName);


    @Select("select sku_id, fba_transport_qty, fba_stock_qty, msku_id, shop_id, shop_name from v_fba_total where msku_id=#{mskuId} and shop_id=#{shopId} and fn_sku_id=#{fnSkuId}")
    MskuFbaVO getMskuFba(@Param("mskuId") String mskuId, @Param("shopId") String shopId, @Param("fnSkuId") String fnSkuId);
}
