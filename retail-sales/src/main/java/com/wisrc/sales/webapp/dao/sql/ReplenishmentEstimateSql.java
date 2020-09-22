package com.wisrc.sales.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ReplenishmentEstimateSql {
    public static String getNum(@Param("shopId") String shopId,
                                @Param("mskuId") String mskuId,
                                @Param("startTime") String startTime,
                                @Param("endTime") String endTime) {
        return new SQL() {{
            SELECT("ifnull(sum(estimate_number),0)");
            FROM("v_sale_estimate_info");
            if (shopId != null) {
                WHERE("shop_id = #{shopId}");
            }
            if (mskuId != null) {
                WHERE("msku_id = #{mskuId}");
            }
            if (startTime != null) {
                WHERE("estimate_date >= #{startTime}");
            }
            if (endTime != null) {
                WHERE("estimate_date <= #{endTime}");
            }
            WHERE("sales_estimate_update_flag = 1 and sales_estimate_detail_info_update_flag = 1");
        }}.toString();
    }

    public static String getEstimateDetailByEstimateIdAndCond(@Param("estimateId") final String estimateId,
                                                              @Param("startTime") final String startTime,
                                                              @Param("endTime") final String endTime
    ) {
        return new SQL() {{
            SELECT("uuid, estimate_id, estimate_date, estimate_number");
            FROM("sales_estimate_detail_info");
            if (estimateId != null) {
                WHERE("estimate_id = #{estimateId}");
            }
            if (startTime != null) {
                WHERE("estimate_date >= #{startTime}");
            }
            if (endTime != null) {
                WHERE("estimate_date <= #{endTime}");
            }
            ORDER_BY("estimate_date asc");
            WHERE(" update_flag = 1");
        }}.toString();
    }
}
