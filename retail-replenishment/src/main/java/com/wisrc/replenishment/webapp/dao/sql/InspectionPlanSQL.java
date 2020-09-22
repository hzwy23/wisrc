package com.wisrc.replenishment.webapp.dao.sql;

import com.wisrc.replenishment.webapp.query.GetInspectionPlanByPlanIdsQuery;
import com.wisrc.replenishment.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Date;

public class InspectionPlanSQL {
    private static final int using = 0;
    private static final int delete = 1;

    public static String getInspectionPlanByPlanIds(GetInspectionPlanByPlanIdsQuery inspectionPlanQuery) {
        return new SQL() {{
            SELECT("commodity_id", "MAX(delivery_plan_date) delivery_plan_date", "delivery_plan_quantity");
            FROM("inspection_plan");
            if (null != inspectionPlanQuery.getCommodityIds() && inspectionPlanQuery.getCommodityIds().size() != 0) {
                WHERE(SQLUtil.whereIn("commodity_id", inspectionPlanQuery.getCommodityIds()));
            }
            WHERE("delete_status = " + using);
            if (inspectionPlanQuery.getDeliveryPlanEndDate() != null) {
                WHERE("delivery_plan_date <= #{deliveryPlanEndDate}");
            }
            if (inspectionPlanQuery.getDeliveryPlanStartDate() != null) {
                WHERE("delivery_plan_date >= #{deliveryPlanStartDate}");
            }
            GROUP_BY("commodity_id");
        }}.toString();
    }

    public static String mskuInSalesEndTime(@Param("deliveryPlanEndDate") Date deliveryPlanEndDate, @Param("deliveryPlanStartDate") Date deliveryPlanStartDate) {
        return new SQL() {{
            SELECT("DISTINCT(commodity_id)");
            FROM("inspection_plan");
            WHERE("delete_status = " + using);
            if (deliveryPlanEndDate != null) {
                WHERE("delivery_plan_date <= #{deliveryPlanEndDate}");
            }
            if (deliveryPlanStartDate != null) {
                WHERE("delivery_plan_date >= #{deliveryPlanStartDate}");
            }
        }}.toString();
    }
}
