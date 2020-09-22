package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.ProductInfoEntity;
import com.wisrc.warehouse.webapp.entity.ReportLossStatementEntity;
import com.wisrc.warehouse.webapp.entity.ReportLossStatementStatusAttrEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseManageInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportLossStatementDao {
    @Insert(" INSERT INTO report_loss_statement (\n" +
            "   report_loss_statement_id,\n" +
            "   label_flag,\n" +
            "   report_loss_reason,\n" +
            "   annex_address,\n" +
            "   status_cd,\n" +
            "   apply_person_id,\n" +
            "   create_time,\n" +
            "   warehouse_id,\n" +
            "   review_time,\n" +
            "   cancel_reason,\n" +
            "   disagree_reason,\n" +
            "   review_person_id\n" +
            ")\n" +
            "VALUES\n" +
            " (\n" +
            "   #{reportLossStatementId},\n" +
            "   #{labelFlag},\n" +
            "   #{reportLossReason},\n" +
            "   #{annexAddress},\n" +
            "   #{statusCd},\n" +
            "   #{applyPersonId},\n" +
            "   #{createTime},\n" +
            "   #{warehouseId},\n" +
            "   #{reviewTime},\n" +
            "   #{cancelReason},\n" +
            "   #{disagreeReason},\n" +
            "   #{reviewPersonId}\n" +
            " ) ")
    void insertRLSEntity(ReportLossStatementEntity rLSEntity);

    @Insert(" INSERT INTO product_info (\n" +
            "   uuid,\n" +
            "   report_loss_statement_id,\n" +
            "   sku_id,\n" +
            "   fn_sku,\n" +
            "   reported_loss_amount\n" +
            ")\n" +
            "VALUES\n" +
            " (\n" +
            "   #{uuid},\n" +
            "   #{reportLossStatementId},\n" +
            "   #{skuId},\n" +
            "   #{fnSku},\n" +
            "   #{reportedLossAmount}\n" +
            " ) ")
    void insertPIEntity(ProductInfoEntity pIEntity);

    @Select(" SELECT\n" +
            "  report_loss_statement_id as reportLossStatementId,\n" +
            "  label_flag               as labelFlag,\n" +
            "  report_loss_reason       as reportLossReason,\n" +
            "  annex_address            as annexAddress,\n" +
            "  status_cd                as statusCd,\n" +
            "  apply_person_id          as applyPersonId,\n" +
            "  create_time              as createTime,\n" +
            "  warehouse_id             as warehouseId,\n" +
            "  review_time              as reviewTime,\n" +
            "  cancel_reason            as cancelReason,\n" +
            "  disagree_reason          as disagreeReason,\n" +
            "  review_person_id         as reviewPersonId\n" +
            " FROM " +
            "   report_loss_statement " +
            " where report_loss_statement_id = #{reportLossStatementId}")
    Map getRLSS(String reportLossStatementId);

    @Select(" SELECT\n" +
            "  uuid                     as uuid,\n" +
            "  report_loss_statement_id as reportLossStatementId,\n" +
            "  sku_id                   as skuId,\n" +
            "  fn_sku                   as fnSku,\n" +
            "  reported_loss_amount     as reportedLossAmount \n" +
            " FROM " +
            "   product_info " +
            " where " +
            "   report_loss_statement_id = #{reportLossStatementId}")
    List<Map> getpIList(String reportLossStatementId);

    @Select(" SELECT COUNT(*)\n" +
            " FROM report_loss_statement\n" +
            " WHERE report_loss_statement_id LIKE concat('', #{reportLossStatementId}, '%') ")
    int getFuzzySize(String reportLossStatementId);

    @Update(" UPDATE\n" +
            "  report_loss_statement\n" +
            " SET\n" +
            "  status_cd        = #{statusCd},\n" +
            "  review_person_id = #{reviewPersonId},\n" +
            "  review_time      = now(),\n" +
            "  disagree_reason  = #{disagreeReason}\n" +
            " WHERE\n" +
            "  report_loss_statement_id = #{reportLossStatementId}\n" +
            "  AND (status_cd != 2 or  status_cd != 4 )")
    void review(ReportLossStatementEntity rLSEntity);

    @Update(" UPDATE\n" +
            "  report_loss_statement\n" +
            " SET\n" +
            "  status_cd        = #{statusCd},\n" +
            "  review_person_id = #{reviewPersonId},\n" +
            "  review_time      = now(),\n" +
            "  cancel_reason  = #{cancelReason}\n" +
            " WHERE\n" +
            "  report_loss_statement_id = #{reportLossStatementId}\n" +
            "  AND status_cd = 1 ")
    void cancel(ReportLossStatementEntity rLSEntity);

    @Select(" <script>" +
            " SELECT\n" +
            "  a.report_loss_statement_id AS reportLossStatementId,\n" +
            "  a.label_flag               AS reportLossReason,\n" +
            "  a.report_loss_reason       AS reportLossReason,\n" +
            "  a.annex_address            AS annexAddress,\n" +
            "  a.status_cd                AS statusCd,\n" +
            "  a.apply_person_id          AS applyPersonId,\n" +
            "  a.create_time              AS createTime,\n" +
            "  a.warehouse_id             AS warehouseId,\n" +
            "  a.review_time              AS reviewTime,\n" +
            "  a.cancel_reason            AS cancelReason,\n" +
            "  a.disagree_reason          AS disagreeReason,\n" +
            "  a.review_person_id         AS reviewPersonId\n" +
            " FROM report_loss_statement a\n" +
            "    where \n" +
            "      1 = 1" +
            "      <if test = 'createTimeStart!=null'>" +
            "           AND date(a.create_time) &gt;= #{createTimeStart} \n" +
            "      </if>" +
            "      <if test = 'createTimeEnd!=null'>" +
            "       AND date(a.create_time) &lt;= #{createTimeEnd} \n" +
            "      </if>" +
            "      <if test = 'warehouseId!=null'>" +
            "           AND a.warehouse_id = #{warehouseId}\n" +
            "      </if>" +
            "      <if test = 'statusCd!=null'>" +
            "           AND a.status_cd = #{statusCd}\n" +
            "      </if>" +
            "      <if test = 'idPara!=null'>" +
            "           and report_loss_statement_id in ${idPara} \n" +
            "      </if>" +
            "      order by a.create_time desc " +
            " </script>")
    List<Map> fuzzy(Map map);

    @Select(" <script>" +
            " SELECT\n" +
            "  uuid                     AS uuid,\n" +
            "  report_loss_statement_id AS reportLossStatementId,\n" +
            "  sku_id                   AS skuId,\n" +
            "  fn_sku                   AS fnSku,\n" +
            "  reported_loss_amount     AS reportedLossAmount\n" +
            " FROM\n" +
            "  product_info\n" +
            " WHERE\n" +
            "  1 = 1\n" +
            "  <if test = 'keyWords!=null'>" +
            "   AND report_loss_statement_id LIKE concat('%', #{keyWords}, '%')\n" +
            "   OR  sku_id                   LIKE concat('%', #{keyWords}, '%')\n" +
            "   OR  fn_sku                   LIKE concat('%', #{keyWords}, '%')\n" +
            "   OR  sku_id                   IN ${keyWordsSkuPara} " +
            "  </if>" +
            " </script>"
    )
    List<Map> fuzzyProduct(Map map);

    @Select(" SELECT\n" +
            "  status_cd   AS statusCd,\n" +
            "  status_desc AS statusDesc\n" +
            " FROM report_loss_statement_status_attr ")
    List<ReportLossStatementStatusAttrEntity> getStatusAttr();

    @Delete(" delete from report_loss_statement where report_loss_statement_id = #{reportLossStatementId}  ")
    void deleteRLSE(String reportLossStatementId);

    @Delete(" delete from product_info where report_loss_statement_id = #{reportLossStatementId}  ")
    void deletePIE(String reportLossStatementId);

    @Select("select warehouse_id,warehouse_name,type_cd,status_cd,create_user,create_time,country_cd, province_name,city_name,zip_code, details_addr, sub_warehouse_support, remark, warehouse_contact, warehouse_phone, modify_user, modify_time from warehouse_basis_info where warehouse_id = #{warehouseId}")
    WarehouseManageInfoEntity findWarehouseById(@Param("warehouseId") String warehouseId);

    @Select(" select count(*) from report_loss_statement where status_cd = #{statusCd} ")
    int getSizeByStatusCd(int statusCd);
}
