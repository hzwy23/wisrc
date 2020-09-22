package com.wisrc.purchase.webapp.dao.sql;

import com.wisrc.purchase.webapp.utils.SQLUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Date;
import java.util.List;

public class EntryWarehouseSQL {
    public static String findInfo(@Param("entryId") final String entryId,
                                  @Param("entryTimeBegin") final Date entryTimeBegin,
                                  @Param("entryTimeEnd") final Date entryTimeEnd,
                                  @Param("supplierDeliveryNum") String supplierDeliveryNum,
                                  @Param("inspectionId") final String inspectionId,
                                  @Param("entryUser") final String entryUser,
                                  @Param("warehouseId") final String warehouseId,
                                  @Param("supplierId") final String supplierId) {
        return new SQL() {{
            SELECT("entry_id,entry_time,entry_user,warehouse_id,inspection_id,supplier_cd,supplier_delivery_num,create_time,create_user,order_id,remark,modify_user,modify_time,delete_status, pack_warehouse_id");
            FROM("entry_warehouse_info");
            WHERE("delete_status = 0");
            if (entryId != null) {

                WHERE("entry_id like concat('%',#{entryId},'%')");
            }
            if (entryTimeBegin != null) {
                WHERE("entry_time >= #{entryTimeBegin}");
            }
            if (entryTimeEnd != null) {
                WHERE(" entry_time <= #{entryTimeEnd}");
            }
            if (supplierDeliveryNum != null) {
                WHERE(" supplier_delivery_num like concat('%',#{supplierDeliveryNum},'%')");
            }
            if (inspectionId != null) {
                WHERE(" inspection_id like concat('%',#{inspectionId},'%')");
            }
            if (entryUser != null) {
                WHERE(" entry_user = #{entryUser}");
            }

            if (warehouseId != null) {
                WHERE(" warehouse_id  = #{warehouseId}");
            }
            if (supplierId != null) {
                WHERE(" supplier_cd  = #{supplierId}");
            }
            ORDER_BY("entry_id DESC");
        }}.toString();
    }

    public static String findInfoIds(@Param("ids") String[] ids) {
        String str = "";
        String endstr = "";
        for (int i = 0; i < ids.length; i++) {
            str = "'" + ids[i] + "'";
            if (i < ids.length - 1) {
                str += ",";
            }
            endstr += str;
        }
        String finalEndstr = endstr;
        return new SQL() {{
            SELECT("entry_id,entry_time,entry_user,warehouse_id,inspection_id,supplier_cd,supplier_delivery_num,create_time,create_user,order_id,remark,modify_user,modify_time,delete_status, pack_warehouse_id");
            FROM("entry_warehouse_info");
            WHERE("delete_status = 0");
            WHERE("entry_id in (" + finalEndstr + ")");
            ORDER_BY("entry_time asc");
        }}.toString();
    }

    public static String select(@Param("ids") List<String> ids) {
        return new SQL() {
            {
                SELECT("uuid, entry_id, sku_id, entry_num, entry_frets, batch, unit_price_without_tax, amount_without_tax, tax_rate, unit_price_with_tax, amount_with_tax, create_time, create_user, delete_status");
                FROM("entry_warehouse_product_info");
                WHERE(SQLUtil.forUtil("entry_id", ids));
            }
        }.toString();
    }

