package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.PurchasePlanInfoSql;
import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.query.GetEstimateNumQuery;
import com.wisrc.purchase.webapp.query.PurchasePlanPageQuery;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface PurchasePlanInfoDao {
    @SelectProvider(type = PurchasePlanInfoSql.class, method = "getRecentDelivery")
    List<GetGeneralDelivery> getRecentDelivery(@Param("skuIds") List skuIds);

    @SelectProvider(type = PurchasePlanInfoSql.class, method = "getEstimateNum")
    List<GetEstimateNum> getEstimateNum(GetEstimateNumQuery getEstimateNum);

    @SelectProvider(type = PurchasePlanInfoSql.class, method = "purchasePlanPage")
    List<PurchasePlanPage> purchasePlanPage(PurchasePlanPageQuery purchasePlanPageQuery);

    @Select("SELECT uuid, sku_id, calculate_date, plan_day, stock_cycle, general_delivery, safety_stock_days, haulage_days, international_transport_days, sum_sales, status_cd, " +
            "suggest_count, suggest_date, calculate_type_desc, calculate_time, modify_user FROM purchase_plan_info AS ppi LEFT JOIN calculate_type_attr AS cta ON " +
            "cta.calculate_type_cd = ppi.calculate_type_cd  WHERE uuid = #{uuid} ")
    PurchasePlanPage getPurchasePlan(@Param("uuid") String uuid);

    @Update("UPDATE purchase_plan_info SET status_cd = 2, purchase_id = #{newId} WHERE uuid = #{uuid}")
    void decidePlan(@Param("uuid") String uuid, @Param("newId") String newId);

    @Update("<script>"
            + "UPDATE purchase_plan_info SET status_cd = 3 WHERE "
            + "<foreach item='uuid' index='index' collection='uuids' open=' uuid IN (' separator=',' close=')'>"
            + "#{uuid}"
            + "</foreach>"
            + "</script>"
    )
    void cancelPlan(@Param("uuids") List uuids);

    @Select("<script>"
            + "SELECT status_cd FROM purchase_plan_info WHERE "
            + "<foreach item='uuid' index='index' collection='uuids' open=' uuid IN (' separator=',' close=')'>"
            + "#{uuid}"
            + "</foreach>"
            + "</script>"
    )
    List<Integer> getStatus(@Param("uuids") List uuids);

    @Insert("INSERT INTO purchase_plan_info(uuid, sku_id, calculate_date, plan_day, stock_cycle, end_sales_date, recommend_purchase, last_purchase_date, start_out_stock, " +
            "expect_in_warehouse, available_stock, sum_sales, avg_sales, min_stock, general_delivery, haulage_days, international_transport_days, safety_stock_days, minimum, supplier_id, " +
            "status_cd, purchase_id, order_id, suggest_count, suggest_date, calculate_type_cd, calculate_time, modify_user) VALUES(#{uuid}, #{skuId}, #{calculateDate}, #{planDay}, " +
            "#{stockCycle}, #{endSalesDate}, #{recommendPurchase}, #{lastPurchaseDate}, #{startOutStock}, #{expectInWarehouse}, #{availableStock}, #{sumSales}, #{avgSales}, #{minStock}, " +
            "#{generalDelivery}, #{haulageDays}, #{internationalTransportDays}, #{safetyStockDays}, #{minimum}, #{supplierId}, #{statusCd}, #{purchaseId}, #{orderId}, #{suggestCount}, #{suggestDate}, " +
            "#{calculateTypeCd}, #{calculateTime}, #{modifyUser}) ")
    void savePurchasePlan(PurchasePlanInfoEntity purchasePlanInfoEntity);

    @Select("SELECT MAX(RIGHT(purchase_id, 5)) FROM purchase_plan_info")
    Integer getMaxPurchaseId();

    @Update("UPDATE purchase_plan_info SET status_cd = 0 WHERE sku_id = #{skuId} AND status_cd = 1 AND calculate_date < #{today}")
    void editOverdue(@Param("skuId") String skuId, @Param("today") Date today);

    @Select("SELECT COUNT(*) FROM purchase_plan_info")
    Integer getDataSize();

    @Select("SELECT stock_cycle, international_transport_days, safety_stock_days, end_sales_date FROM purchase_plan_info WHERE uuid = #{uuid}")
    PurchasePlanInfoEntity getPlanTime(@Param("uuid") String uuid);

    @Update("UPDATE purchase_plan_info SET stock_cycle = #{stockCycle}, international_transport_days = #{internationalTransportDays}, safety_stock_days = #{safetyStockDays}, " +
            "end_sales_date = #{endSalesDate} WHERE uuid = #{uuid}")
    void editPlanTime(PurchasePlanInfoEntity purchasePlanInfo);

    @Delete("DELETE FROM purchase_plan_info WHERE uuid = #{uuid}")
    void deletePurchasePlan(@Param("uuid") String uuid);

    @Insert("INSERT INTO purchase_plan_remark(remark_id, uuid, remark_desc, create_user, create_time) VALUES(#{remarkId}, #{uuid}, #{remarkDesc}, #{createUser}, #{createTime})")
    void saveRemark(PurchasePlanRemarkEntity purchasePlanRemarkEntity);

    @SelectProvider(type = PurchasePlanInfoSql.class, method = "deletePlanByUuid")
    void deletePlanByUuid(@Param("uuids") List uuids);

    @Select("SELECT uuid FROM purchase_plan_info WHERE calculate_date = #{calculateDate}")
    List<String> getIdByDate(@Param("calculateDate") Date calculateDate);

    @Update("update purchase_plan_info set status_cd = 3, order_id = #{orderId} where purchase_id = #{purchaseId}")
    void updateOrderId(@Param("orderId") String orderId,
                       @Param("purchaseId") String purchaseId);
}
