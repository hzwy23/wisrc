package com.wisrc.purchase.webapp.dao.sql;


import com.wisrc.purchase.webapp.query.ArrivalPageQuery;
import com.wisrc.purchase.webapp.query.InspectionPageQuery;
import com.wisrc.purchase.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;

public class InspectionProductDetailsInfoSQL {
    static final int using = 0;
    static final int delete = 1;

    public static String deleteBatch(@Param("arrivalProductIds") final List<String> arrivalProductIds) {
        return new SQL() {{
            UPDATE("arrival_product_details_info");
            SET("delete_status = 1");
            String where = "arrival_product_id IN (";
            for (int m = 0; m < arrivalProductIds.size(); m++) {
                if (m == 0) {
                    where += ("'" + arrivalProductIds.get(0) + "'");
                } else {
                    where += (", '" + arrivalProductIds.get(m) + "'");
                }
                if (m == arrivalProductIds.size() - 1) {
                    where += ")";
                }
            }
            WHERE(where);
        }}.toString();
    }

    public static String getInspectionPage(InspectionPageQuery inspectionPageQuery) {
        return new SQL() {{
            SELECT("distinct t.arrival_product_id", "a.arrival_id", "a.apply_date", "employee_id", "a.estimate_arrival_date", "supplier_id", "sku_id",
                    "purchase_order_id", "logistics_id", "t.status_cd");
            FROM("arrival_basis_info a");
            INNER_JOIN("arrival_product_details_info t ON a.arrival_id = t.arrival_id ");
            INNER_JOIN("arrival_freight_amrt_attr ita ON ita.freight_apportion_cd = a.freight_apportion_cd");
            WHERE("t.delete_status = " + using);
            WHERE("a.delete_status = 0");
            if (inspectionPageQuery.getArrivalId() != null && !inspectionPageQuery.getArrivalId().isEmpty()) {
                WHERE("a.arrival_id LIKE concat('%',#{arrivalId},'%')");
            }
            if (inspectionPageQuery.getApplyStartDate() != null) {
                WHERE("apply_date >= #{applyStartDate}");
            }
            if (inspectionPageQuery.getApplyEndDate() != null) {
                WHERE("apply_date <= #{applyEndDate}");
            }
            if (inspectionPageQuery.getEmployeeId() != null) {
                WHERE("employee_id = #{employeeId}");
            }
            if (inspectionPageQuery.getExpectArrivalStartTime() != null) {
                WHERE("estimate_arrival_date >= #{expectArrivalStartTime}");
            }
            if (inspectionPageQuery.getExpectArrivalEndTime() != null) {
                WHERE("estimate_arrival_date <= #{expectArrivalEndTime}");
            }
            if (inspectionPageQuery.getOrderId() != null) {
                WHERE("purchase_order_id LIKE concat('%',#{orderId},'%')");
            }
            if (inspectionPageQuery.getSkuId() != null) {
                WHERE("sku_id LIKE concat('%',#{skuId},'%')");
            }
            if (inspectionPageQuery.getLogisticsId() != null) {
                WHERE("a.logistics_id LIKE concat('%',#{logisticsId},'%')");
            }
            if (inspectionPageQuery.getStatusCd() != null) {
                WHERE("t.status_cd = #{statusCd}");
            }
            if (inspectionPageQuery.getFindKey() != null) {
                List<String> handles = new ArrayList();
                String where = "1=0";
                if (inspectionPageQuery.getSupplierIds() != null && inspectionPageQuery.getSupplierIds().size() > 0) {
                    handles.add(SQLUtil.forUtil("supplier_id", inspectionPageQuery.getSupplierIds()));
                }
                if (inspectionPageQuery.getSkuIds() != null && inspectionPageQuery.getSkuIds().size() > 0) {
                    handles.add(SQLUtil.forUtil("sku_id", inspectionPageQuery.getSkuIds()));
                }
                for (int m = 0; m < handles.size(); m++) {
                    if (m == 0) {
                        where = "(";
                        where += handles.get(0);
                    } else {
                        where += " OR " + handles.get(m);
                    }
                    if (m == handles.size() - 1) {
                        where += ")";
                    }
                }
                WHERE(where);
            }

            if (inspectionPageQuery.getArrivalProductIds() != null) {
                WHERE(SQLUtil.forUtil("arrival_product_id", inspectionPageQuery.getArrivalProductIds()));
            }
            ORDER_BY("a.estimate_arrival_date DESC, a.apply_date DESC, a.arrival_id DESC");
        }}.toString();
    }

