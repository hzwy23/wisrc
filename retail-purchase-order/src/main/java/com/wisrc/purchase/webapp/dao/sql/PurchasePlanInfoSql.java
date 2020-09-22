package com.wisrc.purchase.webapp.dao.sql;

import com.wisrc.purchase.webapp.query.GetEstimateNumQuery;
import com.wisrc.purchase.webapp.query.PurchasePlanPageQuery;
import com.wisrc.purchase.webapp.query.SkuEstimateDateQuery;
import com.wisrc.purchase.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class PurchasePlanInfoSql {
    public static String getRecentDelivery(@Param("skuIds") List skuIds) {
        return new SQL() {{
            SELECT("DISTINCT sku_id", "supplier_id", "general_delivery", "haulage_days", "minimum");
            FROM("v_sku_supplier_delivery AS a");
            WHERE(SQLUtil.forUtil("sku_id", skuIds));
            WHERE("NOT EXISTS (SELECT 1 FROM v_sku_supplier_delivery AS b WHERE a.create_time < b.create_time AND a.sku_id = b.sku_id )");
        }}.toString();
    }

    public static String getEstimateNum(GetEstimateNumQuery getEstimateNum) {
        return new SQL() {{
            SELECT("sku_id", "estimate_date", "SUM(estimate_number) AS estimate_number");
            FROM("v_sku_sum_sales");
            WHERE("estimate_date >= #{today}");
            WHERE(SQLUtil.forUtil("sales_status_cd", getEstimateNum.getSalesStatus()));
            String skuDateSql = "(";
            for (int m = 0; m < getEstimateNum.getSkuDate().size(); m++) {
                SkuEstimateDateQuery skuDate = getEstimateNum.getSkuDate().get(m);
                if (m != 0) {
                    skuDateSql += " OR ";
                }
                skuDateSql += (" (sku_id = '" + skuDate.getSkuId() + "' AND estimate_date <= '" + skuDate.getEstimateDate() + "')");
            }
            skuDateSql += ")";
            WHERE(skuDateSql);
            GROUP_BY("sku_id", "estimate_date");
            ORDER_BY("sku_id", "estimate_date");
        }}.toString();
    }

    public static String purchasePlanPage(PurchasePlanPageQuery purchasePlanPageQuery) {
        return new SQL() {{
            SELECT("uuid", "sku_id", "calculate_date", "end_sales_date", "recommend_purchase", "suggest_date", "start_out_stock", "expect_in_warehouse", "available_stock",
                    "sum_sales", "avg_sales", "min_stock", "general_delivery", "haulage_days", "international_transport_days", "safety_stock_days", "minimum", "supplier_id",
                    "status_desc", "purchase_id", "order_id", "ppi.status_cd", "remark");
            FROM("purchase_plan_info AS ppi");
            LEFT_OUTER_JOIN("plan_status_attr AS psa ON psa.status_cd = ppi.status_cd");
            if (purchasePlanPageQuery.getLastPurchaseStartDate() != null) {
                WHERE("suggest_date >= #{lastPurchaseStartDate}");
            }
            if (purchasePlanPageQuery.getLastPurchaseEndDate() != null) {
                WHERE("suggest_date <= #{lastPurchaseEndDate}");
            }
            if (purchasePlanPageQuery.getSkuId() != null) {
                WHERE("sku_id = #{skuId}");
            }
            if (purchasePlanPageQuery.getStatusCd() != null) {
                WHERE("ppi.status_cd = #{statusCd}");
            }
            if (purchasePlanPageQuery.getCalculateDateStart() != null) {
                WHERE("calculate_date >= #{calculateDateStart}");
            }
            if (purchasePlanPageQuery.getCalculateDateEnd() != null) {
                WHERE("calculate_date <= #{calculateDateEnd}");
            }
            if (purchasePlanPageQuery.getSupplierId() != null) {
                WHERE("ppi.supplier_id = #{supplierId}");
            }
            if (purchasePlanPageQuery.getSortKey() != null) {
                ORDER_BY(purchasePlanPageQuery.getSortKey() + " " + purchasePlanPageQuery.getSort());
            }
        }}.toString();
    }

    public static String deletePlanByUuid(@Param("uuids") List uuids) {
        return new SQL() {{
            DELETE_FROM("purchase_plan_info");
            WHERE(SQLUtil.forUtil("uuid", uuids));
        }}.toString();
    }
}
