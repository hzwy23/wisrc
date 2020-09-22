package com.wisrc.shipment.webapp.dao.sql;

import com.wisrc.shipment.webapp.entity.ChangeLableEnity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class LabelSql {
    public static String update(final ChangeLableEnity changeLableEnity) {
        return new SQL() {{
            UPDATE("shipment_change_label_basis_info");

            //条件写法.
            if (changeLableEnity.getSourceId() != null) {
                SET("source_id=#{sourceId}");
            }
            if (changeLableEnity.getCancelReason() != null) {
                SET("cancel_reason=#{cancelReason}");
            }
            if (changeLableEnity.getWarehouseId() != null) {
                SET("warehouse_id=#{warehouseId}");
            }
            if (changeLableEnity.getRemark() != null) {
                SET("remark=#{remark}");
            }
            if (changeLableEnity.getFnsku() != null) {
                SET("fnsku=#{fnsku}");
            }
            if (changeLableEnity.getModifyTime() != null) {
                SET("modify_time=#{modifyTime}");
            }
            if (changeLableEnity.getModifyUser() != null) {
                SET("modify_user=#{modifyUser}");
            }
            WHERE("change_label_id=#{changeLabelId}");
        }}.toString();
    }

    public static String findByCond(@Param("startTime") final String startTime,
                                    @Param("endTime") final String endTime,
                                    @Param("wareHouseId") final String wareHouseId,
                                    @Param("statusCd") int statusCd,
                                    @Param("keyword") final String keyword
    ) {
        return new SQL() {{
            SELECT("change_label_id, source_id, warehouse_id, remark, status_cd, cancel_reason, create_time, fnsku");
            FROM("shipment_change_label_basis_info");
            if (wareHouseId != null) {
                WHERE("warehouse_id=#{wareHouseId}");
            }
            if (statusCd != 0) {
                WHERE("status_cd=#{statusCd}");
            }
            if (keyword != null) {
                WHERE(" change_label_id like '" + '%' + keyword + '%' + "' " + "or" + " source_id like '" + '%' + keyword + '%' + "' " + "or" + " fnsku like '" + '%' + keyword + '%' + "'");
            }
            if (startTime != null) {
                WHERE("create_time>=#{startTime}");
            }
            if (endTime != null) {
                WHERE("create_time<=#{endTime}");
            }
            ORDER_BY("create_time DESC");
        }}.toString();
    }
}
