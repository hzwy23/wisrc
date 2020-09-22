package com.wisrc.sales.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;

public class SalePlanInfoSql {
    public static String findByCond(@Param("shopId") String shopId,
                                    @Param("msku") String msku,
                                    @Param("commodityIdListStr") String commodityIdListStr) {
        return new SQL() {{
            SELECT("sale_plan_id, director_employee_id, charge_employee_id, commodity_id, shop_id, msku_id, create_user, create_time, modify_user, modify_time");
            FROM("sale_plan_info");
            if (shopId != null) {
                WHERE("shop_id = #{shopId}");
            }
            if (msku != null) {
                WHERE("msku_id like concat('%',#{msku},'%')");
            }
            if (commodityIdListStr != null && !commodityIdListStr.isEmpty()) {
                WHERE("commodity_id in " + commodityIdListStr);
            }
            ORDER_BY("modify_time desc");
        }}.toString();
    }

    public static String getAllMsku(@Param("commodityIdList") String commodityIdList) {
        return new SQL() {{
            SELECT("sale_plan_id, director_employee_id, charge_employee_id, commodity_id, shop_id, msku_id, create_user, create_time, modify_user, modify_time");
            FROM("sale_plan_info");
            if (commodityIdList != null && !commodityIdList.isEmpty()) {
                WHERE("commodity_id in " + commodityIdList);
            }
            ORDER_BY("modify_time desc");
        }}.toString();
    }

    public static String getTotal(@Param("shopId") String shopId,
                                  @Param("msku") String msku,
                                  @Param("startMonth") String startMonth,
                                  @Param("endMonth") String endMonth,
                                  @Param("commodityIdListStr") String commodityIdListStr) {
        return new SQL() {{
            SELECT("uuid, weight, plan_date, sale_cycle, cost_price, sale_price, day_sale_num, sale_time, sale_amount, estimate_refundable_rate, commission_coefficient, commission, FulfillmentCost, marketing_cost, marketing_cost_ratio, test_cost, test_cost_ratio, advertisement_cost, advertisement_cost_ratio, coupon_cost, coupon_cost_ratio, deal_cost, deal_cost_ratio, outside_promotion_cost, outside_promotion_cost_ratio, first_unit_price, first_freight, breakage_cost, real_sale_amount, total_cost, gross_profit, gross_rate, day_gross_rate, sale_plan_id, director_employee_id, charge_employee_id, commodity_id, shop_id, msku_id,modify_time");
            FROM("v_sale_plan_info_detail");
            WHERE("sale_cycle=999");
            if (shopId != null) {
                WHERE("shop_id = #{shopId}");
            }
            if (msku != null) {
                WHERE("msku_id like concat('%',#{msku},'%')");
            }
            if (startMonth != null) {
                WHERE("plan_date >= #{startMonth}");
            }
            if (endMonth != null) {
                WHERE("plan_date <= #{endMonth}");
            }
            if (commodityIdListStr != null && !commodityIdListStr.isEmpty()) {
                WHERE("commodity_id in " + commodityIdListStr);
            }
            ORDER_BY("modify_time desc");
        }}.toString();
    }
}
