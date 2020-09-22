package com.wisrc.warehouse.webapp.dao.sql;

import com.wisrc.warehouse.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.SimpleFormatter;

public class WsRmpWarehouseStockSumSQL {


    public static String search(
            @Param("date") String date,
            @Param("skuIdList") List skuIdList,
            @Param("orderFlag") int orderFlag) {
        String orderby = null;
        if (orderFlag == 0) {
            orderby = "ORDER BY total_qty DESC";
        } else if (orderFlag == 1) {
            orderby = "ORDER BY product_qty ASC";
        } else if (orderFlag == 2) {
            orderby = "ORDER BY product_qty DESC";
        } else if (orderFlag == 3) {
            orderby = "ORDER BY transport_qty ASC";
        } else if (orderFlag == 4) {
            orderby = "ORDER BY transport_qty DESC";
        } else if (orderFlag == 5) {
            orderby = "ORDER BY panyu_stock_qty ASC";
        } else if (orderFlag == 6) {
            orderby = "ORDER BY panyu_stock_qty DESC";
        } else if (orderFlag == 7) {
            orderby = "ORDER BY virtual_stock_qty ASC";
        } else if (orderFlag == 8) {
            orderby = "ORDER BY virtual_stock_qty DESC";
        } else if (orderFlag == 9) {
            orderby = "ORDER BY overseas_transport_qty ASC";
        } else if (orderFlag == 10) {
            orderby = "ORDER BY overseas_transport_qty DESC";
        } else if (orderFlag == 11) {
            orderby = "ORDER BY overseas_stock_qty ASC";
        } else if (orderFlag == 12) {
            orderby = "ORDER BY overseas_stock_qty DESC";
        } else if (orderFlag == 999) {
            orderby = "ORDER BY total_qty ASC";
        }
        return SQLUtil.forUtil("SELECT  store_sku as sku_id,cn_name as sku_name, product_qty, transport_qty as local_onway_qty, panyu_stock_qty as local_stock_qty, " +
                "virtual_stock_qty, overseas_transport_qty, overseas_stock_qty,total_qty  FROM ws_rmp_warehouse_stock_sum where data_dt=#{date} and  store_sku", skuIdList) + orderby;
    }

    public static String getTotal(@Param("date") String date, @Param("skuIds") List skuIds) {
        return SQLUtil.forUtil("SELECT  sum(product_qty+transport_qty+panyu_stock_qty+virtual_stock_qty+overseas_transport_qty+overseas_stock_qty) as total_qty,store_sku  FROM ws_rmp_warehouse_stock_sum where data_dt= '" + date + "' and store_sku", skuIds) + " group by store_sku";
    }

    public static String getMskuTotal(@Param("date") String date, @Param("skuIds") List skuIds) {
        return SQLUtil.forUtil("select sum(fba_return_qty+fba_transport_qty+fba_stock_qty) as total_qty, store_sku  FROM fba_warehouse_stock_sum where data_dt= '" + date + "' and store_sku", skuIds) + " group by store_sku";
    }

    public static String getSkuId(@Param("date") String date, @Param("keyword") String keyword) {
        return new SQL() {{
            SELECT("store_sku");
            FROM("ws_rmp_warehouse_stock_sum");
            WHERE("data_dt = #{date}");
            if (keyword != null) {
                WHERE("store_sku like concat('%',#{keyword},'%') OR cn_name like concat('%',#{keyword},'%')");
            }
        }}.toString();
    }

    public static String getMskuSku(@Param("date") String date, @Param("keyword") String keyword, @Param("shopName") String shopName) {
        return new SQL() {{
            SELECT("store_sku");
            FROM("fba_warehouse_stock_sum");
            WHERE("data_dt = #{date}");
            if (shopName != null) {
                WHERE("shop_name = #{shopName}");
            }
            if (keyword != null) {
                WHERE("fn_sku_id like concat('%',#{keyword},'%')");
            }
        }}.toString();
    }
}
