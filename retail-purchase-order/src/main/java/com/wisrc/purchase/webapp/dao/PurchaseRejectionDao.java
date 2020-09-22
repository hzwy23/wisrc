package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.GatherPurchaseRejectionEntity;
import com.wisrc.purchase.webapp.entity.PurchaseRejectionDetailsEntity;
import com.wisrc.purchase.webapp.entity.PurchaseRejectionInfoEntity;
import com.wisrc.purchase.webapp.entity.PurchaseRejectionInfoNewEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseRejectionDao {
    @Insert(" INSERT INTO purchase_rejection_info\n" +
            "(\n" +
            "  rejection_id,\n" +
            "  rejection_date,\n" +
            "  handle_user,\n" +
            "  inspection_id,\n" +
            "  supplier_cd,\n" +
            "  supplier_delivery_num,\n" +
            "  remark,\n" +
            "  delete_status,\n" +
            "  order_id,\n" +
            "  create_time," +
            "  create_user," +
            "  modify_user," +
            "  modify_time," +
            "  status_modify_time," +
            "  random_value" +
            ")\n" +
            "VALUES\n" +
            "  (\n" +
            "    #{rejectionId},\n" +
            "    #{rejectionDate},\n" +
            "    #{handleUser},\n" +
            "    #{inspectionId},\n" +
            "    #{supplierCd},\n" +
            "    #{supplierDeliveryNum},\n" +
            "    #{remark},\n" +
            "    0,\n" +
            "    #{orderId},\n" +
            "    #{createTime}," +
            "    #{createUser}," +
            "    #{modifyUser}," +
            "    #{modifyTime}," +
            "    #{createTime}," +
            "    #{rejectionId}" +
            "  ) ")
    void insertPRI(PurchaseRejectionInfoEntity purchaseRejectionInfoEntity);

    @Insert("  insert into \n" +
            "  purchase_rejection_details\n" +
            "(\n" +
            "  uuid, \n" +
            "  rejection_id, \n" +
            "  sku_id, \n" +
            "  reject_quantity, \n" +
            "  spare_quantity, \n" +
            "  batch_number, \n" +
            "  unit_price_without_tax, \n" +
            "  amount_without_tax, \n" +
            "  tax_rate, \n" +
            "  unit_price_with_tax, \n" +
            "  amount_with_tax \n" +
            ") VALUES\n" +
            "  (\n" +
            "    #{uuid}, \n" +
            "    #{rejectionId}, \n" +
            "    #{skuId}, \n" +
            "    #{rejectQuantity}, \n" +
            "    #{spareQuantity}, \n" +
            "    #{batchNumber}, \n" +
            "    #{unitPriceWithoutTax}, \n" +
            "    #{amountWithoutTax}, \n" +
            "    #{taxRate}, \n" +
            "    #{unitPriceWithTax}, \n" +
            "    #{amountWithTax} \n" +
            "  ) ")
    void insertPRD(PurchaseRejectionDetailsEntity purchaseRejectionDetailsEntity);

    @Update(" UPDATE \n" +
            "  purchase_rejection_info\n" +
            " SET\n" +
            "  rejection_date        = #{rejectionDate},\n" +
            "  handle_user           = #{handleUser},\n" +
            "  inspection_id         = #{inspectionId},\n" +
            "  supplier_cd           = #{supplierCd},\n" +
            "  supplier_delivery_num = #{supplierDeliveryNum},\n" +
            "  remark                = #{remark},\n" +
            "  order_id              = #{orderId},\n" +
            "  modify_time           = #{modifyTime}," +
            "  modify_user           = #{modifyUser}" +
            " where  rejection_id    = #{rejectionId} ")
    void updatePRI(PurchaseRejectionInfoEntity purchaseRejectionInfoEntity);

    @Update(" UPDATE\n" +
            "  purchase_rejection_details\n" +
            "SET\n" +
            "  uuid                   = #{uuid},\n" +
            "  sku_id                 = #{skuId},\n" +
            "  reject_quantity        = #{rejectQuantity},\n" +
            "  spare_quantity         = #{spareQuantity},\n" +
            "  batch_number           = #{batchNumber},\n" +
            "  unit_price_without_tax = #{unitPriceWithoutTax},\n" +
            "  amount_without_tax     = #{amountWithoutTax},\n" +
            "  tax_rate               = #{taxRate},\n" +
            "  unit_price_with_tax    = #{unitPriceWithTax},\n" +
            "  amount_with_tax        = #{amountWithTax}\n" +
            "WHERE rejection_id = #{rejectionId} ")
    void updatePRD(PurchaseRejectionDetailsEntity purchaseRejectionDetailsEntity);

    @Update(" UPDATE purchase_rejection_info\n" +
            " SET delete_status = 1\n" +
            " WHERE rejection_id = #{rejectionId} ")
    void deletePRI(String rejectionId);

    @Delete(" delete from purchase_rejection_details\n" +
            " WHERE rejection_id = #{rejectionId} ")
    void deletePRDByRejectionId(String rejectionId);

    @Select(" SELECT\n" +
            "  rejection_id,\n" +
            "  rejection_date,\n" +
            "  handle_user,\n" +
            "  inspection_id,\n" +
            "  supplier_cd,\n" +
            "  supplier_delivery_num,\n" +
            "  remark,\n" +
            "  delete_status,\n" +
            "  order_id,\n" +
            "  create_time," +
            "  create_user," +
            "  modify_user," +
            "  modify_time," +
            "  status_cd," +
            "  status_modify_time" +
            " FROM purchase_rejection_info\n" +
            " WHERE\n" +
            "  delete_status = 0\n" +
            "  and\n" +
            "  rejection_id = #{rejectionId} ")
    PurchaseRejectionInfoEntity getPRI(String rejectionId);

    @Select(" SELECT\n" +
            "  uuid,\n" +
            "  rejection_id,\n" +
            "  sku_id,\n" +
            "  reject_quantity,\n" +
            "  spare_quantity,\n" +
            "  batch_number,\n" +
            "  unit_price_without_tax,\n" +
            "  amount_without_tax,\n" +
            "  tax_rate,\n" +
            "  unit_price_with_tax,\n" +
            "  amount_with_tax,\n" +
            "  delete_status\n" +
            " FROM purchase_rejection_details\n" +
            " WHERE\n" +
            "  rejection_id = #{rejectionId} ")
    List<PurchaseRejectionDetailsEntity> getPRD(String rejectionId);

    @Select(" <script>\n" +
            " SELECT\n" +
            "  rejection_id,\n" +
            "  rejection_date,\n" +
            "  handle_user,\n" +
            "  inspection_id,\n" +
            "  supplier_cd,\n" +
            "  supplier_delivery_num,\n" +
            "  remark,\n" +
            "  delete_status,\n" +
            "  order_id,\n" +
            "  create_time," +
            "  create_user," +
            "  modify_user," +
            "  modify_time," +
            "  status_cd," +
            "  status_modify_time" +
            " FROM purchase_rejection_info\n" +
            " WHERE\n" +
            "  delete_status = 0\n" +
            "           <if test = 'rejectionId!=null'>\n" +
            "             AND rejection_id LIKE concat('%', #{rejectionId}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'supplierCd!=null'>\n" +
            "              AND supplier_cd LIKE concat('%', #{supplierCd}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'inspectionId!=null'>\n" +
            "             AND inspection_id LIKE concat('%', #{inspectionId}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'supplierDeliveryNum!=null'>\n" +
            "              AND supplier_delivery_num LIKE concat('%', #{supplierDeliveryNum}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'handleUser!=null'>\n" +
            "              AND handle_user LIKE concat('%',#{handleUser}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'rejectionDateStart!=null'>\n" +
            "              AND rejection_date   &gt;= #{rejectionDateStart}\n" +
            "           </if>\n" +
            "            <if test = 'statusCd != null'>\n" +
            "              AND status_cd   &gt;= #{statusCd}\n" +
            "           </if>\n" +
            "            <if test = 'rejectionDateEnd!=null'>\n" +
            "              AND rejection_date  &lt;= #{rejectionDateEnd}\n" +
            "          </if>\n" +
            "          order BY rejection_date DESC , rejection_id DESC" +
            "      </script> ")
    List<PurchaseRejectionInfoEntity> fuzzy(PurchaseRejectionInfoEntity pRIEntity);

    @Select(" SELECT\n" +
            "  a.rejection_id,\n" +
            "  a.rejection_date,\n" +
            "  a.handle_user,\n" +
            "  a.inspection_id,\n" +
            "  a.supplier_cd,\n" +
            "  a.supplier_delivery_num,\n" +
            "  a.remark,\n" +
            "  a.order_id,\n" +
            "  b.uuid,\n" +
            "  b.sku_id,\n" +
            "  b.reject_quantity,\n" +
            "  b.spare_quantity,\n" +
            "  b.batch_number,\n" +
            "  b.unit_price_without_tax,\n" +
            "  b.amount_without_tax,\n" +
            "  b.tax_rate,\n" +
            "  b.unit_price_with_tax,\n" +
            "  b.amount_with_tax\n" +
            " FROM\n" +
            "  purchase_rejection_info a\n" +
            "  LEFT JOIN\n" +
            "  purchase_rejection_details b\n" +
            "    ON\n" +
            "      a.rejection_id = b.rejection_id\n" +
            " WHERE\n" +
            "  a.delete_status = 0\n" +
            "  AND\n" +
            "  a.rejection_id IN ${rejectionId} ")
    List<GatherPurchaseRejectionEntity> getDetail(Map papaMap);

    @Select("select rejection_id from purchase_rejection_info where random_value = #{randomValue}")
    String findId(@Param("randomValue") String randomValue);

    @Update("update purchase_rejection_info set status_cd = #{statusCd}, status_modify_time = #{statusModifyTime} where rejection_id = #{rejectionId}")
    void changeStatus(@Param("rejectionId") String rejectionId, @Param("statusCd") Integer statusCd, @Param("statusModifyTime") String statusModifyTime);

    @Select(" <script>" +
            " SELECT\n" +
            "  rejection_id" +
            " FROM purchase_purchase_return_info\n" +
            " WHERE\n" +
            "  1=1 = 0\n" +
            "            <if test = 'skuIds!=null'>\n" +
            "               AND skuIds in (${skuIds})\n" +
            "           </if>\n" +
            "            <if test = 'skuId!=null'>\n" +
            "               AND sku_id LIKE concat('%', #{skuId}, '%')\n" +
            "           </if>\n" +
            "          group by rejection_id " +
            "          order BY create_date DESC, rejection_id DESC" +
            " </script> ")
    List<String> getRejectBillIdAndSkuId(@Param("skuIds") String skuIds, @Param("skuId") String skuId);

    @Select("select rejection_id from purchase_rejection_details where sku_id in (${skuIds})")
    List<String> getRejectBillId(@Param("skuIds") String skuIds);

    @Select("select rejection_id from purchase_rejection_details where  sku_id like concat('%', #{skuId}, '%') group by rejection_id")
    List<String> getRejectBillIdBySkuId(String skuId);


    @Select(" <script>\n" +
            " SELECT\n" +
            "  rejection_id,\n" +
            "  rejection_date,\n" +
            "  handle_user,\n" +
            "  inspection_id,\n" +
            "  supplier_cd,\n" +
            "  supplier_delivery_num,\n" +
            "  remark,\n" +
            "  delete_status,\n" +
            "  order_id,\n" +
            "  create_time," +
            "  create_user," +
            "  modify_user," +
            "  modify_time," +
            "  status_cd," +
            "  status_modify_time" +
            " FROM purchase_rejection_info\n" +
            " WHERE\n" +
            "  delete_status = 0\n" +
            "           <if test = 'rejectionId!=null'>\n" +
            "             AND rejection_id LIKE concat('%', #{rejectionId}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'supplierCd!=null'>\n" +
            "              AND supplier_cd LIKE concat('%', #{supplierCd}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'inspectionId!=null'>\n" +
            "             AND inspection_id LIKE concat('%', #{inspectionId}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'supplierDeliveryNum!=null'>\n" +
            "              AND supplier_delivery_num LIKE concat('%', #{supplierDeliveryNum}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'handleUser!=null'>\n" +
            "              AND handle_user LIKE concat('%',#{handleUser}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'rejectionDateStart!=null'>\n" +
            "              AND rejection_date   &gt;= #{rejectionDateStart}\n" +
            "           </if>\n" +
            "            <if test = 'statusCd != null'>\n" +
            "              AND status_cd   &gt;= #{statusCd}\n" +
            "           </if>\n" +
            "            <if test = 'rejectionDateEnd!=null'>\n" +
            "              AND rejection_date  &lt;= #{rejectionDateEnd}\n" +
            "          </if>\n" +
            "           <if test = 'rejectBills!=null'>\n" +
            "               AND rejection_id in (${rejectBills})\n" +
            "           </if>\n" +
            "           <if test = 'supplierIds!=null'>\n" +
            "               AND supplier_cd in (${supplierIds})\n" +
            "           </if>\n" +
            "            <if test = 'orderId!=null'>\n" +
            "               AND order_id LIKE concat('%', #{orderId}, '%')\n" +
            "           </if>\n" +
            "          order BY rejection_date DESC , rejection_id DESC" +
            "      </script> ")
    List<PurchaseRejectionInfoEntity> fuzzyNew(PurchaseRejectionInfoNewEntity pRIEntity);
}
