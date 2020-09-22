package com.wisrc.order.webapp.dao.sql;

import com.wisrc.order.webapp.query.NvoiceOrderPageQuery;
import com.wisrc.order.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class SaleNvoiceInfoSql {
    public static String saleNvoicePage(NvoiceOrderPageQuery nvoiceOrderPageQuery) {
        return new SQL() {{
            SELECT("sni.invoice_number", "sisa.status_desc", "sni.create_time", "soi.order_id", "soi.original_order_id", "total_weight", "sli.logistics_id", "sli.offer_id", "sli.logistics_cost");
            FROM("order_nvoice_info AS sni");
            LEFT_OUTER_JOIN("order_nvoice_relation AS snoi ON snoi.invoice_number = sni.invoice_number");
            LEFT_OUTER_JOIN("order_basic_info AS soi ON soi.order_id = snoi.order_id");
            LEFT_OUTER_JOIN("order_invoice_status_attr AS sisa ON sisa.status_cd = sni.status_cd");
            LEFT_OUTER_JOIN("order_logistics_info AS sli ON sli.order_id = soi.order_id");
            LEFT_OUTER_JOIN("order_commodity_info AS soci ON soci.order_id = snoi.order_id");
            if (null != nvoiceOrderPageQuery.getInvoiceNumber()) {
                WHERE("sni.invoice_number like concat('%',#{invoiceNumber},'%')");
            }
            if (nvoiceOrderPageQuery.getWmsWaveNumber() != null) {

            }
            if (nvoiceOrderPageQuery.getOrderId() != null) {
                WHERE("soi.order_id like concat('%',#{orderId},'%')");
            }
            if (nvoiceOrderPageQuery.getOriginalOrderId() != null) {
                WHERE("soi.original_order_id like concat('%',#{originalOrderId},'%')");
            }
            if (nvoiceOrderPageQuery.getStatusCd() != null) {
                WHERE("sni.status_cd = #{statusCd}");
            }
            if (nvoiceOrderPageQuery.getMskuIds() != null && !nvoiceOrderPageQuery.getMskuIds().isEmpty()) {
                WHERE(SQLUtil.forUtil("soci.commodity_id", nvoiceOrderPageQuery.getMskuIds()));
            }
        }}.toString();
    }

    public static String saleNvoiceByOrder(@Param("orderIds") List orderIds) {
        return new SQL() {{
            SELECT("sni.invoice_number", "status_desc", "wms_wave_number", "total_weight");
            FROM("order_nvoice_info AS sni");
            LEFT_OUTER_JOIN("order_nvoice_relation AS snoi ON snoi.invoice_number = sni.invoice_number", "order_invoice_status_attr AS sisa ON sisa.status_cd = sni.status_cd");
        }}.toString();
    }
}
