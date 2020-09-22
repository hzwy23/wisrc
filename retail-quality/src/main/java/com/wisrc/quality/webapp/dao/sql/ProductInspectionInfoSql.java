package com.wisrc.quality.webapp.dao.sql;

import com.wisrc.quality.webapp.vo.productInspectionInfo.get.GetProductInspectionInfoVO;
import org.apache.ibatis.jdbc.SQL;

public class ProductInspectionInfoSql {
    public static String findALl(GetProductInspectionInfoVO ele) {
        return new SQL() {{
            SELECT("annex_name,sku_id,inspection_cd ,inspection_date ,purchase_order_id ,inspection_method_cd ,inspection_type_cd,test_times ,inspection_level_cd ,sampling_plan_cd,apply_inspection_quantity,actual_inspection_quantity,change_reason_cd,sample_quantity,conclusion_cd,final_determine_cd,qualified_quantity,unqualified_quantity,annex_address,quality_consumption ,source_type_cd ,source_document_cd ,inspection_status_cd, remark,create_user,create_time,update_time");
            FROM("inspection_product_info t");
            WHERE("delete_status = 0");
            if (ele.getPurchaseOrderId() != null) {
                WHERE("purchase_order_id LIKE concat('%', #{purchaseOrderId}, '%')");
            }
            if (ele.getInspectionDateStart() != null) {
                WHERE("inspection_date   >= #{inspectionDateStart}");
            }
            if (ele.getInspectionDateEnd() != null) {
                WHERE("inspection_date  <= #{inspectionDateEnd}");
            }
            if (ele.getInspectionStatusCd() != null) {
                WHERE("inspection_status_cd = #{inspectionStatusCd}");
            }
            if (ele.getFinalDetermineCd() != null) {
                WHERE("final_determine_cd =  #{finalDetermineCd}");
            }
            if (ele.getSourceTypeCd() != null) {
                WHERE("source_type_cd = #{sourceTypeCd}");
            }
            if (ele.getSkuId() != null) {
                WHERE("sku_id LIKE concat('%', #{skuId}, '%')");
            }
            if (ele.getEmployeeId() != null) {
                WHERE("exists (select 1 from inspection_personnel_info f where t.inspection_cd = f.inspection_cd and f.employee_id like concat('%',#{employeeId},'%') )");
            }
            ORDER_BY(" update_time desc ");
        }}.toString();
    }
}
