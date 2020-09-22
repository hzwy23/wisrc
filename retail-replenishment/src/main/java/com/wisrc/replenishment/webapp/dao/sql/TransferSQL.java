package com.wisrc.replenishment.webapp.dao.sql;

import com.wisrc.replenishment.webapp.utils.SQLUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Administrator
 */
public class TransferSQL {

    public static String findByCond(@Param("warehouseStartId") String warehouseStartId,
                                    @Param("warehouseEndId") String warehouseEndId,
                                    @Param("startDate") String startDate,
                                    @Param("endDate") String endDate,
                                    @Param("shipmentId") String shipmentId,
                                    @Param("status") Integer status) {
        return new SQL() {{
            SELECT("transfer_order_cd,waybill_id, warehouse_start_id, warehouse_end_id,sub_warehouse_end_id, status_cd, offer_id, shipment_id, wh_label, remark, create_user, create_time, modify_user, modify_time, cancel_reason, delivery_date,sign_in_date, transfer_type_cd, random_value, serial_number");
            FROM("transfer_order_info");
            WHERE("1 = 1");
            if (StringUtils.isNotEmpty(warehouseStartId)) {
                WHERE("warehouse_start_id = #{warehouseStartId}");
            }
            if (StringUtils.isNotEmpty(warehouseEndId)) {
                WHERE("warehouse_end_id = #{warehouseEndId}");
            }
            if (StringUtils.isNotEmpty(startDate)) {
                WHERE("create_time >= #{startDate}");
            }
            if (StringUtils.isNotEmpty(endDate)) {
                WHERE("create_time <= #{endDate}");
            }
            if (StringUtils.isNotEmpty(shipmentId)) {
                WHERE("shipment_id = #{shipmentId}");
            }
            if (status != null) {
                WHERE("status_cd = #{status}");
            }
            ORDER_BY("transfer_order_cd desc");
        }}.toString();
    }

    public static String getSkuInfoByCd(@Param("transferOrderCd") String[] transferOrderCds) {
        return new SQL() {{
            SELECT("*");
            FROM("transfer_order_details");
            WHERE("transfer_order_cd IN (" + SQLUtil.idsToStr(transferOrderCds) + ")");
        }}.toString();
    }
}

