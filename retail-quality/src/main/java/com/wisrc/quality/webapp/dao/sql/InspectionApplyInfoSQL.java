package com.wisrc.quality.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Date;

public class InspectionApplyInfoSQL {

    public static String findByCond(@Param("orderId") String orderId, @Param("employeeId") String employeeId, @Param("inspectionStartTime") Date inspectionStartTime,
                                    @Param("inspectionEndTime") Date inspectionEndTime, @Param("statusCd") String statusCd, @Param("inspectionType") String inspectionType, @Param("supplierIds") String[] supplierIds) {
        return new SQL() {{
            SELECT("inspection_id,order_id,employee_id,apply_date,expect_inspection_time,inspection_type_cd,supplier_id,supplier_contact_user,supplier_phone,supplier_addr," +
                    "remark,create_time,create_user,modify_user,modify_time");
            FROM("inspection_apply_info");
            if (orderId != null && !orderId.isEmpty()) {
                WHERE("order_id like concat('%',#{orderId},'%')");
            }
            if (employeeId != null && !employeeId.isEmpty()) {
                WHERE("employee_id=#{employeeId}");
            }
            if (inspectionStartTime != null && !"".equals(inspectionStartTime)) {
                WHERE("expect_inspection_time >= #{inspectionStartTime}");
            }
            if (inspectionEndTime != null && !"".equals(inspectionEndTime)) {
                WHERE("expect_inspection_time <= #{inspectionEndTime}");
            }
            /*
            申请单的状态和检验单的检验方式有关系 【默认关系】
            申请单的状态 有未开始、待检验、已完成
            检验单的检验方式有四种 为【不存在】、工厂验货、免检、仓库验货
            对应关系是
            未开始——【不存在】
            待检验—— 工厂验货
            已完成——免检、仓库验货
             */
            if (statusCd != null && !"".equals(statusCd)) {
                if ("1".equals(statusCd)) {
                    WHERE("inspection_type_cd is null");
                } else if ("2".equals(statusCd)) {
                    WHERE("inspection_type_cd=2");
                } else {
                    WHERE("(inspection_type_cd=1 or inspection_type_cd=3 or inspection_type_cd=2)");
                }
            }
            if (inspectionType != null && !"0".equals(inspectionType)) {
                WHERE("inspection_type_cd=#{inspectionType}");
            }
            if (supplierIds != null && supplierIds.length > 0) {
                WHERE("supplier_id IN  (" + idsToStr(supplierIds) + ")");
            }
            ORDER_BY("inspection_id DESC,expect_inspection_time DESC, apply_date DESC");
        }}.toString();
    }

    public static String findInspectionData(@Param("orderId") String orderId, @Param("supplierIds") String[] supplierIds, @Param("skuId") String skuId,
                                            @Param("skuIds") String[] skuIds, @Param("inspectionId") String inspectionId) {
        return new SQL() {{
            SELECT("inspection_id inspectionId,order_id orderId,employee_id applyPersonId,sku_id skuId,expect_inspection_time expectInspectionTime,supplier_id supplierId," +
                    "supplier_addr supplierAddr,apply_inspection_quantity applyInspectionQuantity");
            FROM("v_inspection_product_info");
            if (orderId != null && !orderId.isEmpty()) {
                WHERE("order_id like concat('%',#{orderId},'%')");
            }
            if (supplierIds != null && supplierIds.length > 0) {
                WHERE("supplier_id IN  (" + idsToStr(supplierIds) + ")");
            }
            if (skuId != null && !skuId.isEmpty()) {
                WHERE("sku_id like concat('%',#{skuId},'%')");
            }
            if (skuIds != null && skuIds.length > 0) {
                WHERE("sku_id IN  (" + idsToStr(skuIds) + ")");
            }
            if (inspectionId != null && !inspectionId.isEmpty()) {
                WHERE("inspection_id like concat('%',#{inspectionId},'%')");
            }
            WHERE("status_cd = 2");
            ORDER_BY("expect_inspection_time DESC");
        }}.toString();
    }

    public static String findInspectionDataByWords(@Param("orderId") String orderId, @Param("supplierIds") String[] supplierIds, @Param("skuIds") String[] skuIds, @Param("skuId") String skuId) {
        return new SQL() {{
            SELECT("inspection_id inspectionId,order_id orderId,employee_id applyPersonId,sku_id skuId,expect_inspection_time expectInspectionTime,supplier_id supplierId," +
                    "supplier_addr supplierAddr,apply_inspection_quantity applyInspectionQuantity ");
            FROM("v_inspection_product_info");
            if (orderId != null && !orderId.isEmpty()) {
                WHERE("order_id like concat('%',#{orderId},'%')");
            }
            if (supplierIds != null && supplierIds.length > 0 || skuIds != null && skuIds.length > 0) {
                WHERE("sku_id IN (" + idsToStr(skuIds) + ") OR supplier_id IN (" + idsToStr(supplierIds) + ")");
            }
            if (skuId != null && !skuId.isEmpty()) {
                WHERE("sku_id like concat('%',#{skuId},'%')");
            }
            ORDER_BY("expect_inspection_time DESC");
        }}.toString();
    }

    public static String findInspecByOrderId(@Param("orderIds") String[] orderIds, @Param("skuIds") String[] skuIds) {
        return new SQL() {{
            SELECT("inspection_id ,order_id ,sku_id ,apply_inspection_quantity,inspection_type_cd,qualified_quantity,apply_date");
            FROM("v_inspection_product_info");
            if (orderIds != null && orderIds.length > 0) {
                WHERE("order_id IN (" + idsToStr(orderIds) + ")");
            }
            if (skuIds != null && skuIds.length > 0) {
                WHERE("sku_id IN (" + idsToStr(skuIds) + ")");
            }
            ORDER_BY("expect_inspection_time DESC");
        }}.toString();
    }

    public static String findInspecSumByOrderId(@Param("orderIds") String[] orderIds, @Param("skuIds") String[] skuIds) {
        return new SQL() {{
            SELECT("inspection_id, order_id,sku_id, SUM(CASE WHEN inspection_type_cd = 1 THEN apply_inspection_quantity WHEN inspection_type_cd = 3 THEN " +
                    "apply_inspection_quantity ELSE qualified_quantity  END ) completeNum");
            FROM("v_inspection_product_info");
            if (orderIds != null && orderIds.length > 0) {
                WHERE("order_id IN (" + idsToStr(orderIds) + ")");
            }
            if (skuIds != null && skuIds.length > 0) {
                WHERE("sku_id IN (" + idsToStr(skuIds) + ")");
            }
            GROUP_BY("order_id,sku_id ");
            ORDER_BY("expect_inspection_time DESC");
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
