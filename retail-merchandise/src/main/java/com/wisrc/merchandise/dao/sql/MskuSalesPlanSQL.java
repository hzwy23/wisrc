package com.wisrc.merchandise.dao.sql;

import com.wisrc.merchandise.query.DistinctSalesDefineQuery;
import com.wisrc.merchandise.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class MskuSalesPlanSQL {
    private static final String remove = "3";

    public static String distinctSalesDefine(DistinctSalesDefineQuery distinctSalesDefineQuery) {
        return new SQL() {{
            SELECT("DISTINCT msd.id");
            FROM("msku_sales_define AS msd");
            LEFT_OUTER_JOIN("msku_ext_info AS mei ON mei.id = msd.id", "msku_info AS mi ON mi.id = msd.id");
            WHERE("msd.msku_status_cd <> " + remove);
            if (null != distinctSalesDefineQuery.getShopId()) {
                WHERE("shop_id = #{shopId}");
            }
            if (distinctSalesDefineQuery.getManager() != null) {
                WHERE("group_id = #{groupId}");
            }
            if (distinctSalesDefineQuery.getGroupId() != null) {
                WHERE("user_id = #{manager}");
            }
            if (null != distinctSalesDefineQuery.getFindKey()) {
                String findKey = "(msku_id LIKE concat('%', #{findKey}, '%') OR msku_name LIKE concat('%', #{findKey}, '%') OR mei.asin LIKE concat('%', #{findKey}, '%')";
                if (null != distinctSalesDefineQuery.getStoreSkuDealted() && distinctSalesDefineQuery.getStoreSkuDealted().size() > 0) {
                    findKey += (" OR " + SQLUtil.forUtil("sku_id", distinctSalesDefineQuery.getStoreSkuDealted()));
                }
                findKey += ")";
                WHERE(findKey);
            }
        }}.toString();
    }

    public static String getSalesPlanByIds(@Param("ids") List<String> ids) {
        return new SQL() {{
            SELECT("msp.plan_id", "sales_status_cd", "expected_daily_sales", "guide_price", "start_date", "expiry_date", "msd.id");
            FROM("msku_sales_plan AS msp");
            LEFT_OUTER_JOIN("msku_sales_define AS msd ON msd.plan_id = msp.plan_id");
            WHERE(SQLUtil.forUtil("msd.id", ids));
        }}.toString();
    }
}
