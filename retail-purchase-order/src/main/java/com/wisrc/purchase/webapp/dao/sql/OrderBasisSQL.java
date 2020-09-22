package com.wisrc.purchase.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Date;
import java.util.List;

public class OrderBasisSQL {
    public static String findBasisInfo(@Param("orderId") final String orderId,
                                       @Param("employeeId") final String employeeId,
                                       @Param("deliveryTypeCd") final int deliveryTypeCd,
                                       @Param("keyword") final String keyword,
                                       @Param("tiicketOpenCd") final int tiicketOpenCd,
                                       @Param("customsTypeCd") final int customsTypeCd,
                                       @Param("billDateBegin") final Date billDateBegin,
                                       @Param("billDateEnd") final Date billDateEnd,
                                       @Param("deliveryTimeBegin") final Date deliveryTimeBegin,
                                       @Param("deliveryTimeEnd") final Date deliveryTimeEnd,
                                       @Param("supplierId") final String supplierId,
                                       @Param("supplierIds") final String supplierIds) {
        return new SQL() {{
            SELECT("distinct order_id,bill_date,supplier_id,employee_id,amount_without_tax,amount_with_tax,tiicket_open_cd,customs_type_cd,payment_provision,delivery_type_cd");
            FROM("v_order_details_info");
            WHERE("order_status = 0");
            if (orderId != null) {
                WHERE("order_id  like concat('%',#{orderId},'%')");
            }

            if (employeeId != null) {
                WHERE("employee_id like concat('%',#{employeeId},'%')");
            }

            if (deliveryTypeCd != 0) {
                WHERE(" delivery_type_cd = #{deliveryTypeCd}");
            }

            if (tiicketOpenCd != 0) {
                WHERE(" tiicket_open_cd = #{tiicketOpenCd}");
            }

            if (customsTypeCd != 0) {
                WHERE(" customs_type_cd = #{customsTypeCd}");
            }
            if (billDateBegin != null) {
                WHERE(" bill_date >= #{billDateBegin}");
            }

            if (billDateEnd != null) {
                WHERE(" bill_date  <= #{billDateEnd}");
            }

            if (deliveryTimeBegin != null) {
                WHERE(" delivery_time  >= #{deliveryTimeBegin}");
            }

            if (deliveryTimeEnd != null) {
                WHERE(" delivery_time  <= #{deliveryTimeEnd}");
            }

            if (keyword != null && !keyword.isEmpty()) {
                if (supplierIds != null) {
                    WHERE("( payment_provision like concat('%',#{keyword},'%') " +
                            "or supplier_id in (" + supplierIds + ") )");
                } else {
                    WHERE("payment_provision  like concat('%',#{keyword},'%')");
                }
            }
            if (supplierId != null && !supplierId.isEmpty()) {
                WHERE(" supplier_id  = #{supplierId}");
            }
            ORDER_BY("order_id desc");
        }}.toString();
    }

    public static String findBasisByIds(@Param("ids") final String[] ids) {
        String str = "";
        String endstr = "";
        for (int i = 0; i < ids.length; i++) {
            str = "'" + ids[i] + "'";
            if (i < ids.length - 1) {
                str += ",";
            }
            endstr += str;
        }
        String finalEndstr = endstr;
        return new SQL() {{
            SELECT("distinct order_id,bill_date,supplier_id,employee_id,amount_without_tax,amount_with_tax,tiicket_open_cd,customs_type_cd,payment_provision,delivery_type_cd");
            FROM("v_order_details_info");
            WHERE("order_status = 0");
            WHERE("order_id in (" + finalEndstr + ")");
            GROUP_BY("order_id");
            ORDER_BY("bill_date asc");
        }}.toString();
    }

    public static String findBasisNeet(@Param("orderId") final String orderId,
                                       @Param("supplierIds") final List supplierIds,
                                       @Param("skuId") final String skuId,
                                       @Param("skuIds") final List skuIds) {
        return new SQL() {{
            SELECT("distinct order_id ,supplier_id,sku_id");
            FROM("v_order_details_info");
            WHERE("order_status = 0");
            if (orderId != null) {
                WHERE("order_id  like concat('%',#{orderId},'%')");
            }
            if (skuId != null) {
                WHERE("sku_id  like concat('%',#{skuId},'%')");
            }
            if (supplierIds == null) {
                WHERE("1=2");
            } else if (supplierIds.size() > 0) {
                String supplierIdList = toInString(supplierIds);
                WHERE("supplier_id  in (" + supplierIdList + ")");
            }
            if (skuIds == null) {
                WHERE("1=2");
            } else if (skuIds.size() > 0) {
                String skuIdList = toInString(skuIds);
                WHERE("sku_id  in (" + skuIdList + ")");
            }
            ORDER_BY("order_id desc");
        }}.toString();
    }

    public static String findBasisNeet2(@Param("orderId") final String orderId,
                                        @Param("supplierIds") final List supplierIds,
                                        @Param("skuId") final String skuId,
                                        @Param("skuIds") final List skuIds) {
        return new SQL() {{
            SELECT("DISTINCT order_id orderId,supplier_id,sku_id");
            FROM("v_order_details_info");
            WHERE("order_status = 0");
            if (orderId != null) {
                WHERE("order_id  like concat('%',#{orderId},'%')");
            }
            if (skuId != null) {
                WHERE("sku_id  like concat('%',#{skuId},'%')");
            }
            if (skuIds == null && supplierIds == null) {
                WHERE("1=2");
            } else if (supplierIds == null && skuIds.size() > 0) {
                String skuIdList = toInString(skuIds);
                WHERE("sku_id  in (" + skuIdList + ") ");
            } else if (skuIds == null && supplierIds.size() > 0) {
                String supplierIdList = toInString(supplierIds);
                WHERE("supplier_id  in (" + supplierIdList + ") ");
            } else if (skuIds.size() > 0 && supplierIds.size() > 0 && skuId == null) {
                String skuIdList = toInString(skuIds);
                String supplierIdList = toInString(supplierIds);
                WHERE("sku_id  in (" + skuIdList + ") or supplier_id  in (" + supplierIdList + ")");
            } else if (skuIds.size() > 0 && supplierIds.size() > 0 && skuId != null) {
                String supplierIdList = toInString(supplierIds);
                WHERE("supplier_id  in (" + supplierIdList + ")");
            }
            ORDER_BY("order_id desc");
        }}.toString();
    }

    public static String toInString(List ids) {
        String str = "";
        String endstr = "";
        for (int i = 0; i < ids.size(); i++) {
            str = "'" + ids.get(i) + "'";
            if (i < ids.size() - 1) {
                str += ",";
            }
            endstr += str;
        }
        return endstr;
    }
}