    public static String getInspectionPageInId(@Param("arrivalProductIds") List arrivalProductIds) {
        return new SQL() {{
            SELECT("t.arrival_product_id", "a.arrival_id", "a.apply_date", "employee_id", "a.estimate_arrival_date", "supplier_id", "sku_id",
                    "purchase_order_id", "logistics_id", "t.status_cd");
            FROM("arrival_product_details_info t");
            INNER_JOIN("arrival_basis_info a on a.arrival_id = t.arrival_id");
            INNER_JOIN("arrival_freight_amrt_attr AS ita on ita.freight_apportion_cd = a.freight_apportion_cd");
            WHERE("t.delete_status = " + using);
            WHERE(SQLUtil.forUtil("arrival_product_id", arrivalProductIds));
        }}.toString();
    }

    public static String getInspectionIdByProductId(@Param("arrivalProductIds") List arrivalProductIds) {
        return new SQL() {{
            SELECT("arrival_product_id", "arrival_id");
            FROM("arrival_product_details_info");
            WHERE("delete_status = " + using);
            WHERE(SQLUtil.forUtil("arrival_product_id", arrivalProductIds));
        }}.toString();
    }

    public static String deleteInspectionBasisInfo(@Param("arrivalProductIds") List arrivalProductIds) {
        return new SQL() {{
            UPDATE("arrival_product_details_info");
            SET("delete_status = " + delete);
            WHERE(SQLUtil.forUtil("arrival_product_id", arrivalProductIds));
        }}.toString();
    }

    public static String getArrivalProduct(InspectionPageQuery inspectionPageQuery) {
        return new SQL() {{
            SELECT("t.arrival_product_id", "a.arrival_id", "a.apply_date", "employee_id", "a.estimate_arrival_date", "supplier_id", "sku_id",
                    "purchase_order_id", "logistics_id", "delivery_quantity", "t.receipt_quantity", "t.receipt_spare_quantity");
            FROM("arrival_product_details_info t ");
            LEFT_OUTER_JOIN("arrival_basis_info AS a ON a.arrival_id = t.arrival_id ", "arrival_freight_amrt_attr AS ita ON ita.freight_apportion_cd = a.freight_apportion_cd");
            WHERE("t.delete_status = " + using);
            if (inspectionPageQuery.getArrivalId() != null) {
                WHERE("a.arrival_id LIKE concat('%',#{arrivalId},'%')");
            }
            if (inspectionPageQuery.getSkuId() != null) {
                WHERE("sku_id LIKE concat('%',#{skuId},'%')");
            }
            if (inspectionPageQuery.getOrderId() != null) {
                WHERE("purchase_order_id LIKE concat('%',#{orderId},'%')");
            }
            if (inspectionPageQuery.getSupplierIds() != null && inspectionPageQuery.getSupplierIds().size() > 0) {
                WHERE(SQLUtil.forUtil("supplier_id", inspectionPageQuery.getSupplierIds()));
            }
            if (inspectionPageQuery.getSkuIds() != null && inspectionPageQuery.getSkuIds().size() > 0) {
                WHERE(SQLUtil.forUtil("sku_id", inspectionPageQuery.getSkuIds()));
            }
            if (inspectionPageQuery.getStatusCd() != null) {
                WHERE("t.status_cd = #{statusCd}");
            }
            ORDER_BY("a.estimate_arrival_date DESC,a.apply_date DESC,a.arrival_id DESC");
        }}.toString();
    }

