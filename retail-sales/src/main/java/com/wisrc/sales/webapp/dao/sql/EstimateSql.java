package com.wisrc.sales.webapp.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Timestamp;

public class EstimateSql {
    public static String getByCond(@Param("shopId") final String shopId,
                                   @Param("mskuId") final String mskuId,
                                   @Param("comodityIds") final String comodityIds,
                                   @Param("asOfDate") Timestamp asOfDate
    ) {
        return new SQL() {{
            SELECT("estimate_id, commodity_id, shop_id, msku_id, create_user, create_time, modify_user, modify_time, charge_employee_id, update_employee_id,shop_seller_id");
            FROM("sales_estimate");
            if (shopId != null) {
                WHERE("shop_id = #{shopId}");
            }
            if (mskuId != null) {
                WHERE("msku_id like concat('%',#{mskuId},'%')");
            }
            if (comodityIds != null) {
                WHERE("commodity_id in " + "(" + comodityIds + ")");
            }
            WHERE("effective_date <= #{asOfDate}");
            WHERE("expiration_date > #{asOfDate}");
            WHERE("update_flag = 1");
            ORDER_BY("convert(modify_time,datetime) desc");
        }}.toString();
    }

    public static String getAllDetailByUUids(@Param("uuids") final String uuids
    ) {
        return new SQL() {{
            SELECT("uuid, estimate_id, estimate_date, estimate_number");
            FROM("sales_estimate_detail_info");
            if (StringUtils.isNotEmpty(uuids)) {
                WHERE("uuid in " + "(" + uuids + ")");
            }
        }}.toString();
    }

    public static String getTotalNum(@Param("commodityId") final String commodityId,
                                     @Param("startTime") final String startTime,
                                     @Param("endTime") final String endTime
    ) {
        return new SQL() {{
            SELECT("sum(estimate_number)");
            FROM("v_sale_estimate_info");
            if (commodityId != null) {
                WHERE("commodity_id = #{commodityId}");
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
            WHERE("update_flag = 1");
        }}.toString();
    }


}