package com.wisrc.quality.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class InspectionApplyProductDetailsInfoSQL {

    public static String findByInspectionIds(@Param("inspectionIds") String[] inspectionIds) {
        return new SQL() {{
            SELECT("inspection_product_id,inspection_id,sku_id,apply_inspection_quantity,status_cd,inspection_quantity,qualified_quantity,unqualified_quantity");
            FROM("inspection_apply_details_info");
            WHERE("inspection_id IN (" + idsToStr(inspectionIds) + ")");
        }}.toString();
    }

    public static String idsToStr(String[] ids) {
        String str = "";
        String endstr = "";
        for (int i = 0; i < ids.length; i++) {
            str = "'" + ids[i] + "'";
            if (i < ids.length - 1) {
                str += ",";
            }
            endstr += str;
        }
        return endstr;
    }
}
