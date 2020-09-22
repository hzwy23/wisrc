package com.wisrc.replenishment.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Date;

public class FbaReplenishmentInfoSQL {

    public static String findInfoByCond(@Param("createBeginTime") final Date createBeginTime,
                                        @Param("createEndTime") final Date createEndTime,
                                        @Param("shopId") String shopId,
                                        @Param("warehouseId") String warehouseId,
                                        @Param("shipmentId") String shipmentId,
                                        @Param("fbaIds") String[] fbaIds,
                                        @Param("statusCd") String statusCd,
                                        @Param("fbaQueryIds") String[] fbaQueryIds) {
        return new SQL() {{
            SELECT("*");
            FROM("fba_replenishment_info");
            if (shopId != null && !shopId.isEmpty()) {
                WHERE("shop_id=#{shopId}");
            }
            if (warehouseId != null && !warehouseId.isEmpty()) {
                WHERE("warehouse_id=#{warehouseId}");
            }
            if (shipmentId != null && !shipmentId.isEmpty()) {
                WHERE("shipment_id=#{shipmentId}");
            }
            if (createBeginTime != null && !"".equals(createBeginTime)) {
                WHERE("date_format(create_time,'%Y-%m-%d') >= date_format(#{createBeginTime},'%Y-%m-%d')");
            }
            if (createEndTime != null && !"".equals(createEndTime)) {
                WHERE("date_format(create_time,'%Y-%m-%d') <= date_format(#{createEndTime},'%Y-%m-%d')");
            }
            if (fbaIds != null && !(fbaIds.length == 0)) {
                WHERE("fba_replenishment_id IN (" + idsToStr(fbaIds) + ")");
            }
            if (statusCd != null && !statusCd.isEmpty()) {
                WHERE("status_cd=#{statusCd}");
            }
            if (fbaQueryIds != null && !(fbaQueryIds.length == 0)) {
                WHERE("fba_replenishment_id IN (" + idsToStr(fbaQueryIds) + ")");
            } else {
                WHERE("1=0");
            }
            ORDER_BY("create_time DESC");
        }}.toString();
    }

    public static String findFbaByBatchAndId(@Param("fbaReplenishmentId") String fbaReplenishmentId, @Param("batchNumber") String batchNumber) {
        return new SQL() {{
            SELECT("fba_replenishment_Id");
            FROM("fba_replenishment_info");
            if (fbaReplenishmentId != null && !fbaReplenishmentId.isEmpty()) {
                WHERE("(fba_replenishment_Id like concat('%',#{fbaReplenishmentId},'%') or batch_number like concat('%',#{batchNumber},'%'))");
            }
           /* if (batchNumber != null && !batchNumber.isEmpty()) {
                WHERE("batch_number like concat('%',#{batchNumber},'%')");
            }*/
        }}.toString();
    }

    public static String findFbaByWaybillId(String waybillId) {
        return new SQL() {{
            SELECT("fba_replenishment_Id");
            FROM("waybill_replenishment_rel");
            if (waybillId != null && !waybillId.isEmpty()) {
                WHERE("waybill_id like concat('%',#{waybillId},'%')");
            }
        }}.toString();
    }

    public static String idsToStr(String[] ids) {
        String str = "";
        String endstr = "";
        for (int i = 0; i < ids.length; i++) {
            str = "'" + ids[i] + "'";
            if (i < ids.length - 1) {
                str += ",";
            }
            endstr += str;
        }
        return endstr;
    }

    public static String findAll(@Param("createBeginTime") final Date createBeginTime,
                                 @Param("createEndTime") final Date createEndTime,
                                 @Param("shopId") String shopId,
                                 @Param("warehouseId") String warehouseId,
                                 @Param("shipmentId") String shipmentId,
                                 @Param("statusCd") String statusCd) {
        return new SQL() {{
            SELECT("*");
            FROM("fba_replenishment_info");
            if (shopId != null && !shopId.isEmpty()) {
                WHERE("shop_id=#{shopId}");
            }
            if (warehouseId != null && !warehouseId.isEmpty()) {
                WHERE("warehouse_id=#{warehouseId}");
            }
            if (shipmentId != null && !shipmentId.isEmpty()) {
                WHERE("shipment_id=#{shipmentId}");
            }
            if (createBeginTime != null && !"".equals(createBeginTime)) {
                WHERE("date_format(create_time,'%Y-%m-%d') >= date_format(#{createBeginTime},'%Y-%m-%d')");
            }
            if (createEndTime != null && !"".equals(createEndTime)) {
                WHERE("date_format(create_time,'%Y-%m-%d') <= date_format(#{createEndTime},'%Y-%m-%d')");
            }
            if (statusCd != null && !statusCd.isEmpty()) {
                WHERE("status_cd=#{statusCd}");
            }

            ORDER_BY("create_time DESC");
        }}.toString();
    }
}
