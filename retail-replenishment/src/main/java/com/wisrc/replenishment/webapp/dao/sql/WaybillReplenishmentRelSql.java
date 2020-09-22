package com.wisrc.replenishment.webapp.dao.sql;

import com.wisrc.replenishment.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class WaybillReplenishmentRelSql {
    public static int using = 0;

    public static String getFbaByWaybill(@Param("waybillIds") List waybillIds) {
        return new SQL() {{
            SELECT("wrr.waybill_id", "shipment_id", "fri.create_time", "commodity_id", "rmi.replenishment_quantity", "rmi.delivery_number", "rmpi.number_of_boxes", "rmpi.outer_box_specification_len", "rmpi.outer_box_specification_width"
                    , "rmpi.outer_box_specification_height", "rmpi.packing_weight", "rmpi.packing_quantity", "fri.batch_number");
            FROM("waybill_replenishment_rel AS wrr");
            LEFT_OUTER_JOIN("fba_replenishment_info AS fri ON fri.fba_replenishment_id = wrr.fba_replenishment_id", "replenishment_msku_info AS rmi ON rmi.fba_replenishment_id = fri.fba_replenishment_id"
                    , "replenishment_msku_pack_info AS rmpi ON rmpi.replenishment_commodity_id = rmi.replenishment_commodity_id");
            WHERE(SQLUtil.whereIn("waybill_id", waybillIds));
            WHERE("rmpi.delete_status = 0");
        }}.toString();
    }
}