    //    @Param("suppliers") String suppliers, @Param("skuIds") String skuIds, @Param("orderId")String orderId
    public static String findInfoNew(@Param("entryId") final String entryId,
                                     @Param("entryTimeBegin") final Date entryTimeBegin,
                                     @Param("entryTimeEnd") final Date entryTimeEnd,
                                     @Param("supplierDeliveryNum") String supplierDeliveryNum,
                                     @Param("inspectionId") final String inspectionId,
                                     @Param("entryUser") final String entryUser,
                                     @Param("warehouseId") final String warehouseId,
                                     @Param("skuIds") final String skuIds,
                                     @Param("orderId") final String orderId,
                                     @Param("skuId") final String skuId,
                                     @Param("suppliers") final String suppliers) {
        return new SQL() {{
            SELECT("entry_id,entry_time,entry_user,warehouse_id,inspection_id,supplier_cd,supplier_delivery_num,create_time,create_user,order_id,remark,modify_user,modify_time,delete_status, pack_warehouse_id");
            FROM("v_warehouse_product_info");
            WHERE("delete_status = 0");
            if (entryId != null) {

                WHERE("entry_id like concat('%',#{entryId},'%')");
            }
            if (entryTimeBegin != null) {
                WHERE("entry_time >= #{entryTimeBegin}");
            }
            if (entryTimeEnd != null) {
                WHERE(" entry_time <= #{entryTimeEnd}");
            }
            if (supplierDeliveryNum != null) {
                WHERE(" supplier_delivery_num like concat('%',#{supplierDeliveryNum},'%')");
            }
            if (inspectionId != null) {
                WHERE(" inspection_id like concat('%',#{inspectionId},'%')");
            }
            if (entryUser != null) {
                WHERE(" entry_user = #{entryUser}");
            }
            if (warehouseId != null) {
                WHERE(" warehouse_id  = #{warehouseId}");
            }
            if (skuId != null) {
                WHERE(" sku_id like concat('%',#{skuId},'%')");
            }
            if (orderId != null) {
                WHERE(" order_id like concat('%',#{orderId},'%')");
            }
            if (suppliers != null) {
                WHERE("supplier_cd in " + "(" + suppliers + ")");
            }
            if (skuIds != null) {
                WHERE("sku_id in " + "(" + skuIds + ")");
            }
            GROUP_BY("entry_id");
            ORDER_BY("entry_id DESC");
        }}.toString();
    }

    public static String findInfoNewExport(@Param("entryId") final String entryId,
                                           @Param("entryTimeBegin") final Date entryTimeBegin,
                                           @Param("entryTimeEnd") final Date entryTimeEnd,
                                           @Param("supplierDeliveryNum") String supplierDeliveryNum,
                                           @Param("inspectionId") final String inspectionId,
                                           @Param("entryUser") final String entryUser,
                                           @Param("warehouseId") final String warehouseId,
                                           @Param("skuIds") final String skuIds,
                                           @Param("orderId") final String orderId,
                                           @Param("skuId") final String skuId,
                                           @Param("suppliers") final String suppliers) {
        return new SQL() {{
            SELECT("entry_id,entry_time,entry_user,warehouse_id,inspection_id,supplier_cd,supplier_delivery_num,create_time,create_user,order_id,remark,modify_user,modify_time,delete_status, pack_warehouse_id,sku_id,entry_frets,batch,unit_price_without_tax,amount_without_tax,unit_price_with_tax,tax_rate,entry_num,amount_with_tax");
            FROM("v_warehouse_product_info");
            WHERE("delete_status = 0");
            if (entryId != null) {

                WHERE("entry_id like concat('%',#{entryId},'%')");
            }
            if (entryTimeBegin != null) {
                WHERE("entry_time >= #{entryTimeBegin}");
            }
            if (entryTimeEnd != null) {
                WHERE(" entry_time <= #{entryTimeEnd}");
            }
            if (supplierDeliveryNum != null) {
                WHERE(" supplier_delivery_num like concat('%',#{supplierDeliveryNum},'%')");
            }
            if (inspectionId != null) {
                WHERE(" inspection_id like concat('%',#{inspectionId},'%')");
            }
            if (entryUser != null) {
                WHERE(" entry_user = #{entryUser}");
            }
            if (warehouseId != null) {
                WHERE(" warehouse_id  = #{warehouseId}");
            }
            if (skuId != null) {
                WHERE(" sku_id like concat('%',#{skuId},'%')");
            }
            if (orderId != null) {
                WHERE(" order_id like concat('%',#{orderId},'%')");
            }
            if (suppliers != null) {
                WHERE("supplier_cd in " + "(" + suppliers + ")");
            }
            if (skuIds != null) {
                WHERE("sku_id in " + "(" + skuIds + ")");
            }
            GROUP_BY("entry_id");
            ORDER_BY("entry_id DESC");
        }}.toString();
    }
}
