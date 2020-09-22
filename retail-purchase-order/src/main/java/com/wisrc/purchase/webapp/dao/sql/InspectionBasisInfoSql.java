package com.wisrc.purchase.webapp.dao.sql;

import com.wisrc.purchase.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class InspectionBasisInfoSql {
    static final int delete = 1;
    static final int using = 0;

    public static String deleteInspectionByIds(@Param("arrivalIds") List arrivalIds) {
        return new SQL() {{
            UPDATE("arrival_basis_info");
            SET("delete_status = " + delete);
            WHERE(SQLUtil.forUtil("arrival_id", arrivalIds));
        }}.toString();
    }

    public static String getInspectionSelector(@Param("purchaseOrderId") String purchaseOrderId) {
        return new SQL() {{
            SELECT("arrival_id");
            FROM("arrival_basis_info");
            WHERE("delete_status = " + using);
            if (purchaseOrderId != null) {
                WHERE("purchase_order_id LIKE concat('%', #{purchaseOrderId}, '%')");
            }
        }}.toString();
    }
}
