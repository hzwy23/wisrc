package com.wisrc.order.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ExceptOrderSql {
    public static String getExceptOrderByCond(@Param("orderId") String orderId,
                                              @Param("originalOrderId") String originalOrderId,
                                              @Param("platId") String platId,
                                              @Param("shopId") String shopId,
                                              @Param("commodityId") String commodityId,
                                              @Param("commodityName") String commodityName,
                                              @Param("createStartTime") String createStartTime,
                                              @Param("createEndTime") String createEndTime,
                                              @Param("label") String label) {
        return new SQL() {{
            SELECT("order_id, original_order_id, trade_number, status_cd, plat_id, shop_id, pay_status_cd, pay_type_cd, iso_currency_cd, original_currency_cd, amount_money, " +
                    "amount_money_currency, freight, freight_currency, return_amount, return_amount_currency, insurance_amount, insurance_amount_currency, platform_freight, platform_freight_currency, " +
                    " transfer_expense, transfer_expense_currency, other_expenses, other_expenses_currency, receipt_account, payment_date, manual_creation, create_user, create_time, modify_user, " +
                    "modify_time, delete_status");
            FROM("order_basic_info");
            WHERE("status_cd=1");
            if (orderId != null) {
                WHERE("order_id like concat('%',#{orderId},'%')");
            }
            if (originalOrderId != null) {
                WHERE("original_order_id like concat('%',#{originalOrderId},'%')");
            }
            if (platId != null) {
                WHERE("plat_id = #{platId}");
            }
            if (shopId != null) {
                WHERE("shop_id = #{shopId}");
            }
            /*if (commodityId != null) {
                WHERE("shop_id = #{commodityId}");
            }*/
            if (createStartTime != null) {
                WHERE("create_time >= #{createStartTime}");
            }
            if (createEndTime != null) {
                WHERE("create_time <= #{createEndTime}");
            }
            ORDER_BY("create_time desc");
        }}.toString();
    }
}
