package com.wisrc.order.webapp.dao.sql;

import com.wisrc.order.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class SaleOrderInfoSql {
    public static String findByCond(
            @Param("orderId") String orderId,
            @Param("originalOrderId") String originalOrderId,
            @Param("platId") String platId,
            @Param("shopId") String shopId,
            @Param("createTime") Date createTime,
            @Param("exceptTypeCd") String exceptTypeCd,
            @Param("statusCd") int statusCd,
            @Param("comIds") String comIds,
            @Param("countryCd") String countryCd
    ) {
        return new SQL() {{
            SELECT("distinct order_id, original_order_id, status_cd, plat_id, shop_id, amount_money, customer_id, country_cd, offer_id, create_time, delivery_remark, delete_status");
            FROM("v_order_list");
            if (statusCd != 0) {
                WHERE("status_Cd=#{statusCd}");
            }
            if (orderId != null) {
                WHERE("order_id like concat('%',#{orderId},'%')");
            }
            if (originalOrderId != null) {
                WHERE("original_order_id like concat('%',#{originalOrderId},'%')");
            }
            if (null != platId) {
                WHERE("plat_id =#{platId}");
            }
            if (null != shopId) {
                WHERE("shop_id =#{shopId}");
            }
            if (null != createTime) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(createTime);
                cal.add(Calendar.DAY_OF_MONTH, +1);
                Date end = new Date(cal.getTime().getTime());
                WHERE("create_time >= #{createTime}");
                WHERE("create_time < '" + end + "'");
            }
            if (exceptTypeCd != null) {
                WHERE("find_in_set(#{exceptTypeCd}, except_type_cd)");
            }
            if (comIds != null) {
                WHERE("commodity_id in " + "(" + comIds + ")");
            }
            if (countryCd != null && !countryCd.isEmpty()) {
                WHERE("country_cd = #{countryCd}");
            }
            ORDER_BY("create_time desc");
        }}.toString();
    }


    public static String turnWaitHandle(@Param("orderIdList") List orderIdList,
                                        @Param("statusCd") int statusCd) {
        return SQLUtil.forUtil("update sale_order_info set status_cd = " + statusCd + " where order_id", orderIdList);
    }
}
