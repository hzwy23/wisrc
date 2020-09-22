package com.wisrc.order.webapp.dao.sql;

import com.wisrc.order.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class SaleNvoiceOrderInfoSql {
    public static String getList(@Param("orderIdList") List orderIdList) {
        return SQLUtil.forUtil("select uuid, invoice_number, order_id, original_order_id, create_time, create_user where order_id", orderIdList);
    }

    public static String getOrderIdByInvoice(@Param("invoiceNumbers") List invoiceNumbers) {
        return new SQL() {{
            SELECT("invoice_number", "order_id");
            FROM("order_nvoice_relation");
            WHERE(SQLUtil.forUtil("invoice_number", invoiceNumbers));
        }}.toString();
    }
}
