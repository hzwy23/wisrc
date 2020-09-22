package com.wisrc.merchandise.dao;

import com.wisrc.merchandise.dao.sql.MskuSQL;
import com.wisrc.merchandise.dto.msku.SalesStatusAutoDto;
import com.wisrc.merchandise.entity.*;
import com.wisrc.merchandise.query.*;
import com.wisrc.merchandise.vo.wmsMskuInfoVO;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface MskuInfoDao {
    int using = 1;

    @Select("select id, shop_id, msku_id from msku_info")
    List<MskuInfoEntity> findAll();

    // 根据搜索条件查询商品列表
    @SelectProvider(type = MskuSQL.class, method = "getMskuList")
    List<MskuPageEntity> getMskuList(GetMskuListQuery getMskuListQuery) throws Exception;

    // 添加商品信息
    @Insert("INSERT INTO msku_info (id, msku_id, shop_id, msku_name, sku_id, parent_asin, user_Id, sales_status_cd, update_time, msku_status_cd,commission, msku_status_edit_cd) " +
            "VALUES (#{id}, #{mskuId}, #{shopId}, #{mskuName}, #{skuId}, #{parentAsin}, #{userId}, #{salesStatusCd} ,#{updateTime}, #{mskuStatusCd},#{commission}, #{mskuStatusEditCd})")
    void saveMsku(MskuInfoEntity mskuInfoEntity) throws Exception;

    // 根据id查询商品信息
    @Select("SELECT mi.*, mei.asin, mei.fn_sku_id, mei.delivery_mode, mie.epitaph, bsi.shop_name, mdma.delivery_mode_desc " +
            " FROM msku_info AS mi LEFT JOIN msku_ext_info AS mei ON mei.id = mi.id LEFT JOIN msku_info_epitaph AS mie ON mie.id = mi.id LEFT JOIN basic_shop_info AS bsi ON bsi.shop_id = mi.shop_id" +
            " LEFT JOIN msku_delivery_mode_attr AS mdma ON mdma.delivery_mode = mei.delivery_mode" +
            " WHERE BINARY mi.id = #{id}")
    MskuPageEntity getMsku(@Param("id") String id) throws Exception;

    // 编辑商品信息
    @Update("UPDATE msku_info SET sku_id = #{skuId}, parent_asin = #{parentAsin}, msku_name = #{mskuName}, user_Id = #{userId}, " +
            "sales_status_cd = #{salesStatusCd}, update_time = #{updateTime},commission = #{commission} WHERE id = #{id}")
    void editMsku(MskuInfoEntity mskuInfoEntity) throws Exception;

    // 停用商品
    @Update("UPDATE msku_info SET msku_status_cd = #{mskuStatusCd} WHERE id = #{id}")
    void mskuSwitch(MskuInfoEntity mskuInfoEntity) throws Exception;

    // 根据id数组查询商品信息
    @Select("<script>"
            + "SELECT mi.*, mei.asin, mei.fn_sku_id, mei.shelf_date, mei.delivery_mode, delivery_mode_desc, bsi.shop_name, sales_status_cd FROM msku_info AS mi LEFT JOIN msku_ext_info AS mei ON mei.id = mi.id " +
            "LEFT JOIN basic_shop_info AS bsi ON bsi.shop_id = mi.shop_id LEFT JOIN msku_delivery_mode_attr AS mdma ON mdma.delivery_mode = mei.delivery_mode WHERE 1=1 " +
            "<foreach item='id' index='index' collection='ids' open=' AND mi.id IN (' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>"
            + "</script>"
    )
    List<MskuPageEntity> getMskuByPlan(@Param("ids") List<String> ids) throws Exception;

    // 批量停用商品
    @Update("<script>"
            + "UPDATE msku_info SET msku_status_cd = 2 WHERE id IN"
            + "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    void mskuAllDown(@Param("ids") List ids) throws Exception;

    // 变更负责人
    @Update("<script>"
            + "UPDATE msku_info SET user_Id = #{manager} WHERE id IN "
            + "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    void changeManager(@Param("manager") String manager,
                       @Param("ids") List ids
    ) throws Exception;

    // 外部获取商品基础信息
    @SelectProvider(type = MskuSQL.class, method = "getMskuListOutside")
    List<MskuPageEntity> getMskuListOutside(MskuPageOutsideQuery mskuPageOutsideQuery) throws Exception;

    @SelectProvider(type = MskuSQL.class, method = "getMskuId")
    List<String> getMskuId(@Param("shopName") String shopName, @Param("mskuId") String mskuId, @Param("mskuPrivilege") List mskuPrivilege, @Param("user") String user) throws Exception;

    @SelectProvider(type = MskuSQL.class, method = "getIdByNumAndShop")
    List<GetIdByNumAndShop> getIdByNumAndShop(GetIdByNumAndShopQuery getIdByNumAndShopQuery);

    @SelectProvider(type = MskuSQL.class, method = "getIdByMskuIdAndName")
    List<String> getIdByMskuIdAndName(GetIdByMskuIdAndNameQuery getIdByMskuIdAndNameQuery) throws Exception;

    @SelectProvider(type = MskuSQL.class, method = "mskuSearch")
    List<mskuRelationEntity> mskuSearch(MskuSearchQuery mskuSearchQuery) throws Exception;

    @Select("SELECT mi.id, msku_id, shop_id, msku_name, sku_id, sales_status_cd, mei.fn_sku_id FROM msku_info AS mi LEFT JOIN msku_ext_info AS mei ON mei.id = mi.id " +
            "WHERE mei.fn_sku_id = #{fnsku} ")
    List<GetMskuEntity> mskuFnsku(@Param("fnsku") String fnsku) throws Exception;

    @Select("SELECT mi.id, msku_id, shop_id, msku_name, sku_id, sales_status_cd, mei.fn_sku_id FROM msku_info AS mi LEFT JOIN msku_ext_info AS mei ON mei.id = mi.id " +
            "WHERE mei.fn_sku_id like concat('%',#{fnsku},'%') ")
    List<GetMskuEntity> mskuFnskuLike(@Param("fnsku") String fnsku);

    @Select("<script>"
            + "SELECT sku_id FROM msku_info WHERE msku_status_cd = " + using + " "
            + "<foreach item='saleStatus' index='index' collection='saleStatusList' open=' AND sales_status_cd IN (' separator=',' close=')'>"
            + "#{saleStatus}"
            + "</foreach>"
            + "</script>"
    )
    List<String> getSkuIdBySalesStatus(@Param("saleStatusList") List saleStatusList);

    @Insert("  INSERT INTO msku_stock_sales_info\n" +
            " (\n" +
            "   id,\n" +
            "   shop_id,\n" +
            "   msku_id,\n" +
            "   fba_on_warehouse_stock_num,\n" +
            "   fba_on_way_stock_num,\n" +
            "   yesterday_sales_num,\n" +
            "   day_before_yesterday_sales_num,\n" +
            "   previous_sales_num\n" +
            " )\n" +
            " VALUES\n" +
            "  (\n" +
            "    #{id},\n" +
            "    #{shopId},\n" +
            "    #{mskuId},\n" +
            "    #{fbaOnWarehouseStockNum},\n" +
            "    #{fbaOnWayStockNum},\n" +
            "    #{yesterdaySalesNum},\n" +
            "    #{dayBeforeYesterdaySalesNum},\n" +
            "    #{previousSalesNum}\n" +
            "  ) ")
    void saveMskuStockSalesInfo(MskuStockSalesInfoEntity mskuStockSalesInfoEntity);

    @Update(" UPDATE msku_stock_sales_info\n" +
            " SET\n" +
            "  fba_on_warehouse_stock_num     = #{fbaOnWarehouseStockNum},\n" +
            "  fba_on_way_stock_num           = #{fbaOnWayStockNum},\n" +
            "  yesterday_sales_num            = #{yesterdaySalesNum},\n" +
            "  day_before_yesterday_sales_num = #{dayBeforeYesterdaySalesNum},\n" +
            "  previous_sales_num             = #{previousSalesNum}\n" +
            " WHERE\n" +
            "  shop_id = #{shopId}\n" +
            "  and msku_id = #{mskuId} ")
    void updateSalesStock(MskuStockSalesInfoEntity entity);

    @SelectProvider(type = MskuSQL.class, method = "getStocks")
    List<MskuStockSalesInfoEntity> getMSSIEListById(@Param("ids") String ids);

    @Select(" SELECT\n" +
            "  id,\n" +
            "  shop_id,\n" +
            "  msku_id,\n" +
            "  fba_on_warehouse_stock_num,\n" +
            "  fba_on_way_stock_num,\n" +
            "  yesterday_sales_num,\n" +
            "  day_before_yesterday_sales_num,\n" +
            "  previous_sales_num\n" +
            " FROM msku_stock_sales_info\n" +
            " WHERE id = #{ids} ")
    MskuStockSalesInfoEntity getMSSIE(String id);

    @Update("update msku_stock_sales_info set yesterday_sales_num=#{lastNum},day_before_yesterday_sales_num=#{lastTwoNum},previous_sales_num=#{lastThreeNum},update_date=#{modifyTime} where shop_id=#{shopId} and msku_id=#{msku}")
    void updateShipmentStock(MskuSaleNumEnity shipmentStockEnity);

    @Select("SELECT mi.id, mi.msku_id, mi.shop_id, sales_status_cd, update_time, store_in_time, fba_on_warehouse_stock_num, market_place_id, asin  FROM msku_info AS mi LEFT JOIN " +
            "msku_ext_info AS mei ON mei.id = mi.id LEFT JOIN msku_stock_sales_info AS mssi ON mssi.id = mi.id LEFT JOIN basic_shop_info AS bsi ON bsi.shop_id = mi.shop_id LEFT " +
            "JOIN basic_platform_info AS bpi ON bpi.plat_id = bsi.plat_id WHERE msku_status_edit_cd = 0 AND sales_status_cd <> 5 AND sales_status_cd <> 6")
    List<GetMskuEditSales> getMskuAutoSales();

    @Update("UPDATE msku_info SET sales_status_cd = #{salesStatusCd} WHERE id = #{id}")
    void editSalesStatus(SalesStatusAutoDto salesStatusAutoDto);

    @SelectProvider(type = MskuSQL.class, method = "getByKeywordQuery")
    List<mskuRelationEntity> getByKeyword(GetByKeywordQuery getByKeywordQuery);

    @SelectProvider(type = MskuSQL.class, method = "getMskuFBA")
    List<GetMskuFBA> getMskuFBA(GetMskuFBAQuery getMskuFBAQuery);

    @Update("update msku_stock_sales_info set fba_on_warehouse_stock_num=#{stockTotalQuantity},update_date=#{modifyTime} where id=#{id} ")
    void updateFbaStockDetail(@Param("shopId") String shopId, @Param("id") String id, @Param("stockTotalQuantity") int stockTotalQuantity, @Param("useableQuantity") int useableQuantity, @Param("unableQuantity") int unableQuantity, @Param("modifyTime") Date modifyTime);

    @Select("select asin from msku_ext_info where shelf_date is null")
    List<String> getUnShelve();

    @Update("update msku_ext_info set shelf_date=#{shelfDate} where asin=#{asin}")
    void updateShelveInfo(@Param("asin") String asin, @Param("shelfDate") String shelfDate);

    @Update("UPDATE msku_info SET store_in_time = #{storeInTime} WHERE id = #{id}")
    void editStoreInTime(SalesStatusAutoDto auto);

    @Update("UPDATE msku_info SET msku_status_edit_cd = #{mskuStatusEditCd} WHERE id = #{id}")
    void editEditCd(MskuInfoEntity mskuInfoEntity);

    @Update("UPDATE msku_info SET safety_stock_days = #{safetyStockDays} WHERE id = #{id} ")
    void editSafetyDay(MskuInfoEntity mskuInfo);

    @SelectProvider(type = MskuSQL.class, method = "batchGetMskuInfo")
    List<wmsMskuInfoVO> findMskuInfoBatch(@Param("commodityIds") List<String> commodityIds);

    @Select("select plat_id as platId,shop_id as shopId from v_shop_details_info where shop_owner_id=#{sellerId}")
    Map<String, String> getPaltId(String sellerId);

    @Select("select fn_sku_id from msku_ext_info where id=#{commodityId} ")
    String getFnsku(String commodityId);

    @Select("select commodity_id as commodityId, sku_id as skuId from v_msku_info where msku_id=#{msku} and shop_id=#{shopId}")
    Map<String, String> getCommodity(@Param("msku") String msku, @Param("shopId") String shopId);

    @Select("select commodity_id as commodityId from v_msku_info where msku_id=#{mskuId} and shop_id=#{shopId}")
    String getCommodityId(@Param("mskuId") String mskuId, @Param("shopId") String shopId);

    @SelectProvider(type = MskuSQL.class, method = "getMskuByEmployee")
    List<String> getMskuByEmployee (@Param("userId") String userId);

    @Select("select id from msku_ext_info where fn_sku_id=#{fnsku} limit 0,1")
    String getByFnsku(String fnsku);

    @Select("select commodity_id as commodityId, sku_id as skuId from v_msku_info where commodity_id=#{commodityId}")
    Map<String,String> getCommodityById(String commodityId);
}
