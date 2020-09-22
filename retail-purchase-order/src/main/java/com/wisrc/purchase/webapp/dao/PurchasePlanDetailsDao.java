package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.PurchasePlanDetailsSql;
import com.wisrc.purchase.webapp.entity.PurchasePlanDetailsEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PurchasePlanDetailsDao {
    @Select("SELECT plan_date, expect_sales, avg_sales, available_stock, assign_balance, min_stock, minimum, suggest_purchase, last_purchase_date FROM purchase_plan_details " +
            "WHERE uuid = #{uuid} ORDER BY plan_date ASC")
    List<PurchasePlanDetailsEntity> getPurchasePlanDetail(@Param("uuid") String uuid);

    @Insert("INSERT INTO purchase_plan_details(id, uuid, plan_date, expect_sales, avg_sales, available_stock, assign_balance, min_stock, minimum, suggest_purchase, last_purchase_date) VALUES(" +
            "#{id}, #{uuid}, #{planDate}, #{expectSales}, #{avgSales}, #{availableStock}, #{assignBalance}, #{minStock}, #{minimum}, #{suggestPurchase}, #{lastPurchaseDate})")
    void savePurchasePlanDetails(PurchasePlanDetailsEntity purchasePlanDetailsEntity);

    @SelectProvider(type = PurchasePlanDetailsSql.class, method = "deletePurchasePlanDetails")
    void deletePurchasePlanDetails(@Param("uuids") List uuids);
}
