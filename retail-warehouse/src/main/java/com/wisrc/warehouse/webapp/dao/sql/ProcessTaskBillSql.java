package com.wisrc.warehouse.webapp.dao.sql;

import com.wisrc.warehouse.webapp.utils.SQLUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class ProcessTaskBillSql {

    public static String search(@Param("processStartDate") final String processDateBegin,
                                @Param("processEndDate") final String processDateEnd,
                                @Param("processLaterSku") final String processLaterSku,
                                @Param("statusCd") final int statusCd,
                                @Param("warehouseId") final String warehouseId,
                                @Param("createUser") final String createUser) {

        return new SQL() {{
            SELECT("process_task_id,status_cd, process_date, process_later_sku, process_num, warehouse_id, batch, remark, create_user ");
            FROM("process_task_bill");
            if (processDateBegin != null) {
                WHERE("date(process_date) >= #{processStartDate}");
            }
            if (processDateEnd != null) {
                WHERE("date(process_date) <= #{processEndDate}");
            }
            if (processLaterSku != null) {
                WHERE("process_later_sku like concat ('%',#{processLaterSku},'%')");
            }
            if (statusCd != 0) {
                WHERE("status_cd = #{statusCd}");
            }
            if (warehouseId != null) {
                WHERE("warehouse_id like concat ('%',#{warehouseId},'%')");
            }
            if (createUser != null) {
                WHERE("create_user like concat ('%',#{createUser},'%')");
            }
            ORDER_BY("process_task_id desc");
        }}.toString();
    }

    public static String searchByCond(@Param("processStartDate") final String processDateBegin,
                                      @Param("processEndDate") final String processDateEnd,
                                      @Param("processLaterSku") final String processLaterSku,
                                      @Param("statusCd") final int statusCd,
                                      @Param("warehouseId") final String warehouseId,
                                      @Param("createUser") final String createUser,
                                      @Param("skuIds") List<String> skuIds) {

        return new SQL() {{
            SELECT("process_task_id,status_cd, process_date, process_later_sku, process_num, warehouse_id, batch, remark, create_user ");
            FROM("process_task_bill");
            if (processDateBegin != null) {
                WHERE("date(process_date) >= #{processStartDate}");
            }
            if (processDateEnd != null) {
                WHERE("date(process_date) <= #{processEndDate}");
            }
            if (processLaterSku != null) {
                WHERE("process_later_sku like concat ('%',#{processLaterSku},'%')");
            }
            if (statusCd != 0) {
                WHERE("status_cd = #{statusCd}");
            }
            if (warehouseId != null) {
                WHERE("warehouse_id like concat ('%',#{warehouseId},'%')");
            }
            if (createUser != null) {
                WHERE("create_user like concat ('%',#{createUser},'%')");
            }
            if (CollectionUtils.isNotEmpty(skuIds)) {
                WHERE(SQLUtil.forUtil("process_later_sku", skuIds));
            }
            ORDER_BY("process_task_id desc");
        }}.toString();
    }
}