    public static String getArrivalPage(ArrivalPageQuery arrivalPageQuery) {
        return arrivalPage(arrivalPageQuery).toString();
    }

    public static String getArrivalPageInId(@Param("arrivalId") List arrivalId) {
        return arrivalPage(new ArrivalPageQuery()).WHERE(SQLUtil.forUtil("a.arrival_id", arrivalId)).toString();
    }

    private static SQL arrivalPage(ArrivalPageQuery inspectionPageQuery) {
        return new SQL() {{
            SELECT("a.arrival_id", "a.apply_date", "employee_id", "a.estimate_arrival_date", "supplier_id", "sku_id",
                    "purchase_order_id", "logistics_id", "MIN(t.status_cd) AS status_cd", "status_desc");
            FROM("arrival_basis_info a");
            INNER_JOIN("arrival_product_details_info t ON a.arrival_id = t.arrival_id ");
            INNER_JOIN("arrival_freight_amrt_attr ita ON ita.freight_apportion_cd = a.freight_apportion_cd");
            LEFT_OUTER_JOIN("arrival_basic_status_attr AS absa ON absa.status_cd = t.status_cd");
            WHERE("t.delete_status = " + using);
            WHERE("a.delete_status = 0");
            if (inspectionPageQuery.getArrivalId() != null && !inspectionPageQuery.getArrivalId().isEmpty()) {
                WHERE("a.arrival_id LIKE concat('%',#{arrivalId},'%')");
            }
            if (inspectionPageQuery.getApplyStartDate() != null) {
                WHERE("apply_date >= #{applyStartDate}");
            }
            if (inspectionPageQuery.getApplyEndDate() != null) {
                WHERE("apply_date <= #{applyEndDate}");
            }
            if (inspectionPageQuery.getEmployeeId() != null) {
                WHERE("employee_id = #{employeeId}");
            }
            if (inspectionPageQuery.getExpectArrivalStartTime() != null) {
                WHERE("estimate_arrival_date >= #{expectArrivalStartTime}");
            }
            if (inspectionPageQuery.getExpectArrivalEndTime() != null) {
                WHERE("estimate_arrival_date <= #{expectArrivalEndTime}");
            }
            if (inspectionPageQuery.getOrderId() != null) {
                WHERE("purchase_order_id LIKE concat('%',#{orderId},'%')");
            }
            if (inspectionPageQuery.getLogisticsId() != null) {
                WHERE("a.logistics_id LIKE concat('%',#{logisticsId},'%')");
            }
            if (inspectionPageQuery.getStatusCd() != null) {
                WHERE("t.status_cd = #{statusCd}");
            }
            if (inspectionPageQuery.getFindKey() != null) {
                List<String> handles = new ArrayList();
                String where = "1=0";
                if (inspectionPageQuery.getSupplierIds() != null && inspectionPageQuery.getSupplierIds().size() > 0) {
                    handles.add(SQLUtil.forUtil("supplier_id", inspectionPageQuery.getSupplierIds()));
                }
                for (int m = 0; m < handles.size(); m++) {
                    if (m == 0) {
                        where = "(";
                        where += handles.get(0);
                    } else {
                        where += " OR " + handles.get(m);
                    }
                    if (m == handles.size() - 1) {
                        where += ")";
                    }
                }
                WHERE(where);
            }
            if (inspectionPageQuery.getSkuIds() != null && inspectionPageQuery.getSkuIds().size() > 0) {
                WHERE(SQLUtil.forUtil("sku_id", inspectionPageQuery.getSkuIds()));
            }
            GROUP_BY("a.arrival_id");
            ORDER_BY("a.arrival_id DESC");
        }};
    }

    public static String getProductByArrival(@Param("arrivalIds") List arrivalIds) {
        return new SQL() {{
            SELECT("arrival_product_id");
            FROM("arrival_product_details_info");
            WHERE("delete_status = 0");
            WHERE(SQLUtil.forUtil("arrival_id", arrivalIds));
        }}.toString();
    }
}
