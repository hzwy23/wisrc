package com.wisrc.purchase.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class OrderTracingSql {
    public static String findTracingOrderByCond(
            @Param("orderId") String orderId,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("employeeId") String employeeId,
            @Param("supplierId") String supplierId,
            @Param("tiicketOpenCd") Integer tiicketOpenCd,
            @Param("customsTypeCd") Integer customsTypeCd,
            @Param("sku") String sku,
            @Param("deliveryTypeCd") Integer deliveryTypeCd,
            @Param("keywords") String keywords,
            @Param("skuIds") String skuIds) {
        return new SQL() {{
            SELECT("order_id, bill_date,  sku_id, id, employee_id, supplier_id, payment_provision, customs_type_cd, tiicket_open_cd, freight, total_amount, unit_price_without_tax,amount_without_tax,tax_rate," +
                    "unit_price_with_tax,amount_with_tax,remark,order_remark,delivery_type_cd,quantity,spare_rate");
            FROM("v_order_baisi_product_info");
            WHERE("order_status =0");
            if (orderId != null) {
                WHERE("order_id like concat('%',#{orderId},'%')");
            }
            if (employeeId != null) {
                WHERE("employee_id =#{employeeId}");
            }
            if (supplierId != null) {
                WHERE("supplier_id =#{supplierId}");
            }
            if (tiicketOpenCd != 0) {
                WHERE("tiicket_open_cd =#{tiicketOpenCd}");
            }
            if (customsTypeCd != 0) {
                WHERE("customs_type_cd =#{customsTypeCd}");
            }
            if (sku != null) {
                WHERE("sku_id like concat('%',#{sku},'%')");
            }
            if (deliveryTypeCd != 0) {
                WHERE("delivery_type_cd =#{deliveryTypeCd}");
            }
            if (startTime != null) {
                WHERE("date(bill_date)>=#{startTime}");
            }
            if (endTime != null) {
                WHERE("date(bill_date)<=#{endTime}");
            }
            if (keywords != null) {
                WHERE("payment_provision like concat('%',#{keywords},'%') or remark like concat('%',#{keywords},'%') or order_remark like concat('%',#{keywords},'%')");
            }
            if (skuIds != null) {
                WHERE("sku_id in " + "(" + skuIds + ")");
            }
            ORDER_BY("bill_date desc, order_id desc");
        }}.toString();
    }

    public static String getCount(
            @Param("orderId") String orderId,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("employeeId") String employeeId,
            @Param("supplierId") String supplierId,
            @Param("tiicketOpenCd") Integer tiicketOpenCd,
            @Param("customsTypeCd") Integer customsTypeCd,
            @Param("sku") String sku,
            @Param("deliveryTypeCd") Integer deliveryTypeCd,
            @Param("keywords") String keywords,
            @Param("skuIds") String skuIds) {
        return new SQL() {{
            SELECT("count(1)");
            FROM("v_order_baisi_product_info");
            WHERE("order_status =0");
            if (orderId != null) {
                WHERE("order_id like concat('%',#{orderId},'%')");
            }
            if (employeeId != null) {
                WHERE("employee_id =#{employeeId}");
            }
            if (supplierId != null) {
                WHERE("supplier_id =#{supplierId}");
            }
            if (tiicketOpenCd != 0) {
                WHERE("tiicket_open_cd =#{tiicketOpenCd}");
            }
            if (customsTypeCd != 0) {
                WHERE("customs_type_cd =#{customsTypeCd}");
            }
            if (sku != null) {
                WHERE("sku_id like concat('%',#{sku},'%')");
            }
            if (deliveryTypeCd != 0) {
                WHERE("delivery_type_cd =#{deliveryTypeCd}");
            }
            if (startTime != null) {
                WHERE("date(bill_date)>=#{startTime}");
            }
            if (endTime != null) {
                WHERE("date(bill_date)<=#{endTime}");
            }
            if (keywords != null) {
                WHERE("payment_provision like concat('%',#{keywords},'%') or remark like concat('%',#{keywords},'%')");
            }
        }}.toString();
    }
}
