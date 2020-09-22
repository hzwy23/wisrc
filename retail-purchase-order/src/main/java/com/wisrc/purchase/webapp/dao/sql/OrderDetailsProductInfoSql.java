package com.wisrc.purchase.webapp.dao.sql;

import com.wisrc.purchase.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class OrderDetailsProductInfoSql {
    public static String getQuantity(@Param("orderId") String orderId, @Param("skuId") List skuIds) {
        return new SQL() {{
            SELECT("sku_id", "quantity", "spare_rate");
            FROM("order_details_product_info");
            WHERE("order_id = #{orderId}");
            WHERE(SQLUtil.forUtil("sku_id", skuIds));
        }}.toString();
    }
}
