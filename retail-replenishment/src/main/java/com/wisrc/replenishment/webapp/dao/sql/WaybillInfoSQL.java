package com.wisrc.replenishment.webapp.dao.sql;

import com.wisrc.replenishment.webapp.utils.SQLUtil;
import com.wisrc.replenishment.webapp.query.waybillInfo.WayBillInfoQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Date;
import java.util.List;

public class WaybillInfoSQL {
    public static int using = 0;

    public static String search(@Param("waybillOrderDateBegin") final Date waybillOrderDateBegin,
                                @Param("waybillOrderDateEnd") final Date waybillOrderDateEnd,
                                @Param("offerId") final List<String> offerIds,
                                @Param("customsCd") final Integer customsCd,
                                @Param("warehouseId") final String warehouseId,
                                @Param("logisticsTypeCd") final Integer logisticsTypeCd,
                                @Param("Keyword") final String Keyword,
                                @Param("waybillId") final String waybillId,
                                @Param("batchNumber") final String batchNumber,
                                @Param("fbaReplenishmentId") final String fbaReplenishmentId,
                                @Param("mskuListStr") final String mskuListStr,
                                @Param("isLackLogistics") final String isLackLogistics,
                                @Param("isLackShipment") final String isLackShipment,
                                @Param("isLackCustomsDeclare") final String isLackCustomsDeclare,
                                @Param("isLackCustomsClearance") final String isLackCustomsClearance) {
        return new SQL() {{
            SELECT("waybill_id,waybill_order_date,offer_id,estimate_date, logistics_id,sign_in_date,logistics_type_cd,warehouse_id,is_lack_shipment,is_lack_logistics,is_lack_customs_declare,is_lack_customs_clearance,delivery_number,sign_number,replenishment_number,amazon_warehouse_address,amazon_warehouse_zipcode,warehouse_code");
            FROM("v_waybill_info wi");
            WHERE("delete_status = 0");
            if (waybillOrderDateBegin != null) {
                WHERE("waybill_order_date >= #{waybillOrderDateBegin}");
            }

            if (waybillOrderDateEnd != null) {
                WHERE("waybill_order_date <= #{waybillOrderDateEnd}");
            }

            if (offerIds != null && offerIds.size() > 0) {
                WHERE(SQLUtil.whereIn("offer_id", offerIds));
            }

            if (customsCd != null) {
                WHERE("customs_type_cd = #{customsCd}");
            }
            if (warehouseId != null) {
                WHERE("warehouse_id = #{warehouseId}");
            }
            if (waybillId != null) {
                WHERE("waybill_id like concat('%',#{waybillId},'%')");
            }
            if (batchNumber != null) {
                WHERE("batch_number like concat('%',#{batchNumber},'%')");
            }
            if (fbaReplenishmentId != null) {
                WHERE("fba_replenishment_id like concat('%',#{fbaReplenishmentId},'%')");
            }
            if (logisticsTypeCd != null) {
                WHERE("logistics_type_cd = #{logisticsTypeCd}");
            }
            if (isLackLogistics != null || isLackShipment != null || isLackCustomsDeclare != null || isLackCustomsClearance != null) {
                WHERE("logistics_type_cd < 4");
                if (isLackLogistics != null) {//不含亚马逊自提的 代码2
                    WHERE("is_lack_logistics = #{isLackLogistics} AND exists( SELECT 1 FROM v_waybill_fba_replenishment_info wfri WHERE wfri.pickup_type_cd != 2 AND wi.waybill_id = wfri.waybill_id )");
                }
                if (isLackShipment != null) {//and left(warehouse_id,1) not in ('A','F')
                    WHERE("is_lack_shipment = #{isLackShipment} and left(warehouse_id,1) not in ('A','F') ");
                }
                if (isLackCustomsDeclare != null) {
                    WHERE("is_lack_customs_declare = #{isLackCustomsDeclare} and customs_type_cd = 1");
                }
                if (isLackCustomsClearance != null) {  //缺少清关发票的修改为不含海外仓，代码B
                    WHERE("is_lack_customs_clearance = #{isLackCustomsClearance} and left(warehouse_id,1) != 'B'");
                }
            }
            if (Keyword != null) {

                if (mskuListStr != null) {
                    WHERE("(fba_replenishment_id like concat('%',#{Keyword},'%') OR logistics_id like concat('%',#{Keyword},'%') OR batch_number like concat('%',#{Keyword},'%') OR waybill_id like concat('%',#{Keyword},'%') or msku_id in " + mskuListStr + " )");
                } else {
                    WHERE("(fba_replenishment_id like concat('%',#{Keyword},'%') OR logistics_id like concat('%',#{Keyword},'%') OR batch_number like concat('%',#{Keyword},'%') OR waybill_id like concat('%',#{Keyword},'%'))");
                }
            }
            GROUP_BY("waybill_id");
            ORDER_BY("waybill_id DESC");
        }}.toString();
    }

