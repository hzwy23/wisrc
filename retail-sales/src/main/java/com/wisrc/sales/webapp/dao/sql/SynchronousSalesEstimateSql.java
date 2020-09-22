package com.wisrc.sales.webapp.dao.sql;

import com.wisrc.sales.webapp.query.GetEstimateQuery;
import com.wisrc.sales.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class SynchronousSalesEstimateSql {
    public static String getEstimateEnity(GetEstimateQuery getEstimateQuery) {
        return new SQL() {{
            SELECT("estimate_id", "commodity_id");
            FROM("sales_estimate");
            WHERE(SQLUtil.forUtil("commodity_id", getEstimateQuery.getCommodityIdList()));
        }}.toString();
    }

    public static String getApprovalByIds(@Param("estimateIds") List estimateIds) {
        return new SQL() {{
            SELECT("estimate_id", "direct_approv_status", "manager_approv_status", "plan_depart_approv_status", "direct_approv_remark", "manager_approv_remark", "plan_depart_approv_remark", "MAX(expiration_date)");
            FROM("sales_estimate_approval_info");
            WHERE(SQLUtil.forUtil("estimate_id", estimateIds));
            GROUP_BY("estimate_id");
        }}.toString();
    }

    public static String getDetailByIds(@Param("estimateIds") List estimateIds) {
        return new SQL() {{
            SELECT("uuid", "estimate_id", "estimate_date", "estimate_number", "MAX(expiration_date)");
            FROM("sales_estimate_detail_info");
            WHERE(SQLUtil.forUtil("estimate_id", estimateIds));
            GROUP_BY("estimate_id", "estimate_date");
        }}.toString();
    }

    public static String getRemarkByIds(@Param("estimateIds") List estimateIds) {
        return new SQL() {{
            SELECT("estimate_detail_id", "estimate_date", "employee_id", "remark", "MAX(expiration_date)");
            FROM("sale_estimate_detail_remark");
            WHERE(SQLUtil.forUtil("estimate_id", estimateIds));
            GROUP_BY("estimate_id", "estimate_date");
        }}.toString();
    }
}
