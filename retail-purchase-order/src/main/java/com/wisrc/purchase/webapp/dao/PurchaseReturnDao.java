package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.GatherPurchaseReturnEntity;
import com.wisrc.purchase.webapp.entity.PurchaseReturnDetailsEntity;
import com.wisrc.purchase.webapp.entity.PurchaseReturnInfoEntity;
import com.wisrc.purchase.webapp.entity.PurchaseReturnInfoNewEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseReturnDao {

    @Select(" SELECT return_bill\n" +
            " FROM purchase_return_info\n" +
            " WHERE random_value = #{randomValue}")
    String findId(String randomValue);


    @Insert(" INSERT INTO\n" +
            "  purchase_return_info\n" +
            "  (\n" +
            "    return_bill,\n" +
            "    create_date,\n" +
            "    supplier_id,\n" +
            "    employee_id,\n" +
            "    warehouse_id,\n" +
            "    order_id,\n" +
            "    remark,\n" +
            "    delete_status,\n" +
            "    create_user,\n" +
            "    create_time,\n" +
            "    modify_user,\n" +
            "    modify_time,\n" +
            "    status_cd," +
            "    pack_warehouse_id,\n" +
            "    random_value," +
            "    status_modify_time" +
            "  )\n" +
            "VALUES (\n" +
            "  #{returnBill},\n" +
            "  #{createDate},\n" +
            "  #{supplierId},\n" +
            "  #{employeeId},\n" +
            "  #{warehouseId},\n" +
            "  #{orderId},\n" +
            "  #{remark},\n" +
            "  0,\n" +
            "  #{createUser},\n" +
            "  #{createTime},\n" +
            "  #{modifyUser},\n" +
            "  #{createTime},\n" +
            "  1," +
            "  #{packWarehouseId}," +
            "  #{returnBill}," +
            "  #{createTime}" +
            ") ")
    void insertPRI(PurchaseReturnInfoEntity pRIEntity);

    @Insert(" INSERT INTO purchase_return_details\n" +
            "(\n" +
            "  uuid,\n" +
            "  return_bill,\n" +
            "  sku_id,\n" +
            "  return_quantity,\n" +
            "  spare_quantity,\n" +
            "  batch_number,\n" +
            "  unit_price_without_tax,\n" +
            "  amount_without_tax,\n" +
            "  tax_rate,\n" +
            "  unit_price_with_tax,\n" +
            "  amount_with_tax\n" +
            ")\n" +
            "VALUES\n" +
            "  (\n" +
            "    #{uuid},\n" +
            "    #{returnBill},\n" +
            "    #{skuId},\n" +
            "    #{returnQuantity},\n" +
            "    #{spareQuantity},\n" +
            "    #{batchNumber},\n" +
            "    #{unitPriceWithoutTax},\n" +
            "    #{amountWithoutTax},\n" +
            "    #{taxRate},\n" +
            "    #{unitPriceWithTax},\n" +
            "    #{amountWithTax}\n" +
            "  ) ")
    void insertPRD(PurchaseReturnDetailsEntity pRDEntity);

    @Update(" UPDATE\n" +
            "  purchase_return_info\n" +
            "SET\n" +
            "  create_date   = #{createDate},\n" +
            "  supplier_id   = #{supplierId},\n" +
            "  employee_id   = #{employeeId},\n" +
            "  warehouse_id  = #{warehouseId},\n" +
            "  order_id      = #{orderId},\n" +
            "  remark        = #{remark},\n" +
            "  modify_user   = #{modifyUser},\n" +
            "  modify_time   = #{modifyTime}, " +
            "  pack_warehouse_id = #{packWarehouseId}" +
            " where  return_bill = #{returnBill} "
    )
    void updatePRI(PurchaseReturnInfoEntity pRIEntity);

    @Select(" SELECT\n" +
            "  return_bill,\n" +
            "  create_date,\n" +
            "  supplier_id,\n" +
            "  employee_id,\n" +
            "  warehouse_id,\n" +
            "  order_id,\n" +
            "  remark,\n" +
            "  delete_status,\n" +
            "  create_user,\n" +
            "  create_time,\n" +
            "  modify_user,\n" +
            "  modify_time,\n" +
            "  status_cd,\n" +
            "  pack_warehouse_id," +
            "  status_modify_time" +
            " FROM purchase_return_info\n" +
            " WHERE\n" +
            "  delete_status = 0\n" +
            "  AND\n" +
            "  return_bill = #{returnBill} ")
    PurchaseReturnInfoEntity getPRI(String returnBill);

    @Select("select return_bill, create_date, supplier_id, employee_id, warehouse_id, order_id, remark, delete_status, create_user, create_time, " +
            "modify_user, modify_time, pack_warehouse_id, status_cd, serial_number, random_value, status_modify_time from purchase_return_info where random_value=#{returnBill}")
    PurchaseReturnInfoEntity getPRIEntity(String returnBill);

    @Select("select return_bill, create_date, supplier_id, employee_id, warehouse_id, order_id, remark, delete_status, create_user, create_time, " +
            "modify_user, modify_time, pack_warehouse_id, status_cd, serial_number, random_value, status_modify_time from purchase_return_info where random_value=#{returnBill}")
    PurchaseReturnInfoEntity getPRIEntityByReturnBill(String returnBill);

    @Select(" SELECT\n" +
            "  return_bill,\n" +
            "  create_date,\n" +
            "  supplier_id,\n" +
            "  employee_id,\n" +
            "  warehouse_id,\n" +
            "  order_id,\n" +
            "  remark,\n" +
            "  delete_status,\n" +
            "  create_user,\n" +
            "  create_time,\n" +
            "  modify_user,\n" +
            "  modify_time,\n" +
            "  status_cd,\n" +
            "  pack_warehouse_id," +
            "  status_modify_time" +
            " FROM purchase_return_info\n" +
            " WHERE\n" +
            "  delete_status = 0\n" +
            "  AND\n" +
            "  order_id = #{orderId} ")
    List<PurchaseReturnInfoEntity> getPurchaseReturnListByOrderId(String orderId);

    @Select(" SELECT\n" +
            "  uuid,\n" +
            "  return_bill,\n" +
            "  sku_id,\n" +
            "  return_quantity,\n" +
            "  spare_quantity,\n" +
            "  batch_number,\n" +
            "  unit_price_without_tax,\n" +
            "  amount_without_tax,\n" +
            "  tax_rate,\n" +
            "  unit_price_with_tax,\n" +
            "  amount_with_tax,\n" +
            "  delete_status\n" +
            " FROM purchase_return_details\n" +
            " WHERE\n" +
            "  return_bill = #{returnBill} ")
    List<PurchaseReturnDetailsEntity> getPRD(String returnBill);

    @Update(" UPDATE\n" +
            "  purchase_return_info\n" +
            "SET\n" +
            " delete_status          = 1\n" +
            "WHERE return_bill = #{returnBill} ")
    void deletePRI(String returnBill);

    @Update("update purchase_return_info set status_cd = #{statusCd}, status_modify_time = #{statusModifyTime} where return_bill = #{returnBill} ")
    void changeStatus(@Param("statusCd") Integer statusCd, @Param("statusModifyTime") String statusModifyTime, @Param("returnBill") String returnBill);

    @Select(" <script>" +
            " SELECT\n" +
            "  return_bill,\n" +
            "  create_date,\n" +
            "  supplier_id,\n" +
            "  employee_id,\n" +
            "  warehouse_id,\n" +
            "  order_id,\n" +
            "  remark,\n" +
            "  delete_status,\n" +
            "  create_user,\n" +
            "  create_time,\n" +
            "  modify_user,\n" +
            "  modify_time,\n" +
            "  status_cd," +
            "  status_modify_time," +
            "  pack_warehouse_id" +
            " FROM purchase_return_info\n" +
            " WHERE\n" +
            "  delete_status = 0\n" +
            "            <if test = 'returnBill!=null'>\n" +
            "               AND return_bill LIKE concat('%', #{returnBill}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'supplierId!=null'>\n" +
            "               AND supplier_id LIKE concat('%', #{supplierId}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'employeeId!=null'>\n" +
            "               AND employee_id LIKE concat('%', #{employeeId}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'warehouseId!=null'>\n" +
            "               AND warehouse_id LIKE concat('%', #{warehouseId}, '%') " +
            "           </if>\n" +
            "            <if test = 'createDateStart!=null'>\n" +
            "              AND create_date   &gt;= #{createDateStart}\n" +
            "           </if>\n" +
            "            <if test = 'createDateEnd!=null'>\n" +
            "              AND create_date  &lt;= #{createDateEnd}\n" +
            "          </if>\n" +
            "            <if test = 'statusCd != null'>\n" +
            "              AND status_cd  &lt;= #{statusCd}\n" +
            "          </if>\n" +
            "          order BY create_date DESC, return_bill DESC" +
            " </script> ")
    List<PurchaseReturnInfoEntity> fuzzy(PurchaseReturnInfoEntity pRIEntity);

    @Select(" SELECT\n" +
            "  a.return_bill,\n" +
            "  a.create_date,\n" +
            "  a.supplier_id,\n" +
            "  a.employee_id,\n" +
            "  a.warehouse_id,\n" +
            "  a.order_id,\n" +
            "  a.remark,\n" +
            "  a.delete_status,\n" +
            "  a.create_user,\n" +
            "  a.create_time,\n" +
            "  a.modify_user,\n" +
            "  a.modify_time,\n" +
            "  a.status_cd,\n" +
            "  a.status_modify_time,\n" +
            "  a.pack_warehouse_id,\n" +
            "  b.uuid,\n" +
            "  b.return_bill,\n" +
            "  b.sku_id,\n" +
            "  b.return_quantity,\n" +
            "  b.spare_quantity,\n" +
            "  b.batch_number,\n" +
            "  b.unit_price_without_tax,\n" +
            "  b.amount_without_tax,\n" +
            "  b.tax_rate,\n" +
            "  b.unit_price_with_tax,\n" +
            "  b.amount_with_tax\n" +
            " FROM\n" +
            "  purchase_return_info a\n" +
            "  LEFT JOIN\n" +
            "  purchase_return_details b\n" +
            "    ON\n" +
            "      b.delete_status = 0\n" +
            "      AND\n" +
            "      a.return_bill = b.return_bill\n" +
            " WHERE\n" +
            "  a.delete_status = 0\n" +
            "  AND\n" +
            "  a.return_bill IN ${returnBill} " +
            "order BY  a.return_bill DESC,a.create_date DESC")
    List<GatherPurchaseReturnEntity> getAllPR(Map paraMap);

    @Delete("delete from purchase_return_details where return_bill = #{returnBill}")
    void realDeletePRD(String returnBill);

    @Select("select return_bill from purchase_purchase_return_info where sku_id in (${skuIds}) group by return_bill")
    List<String> getReturnBillId(@Param("skuIds") String skuIds);

    @Select(" <script>" +
            " SELECT\n" +
            "  return_bill,\n" +
            "  create_date,\n" +
            "  supplier_id,\n" +
            "  employee_id,\n" +
            "  warehouse_id,\n" +
            "  order_id,\n" +
            "  remark,\n" +
            "  delete_status,\n" +
            "  create_user,\n" +
            "  create_time,\n" +
            "  modify_user,\n" +
            "  modify_time,\n" +
            "  status_cd," +
            "  status_modify_time," +
            "  pack_warehouse_id" +
            " FROM purchase_return_info\n" +
            " WHERE\n" +
            "  delete_status = 0\n" +
            "            <if test = 'returnBill!=null'>\n" +
            "               AND return_bill LIKE concat('%', #{returnBill}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'supplierId!=null'>\n" +
            "               AND supplier_id LIKE concat('%', #{supplierId}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'employeeId!=null'>\n" +
            "               AND employee_id LIKE concat('%', #{employeeId}, '%')\n" +
            "           </if>\n" +
            "            <if test = 'warehouseId!=null'>\n" +
            "               AND warehouse_id LIKE concat('%', #{warehouseId}, '%') " +
            "           </if>\n" +
            "            <if test = 'createDateStart!=null'>\n" +
            "              AND create_date   &gt;= #{createDateStart}\n" +
            "           </if>\n" +
            "            <if test = 'createDateEnd!=null'>\n" +
            "              AND create_date  &lt;= #{createDateEnd}\n" +
            "          </if>\n" +
            "            <if test = 'statusCd != null'>\n" +
            "              AND status_cd  &lt;= #{statusCd}\n" +
            "          </if>\n" +
            "           <if test = 'returnBills!=null'>\n" +
            "               AND return_bill in (${returnBills})\n" +
            "           </if>\n" +
            "           <if test = 'supplierIds!=null'>\n" +
            "               AND supplier_id in (${supplierIds})\n" +
            "           </if>\n" +
            "            <if test = 'orderId!=null'>\n" +
            "               AND order_id LIKE concat('%', #{orderId}, '%')\n" +
            "           </if>\n" +
            "          order BY create_date DESC, return_bill DESC" +
            " </script> ")
    List<PurchaseReturnInfoEntity> fuzzyNew(PurchaseReturnInfoNewEntity pRIEntity);

    @Select(" <script>" +
            " SELECT\n" +
            "  return_bill" +
            " FROM purchase_purchase_return_info\n" +
            " WHERE\n" +
            "  1=1 = 0\n" +
            "            <if test = 'skuIds!=null'>\n" +
            "               AND skuIds in (${skuIds})\n" +
            "           </if>\n" +
            "            <if test = 'skuId!=null'>\n" +
            "               AND sku_id LIKE concat('%', #{skuId}, '%')\n" +
            "           </if>\n" +
            "          group by return_bill " +
            "          order BY create_date DESC, return_bill DESC" +
            " </script> ")
    List<String> getReturnBillIdAndSkuId(@Param("skuIds") String skuIds, @Param("skuId") String skuId);

    @Select("select return_bill from purchase_purchase_return_info where  sku_id like concat('%', #{skuId}, '%') group by return_bill")
    List<String> getReturnBillIdBySkuId(String skuId);
}
