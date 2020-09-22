package com.wisrc.replenishment.webapp.dao.sql;

import com.wisrc.replenishment.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class FbaMskuInfoSQL {


    public static String findMskuByIds(@Param("fbaReplenishmentIds") String[] fbaReplenishmentIds) {
        return new SQL() {{
            SELECT("replenishment_commodity_id,commodity_id,fba_replenishment_id,msku_id,replenishment_quantity,delivery_number,modify_user,modify_time,delete_status");
            FROM("replenishment_msku_info");
            if (null != fbaReplenishmentIds && fbaReplenishmentIds.length != 0) {
                WHERE("fba_replenishment_id IN (" + SQLUtil.idsToStr(fbaReplenishmentIds) + ")");
            }
        }}.toString();
    }

    public static String getMskuInfo(@Param("fbaReplenishmentIds") String[] fbaReplenishmentIds) {
        return new SQL() {{
            SELECT("replenishment_commodity_id,fba_replenishment_id,msku_id,replenishment_quantity,delivery_number,modify_user,modify_time,delete_status");
            FROM("replenishment_msku_info");
            WHERE("fba_replenishment_id IN (" + SQLUtil.idsToStr(fbaReplenishmentIds) + ")");
        }}.toString();
    }

    public static String findFbaByCommIdList(@Param("commodityIds") String commodityIds) {
        return new SQL() {{
            SELECT("fba_replenishment_Id");
            FROM("replenishment_msku_info");
            if (commodityIds == null) {
                WHERE(" 1 =  0");
            } else {
                WHERE("commodity_id in " + commodityIds);
            }
        }}.toString();
    }

    public static String findFbaByCommIdOnwayInfo(@Param("commodityIds") String commodityIds) {
        return new SQL() {{
            SELECT("commodity_id, msku_id, shop_id, delivery_number_total, sign_in_quantity_total, under_way_quantity");
            FROM("v_replenishment_msku_agg");
            if (commodityIds == null) {
                WHERE(" 1 =  0");
            } else {
                WHERE("commodity_id in " + commodityIds);
            }
        }}.toString();
    }
}
