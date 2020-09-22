package com.wisrc.sales.webapp.dao.sql;

import com.wisrc.sales.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class SalePlanCycleDetailsQuery {
    public static String deleteDetail(@Param("salePlanId") String salePlanId, @Param("planDates") List planDates) {
        return new SQL() {{
            DELETE_FROM("sale_plan_cycle_details");
            WHERE("sale_plan_id = #{salePlanId}");
            WHERE(SQLUtil.forUtil("plan_date", planDates));
        }}.toString();
    }
}