    public static String toInString(List ids) {
        String str = "";
        String endstr = "";
        for (int i = 0; i < ids.size(); i++) {
            str = "'" + ids.get(i) + "'";
            if (i < ids.size() - 1) {
                str += ",";
            }
            endstr += str;
        }
        return endstr;
    }

    public static String waybillExcel(WayBillInfoQuery wayBillInfoQuery) {
        return new SQL() {{
            SELECT("wi.waybill_id", "logistics_id", "wi.offer_id", "waybill_order_date", "delivery_time", "estimate_date", "sign_in_date", "exception_type_desc", "total_weight",
                    "unit_price", "annex_cost", "other_fee", "discounted_amount", "total_cost", "weigh_type_name", "wi.remark", "batch_number");
            FROM("waybill_info AS wi");
            LEFT_OUTER_JOIN("freight_estimate_info AS fei ON fei.waybill_id = wi.waybill_id", "waybill_replenishment_rel AS wrr ON wrr.waybill_id = wi.waybill_id");
            LEFT_OUTER_JOIN("weight_type_attr AS wta ON fei.weigh_type_cd = wta.weigh_type_cd", "fba_replenishment_info AS fri ON wrr.fba_replenishment_id = fri.fba_replenishment_id");
            LEFT_OUTER_JOIN("waybill_exception_info AS wei ON wei.waybill_id = wi.waybill_id");
            WHERE("fei.delete_status=0");
            if (wayBillInfoQuery.getWaybillOrderDateBegin() != null) {
                WHERE("waybill_order_date >= #{waybillOrderDateBegin}");
            }

            if (wayBillInfoQuery.getWaybillOrderDateEnd() != null) {
                WHERE("waybill_order_date <= #{waybillOrderDateEnd}");
            }

            if (wayBillInfoQuery.getOfferIds() != null && wayBillInfoQuery.getOfferIds().size() > 0) {
                WHERE(SQLUtil.whereIn("wi.offer_id", wayBillInfoQuery.getOfferIds()));
            }

            if (wayBillInfoQuery.getCustomsCd() != null) {
                WHERE("fei.customs_type_cd=#{customsCd}");
            }
            if (wayBillInfoQuery.getWarehouseId() != null) {
                WHERE("fri.warehouse_id=#{warehouseId}");
            }
            if (wayBillInfoQuery.getWaybillId() != null) {
                WHERE("wi.waybill_id like concat('%',#{waybillId},'%')");
            }
            if (wayBillInfoQuery.getBatchNumber() != null) {
                WHERE("fri.batch_number like concat('%',#{batchNumber},'%')");
            }
            if (wayBillInfoQuery.getFbaReplenishmentId() != null) {
                WHERE("fri.fba_replenishment_id like concat('%',#{fbaReplenishmentId},'%')");
            }
            if (wayBillInfoQuery.getLogisticsTypeCd() != null) {
                WHERE("wi.logistics_type_cd=#{logisticsTypeCd}");
            }
            if (wayBillInfoQuery.getKeyword() != null) {
                String mskuIdList = "";
                if (wayBillInfoQuery.getMskuIds() != null && wayBillInfoQuery.getMskuIds().size() > 0) {
                    mskuIdList = " OR rmi.msku_id IN (" + toInString(wayBillInfoQuery.getMskuIds()) + ")";
                }
                WHERE("(wi.logistics_id=#{Keyword} OR fri.batch_number = #{Keyword}" + mskuIdList + ")");

            }
            if (wayBillInfoQuery.getIsLackLogistics() != null) {
                WHERE("is_lack_logistics=#{isLackLogistics}");
            }
            if (wayBillInfoQuery.getIsLackShipment() != null) {
                WHERE("is_lack_shipment=#{isLackShipment}");
            }
            if (wayBillInfoQuery.getIsLackCustomsDeclare() != null) {
                WHERE("is_lack_customs_declare=#{isLackCustomsDeclare} and customs_type_cd = 1");
            }
            if (wayBillInfoQuery.getIsLackCustomsClearanc() != null) {
                WHERE("is_lack_customs_clearance=#{isLackCustomsClearance}");
            }
            WHERE("wi.delete_status =" + using);
            GROUP_BY("waybill_id");
            ORDER_BY("waybill_id ASC");
        }}.toString();
    }
}
