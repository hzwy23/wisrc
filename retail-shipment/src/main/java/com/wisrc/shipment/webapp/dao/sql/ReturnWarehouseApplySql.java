package com.wisrc.shipment.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ReturnWarehouseApplySql {
    public static String findBycond(@Param("shopId") final String shopId,
                                    @Param("employeeId") final String employeeId,
                                    @Param("statusCd") final int statusCd,
                                    @Param("keyword") final String keyword,
                                    @Param("comodityIds") final String comodityIds,
                                    @Param("startTime") final String startTime,
                                    @Param("productName") final String productName,
                                    @Param("comodityNewIds") final String comodityNewIds,
                                    @Param("endTime") final String endTime
    ) {
        return new SQL() {{
            SELECT("return_apply_id, shop_id, remark, status_cd, create_user, create_time, update_user, update_time, commodity_id, reason, employee_id");
            FROM("v_return_basis_detail_order");
            if (shopId != null) {
                WHERE("shop_id = #{shopId}");
            }
            if (employeeId != null) {
                WHERE("employee_id = #{employeeId}");
            }
            if (statusCd != 0) {
                WHERE("status_cd = #{statusCd}");
            }
            if (keyword != null) {
                String append = " ";
                if (comodityIds != null) {
                    append = " or commodity_id in " + "(" + comodityIds + ")";
                }
                WHERE("( return_apply_id like concat('%',#{keyword},'%') " +
                        " or remove_order_id like concat('%',#{keyword},'%') " +
                        " or  msku_id like concat('%',#{keyword},'%') " + append + ")");
            }
            if (startTime != null) {
                WHERE("create_time >= #{startTime}");
            }
            if (endTime != null) {
                WHERE("create_time <= #{endTime}");
            }
            if (productName != null) {
                WHERE("commodity_id in (" + comodityNewIds + ") ");
            }
            GROUP_BY("return_apply_id");
            ORDER_BY("create_time desc ");
        }}.toString();
    }
}
