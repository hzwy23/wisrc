package com.wisrc.shipment.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SmartReplenishmentImplSql {

    public String fuzzyQuery(@Param("shopId") String shopId,
                             @Param("earlyWarningLevelDesc") String earlyWarningLevelDesc,
                             @Param("salesStatusCd") Integer salesStatusCd,
                             @Param("keyWords") String keyWords
    ) {
        return new SQL() {{
            SELECT("  uuid as uuid,\n" +
                    "  id as id,\n" +
                    "  shop_id as shop_id,\n" +
                    "  msku_id,\n" +
                    "  fba_on_warehouse_stock_num,\n" +
                    "  fba_on_way_stock_num,\n" +
                    "  on_warehouse_available_day,\n" +
                    "  on_way_available_day,\n" +
                    "  all_available_day,\n" +
                    "  safety_stock_days,\n" +
                    "  sku_id,\n" +
                    "  product_name,\n" +
                    "  employee_name,\n" +
                    "  sales_status_desc,\n" +
                    "  picture,\n" +
                    "  create_time,\n" +
                    "  early_warning_level_cd,\n" +
                    "  shop_name,\n" +
                    "  msku_name,\n" +
                    "  sales_status_cd,\n" +
                    "  early_warning_level_desc,\n" +
                    "  msku_safety_stock_days,\n" +
                    "  asin\n"
            );
            FROM("smart_replenishment_info ");
            if (shopId != null) {
                WHERE("shop_id = #{shopId}");
            }
            if (earlyWarningLevelDesc != null) {
                WHERE("early_warning_level_desc = #{earlyWarningLevelDesc}");
            }
            if (salesStatusCd != null) {
                WHERE("sales_status_cd = #{salesStatusCd}");
            }
            if (keyWords != null) {
                WHERE("( " +
                        "msku_id LIKE concat('%', #{keyWords},'%') " +
                        " or sku_id LIKE concat('%', #{keyWords},'%')" +
                        " or product_name LIKE concat('%', #{keyWords},'%')" +
                        ") ");
            }
            WHERE(" create_time IN(SELECT max(create_time) FROM smart_replenishment_info) ");
            ORDER_BY(" all_available_day DESC");
        }}.toString();
    }
}
