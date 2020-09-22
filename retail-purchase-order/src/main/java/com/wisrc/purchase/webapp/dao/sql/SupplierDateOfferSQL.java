package com.wisrc.purchase.webapp.dao.sql;

import com.wisrc.purchase.webapp.query.supplierOffer.SupplierOfferPageQuery;
import com.wisrc.purchase.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class SupplierDateOfferSQL {
    public static String findInfo(@Param("employeeId") final String employeeId,
                                  @Param("supplierId") final String supplierId,
                                  @Param("skuId") final String skuId,
                                  @Param("statusCd") final int statusCd) {
        return new SQL() {{
            SELECT("supplier_offer_id,status_cd,employee_id,sku_id,supplier_id,first_delivery,general_delivery,unit_price_without_tax,minimum,haulage_days,delivery_part,delivery_optimize,remark,create_user,create_time,modify_user,modify_time,delete_status");
            FROM("supplier_date_offer_info");
            WHERE("delete_status = 0");
            if (employeeId != null) {
                WHERE("employee_id  like concat('%',#{employeeId},'%')");
            }
            if (supplierId != null) {
                WHERE("supplier_id  like concat('%',#{supplierId},'%')");
            }
            if (skuId != null) {
                WHERE("sku_id  like concat('%',#{skuId},'%')");
            }
            if (statusCd != 0) {
                WHERE("status_cd  =#{statusCd}");
            }
            ORDER_BY("sku_id asc,unit_price_without_tax asc");
        }}.toString();
    }

    public static String getRecentDelivery(@Param("skuIds") List skuIds) {
        return new SQL() {{
            SELECT("sku_id", "MAX(modify_time)", "supplier_id", "general_delivery", "haulage_days", "minimum");
            FROM("supplier_date_offer_info");
            WHERE(SQLUtil.forUtil("sku_id", skuIds));
            GROUP_BY("sku_id");
        }}.toString();
    }

    public static String supplierOfferPage(SupplierOfferPageQuery queryPojo) {
        return new SQL() {{
            SELECT("supplier_offer_id,status_cd,employee_id,sku_id,supplier_id,first_delivery,general_delivery,unit_price_without_tax,minimum,haulage_days,delivery_part,delivery_optimize,remark,create_user,create_time,modify_user,modify_time,delete_status");
            FROM("supplier_date_offer_info");
            WHERE("delete_status = 0");
            if (queryPojo.getEmployeeId() != null) {
                WHERE("employee_id  like concat('%',#{employeeId},'%')");
            }
            if (queryPojo.getSupplierIds() != null) {
                WHERE(SQLUtil.forUtil("supplier_id", queryPojo.getSupplierIds()));
            }
            if (queryPojo.getSkuIds() != null) {
                WHERE(SQLUtil.forUtil("sku_id", queryPojo.getSkuIds()));
            }
            if (queryPojo.getStatusCd() != 0) {
                WHERE("status_cd  =#{statusCd}");
            }
            ORDER_BY("sku_id asc,unit_price_without_tax asc");
        }}.toString();
    }
}
