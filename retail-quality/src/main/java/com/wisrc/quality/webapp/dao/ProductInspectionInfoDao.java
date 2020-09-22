package com.wisrc.quality.webapp.dao;

import com.wisrc.quality.webapp.entity.InspectionItemCheckDetailsEntity;
import com.wisrc.quality.webapp.entity.InspectionItemsInfoEntity;
import com.wisrc.quality.webapp.entity.ProductInspectionInfoEntity;
import com.wisrc.quality.webapp.vo.productInspectionInfo.get.GetProductInspectionInfoVO;
import com.wisrc.quality.webapp.dao.sql.ProductInspectionInfoSql;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProductInspectionInfoDao {
    @Insert(" INSERT INTO inspection_product_info\n" +
            "(\n" +
            "   arrival_product_id,\n" +
            "   carton_length,\n" +
            "   carton_width,\n" +
            "   carton_height,\n" +
            "   cross_weight,\n" +
            "   annex_name,\n" +
            "   sku_id,\n" +
            "   inspection_cd,\n" +
            "   inspection_date,\n" +
            "   purchase_order_id,\n" +
            "   inspection_method_cd,\n" +
            "   inspection_type_cd,\n" +
            "   test_times,\n" +
            "   inspection_level_cd,\n" +
            "   sampling_plan_cd,\n" +
            "   apply_inspection_quantity,\n" +
            "   actual_inspection_quantity,\n" +
            "   change_reason_cd,\n" +
            "   sample_quantity,\n" +
            "   conclusion_cd,\n" +
            "   final_determine_cd,\n" +
            "   qualified_quantity,\n" +
            "   unqualified_quantity,\n" +
            "   annex_address,\n" +
            "   quality_consumption,\n" +
            "   source_type_cd,\n" +
            "   source_document_cd,\n" +
            "   inspection_status_cd,\n" +
            "   remark,\n" +
            "   create_user,\n" +
            "   create_time,\n" +
            "   update_time,\n" +
            "   delete_status\n" +
            ") VALUES (\n" +
            "   #{arrivalProductId},\n" +
            "   #{cartonLength},\n" +
            "   #{cartonWidth},\n" +
            "   #{cartonHeight},\n" +
            "   #{crossWeight},\n" +
            "   #{annexName},\n" +
            "   #{skuId},\n" +
            "   #{inspectionCd},\n" +
            "   #{inspectionDate},\n" +
            "   #{purchaseOrderId},\n" +
            "   #{inspectionMethodCd},\n" +
            "   #{inspectionTypeCd},\n" +
            "   #{testTimes},\n" +
            "   #{inspectionLevelCd},\n" +
            "   #{samplingPlanCd},\n" +
            "   #{applyInspectionQuantity},\n" +
            "   #{actualInspectionQuantity},\n" +
            "   #{changeReasonCd},\n" +
            "   #{sampleQuantity},\n" +
            "   #{conclusionCd},\n" +
            "   #{finalDetermineCd},\n" +
            "   #{qualifiedQuantity},\n" +
            "   #{unqualifiedQuantity},\n" +
            "   #{annexAddress},\n" +
            "   #{qualityConsumption},\n" +
            "   #{sourceTypeCd},\n" +
            "   #{sourceDocumentCd},\n" +
            "   #{inspectionStatusCd},\n" +
            "   #{remark},\n" +
            "   #{createUser},\n" +
            "   #{createTime},\n" +
            "   #{updateTime},\n" +
            "   0 \n" +
            ") ")
    void insertPIIE(ProductInspectionInfoEntity productInspectionInfoEntity);

    @Select(" SELECT COUNT(*)\n" +
            " FROM inspection_product_info\n" +
            " WHERE inspection_cd LIKE concat('', #{inspectionCd}, '%') ")
    int getFuzzySize(String inspectionCd);

    @Insert(" INSERT INTO inspection_items_info\n" +
            "(\n" +
            "   inspection_item_id,\n" +
            "   inspection_cd,\n" +
            "   items_cd,\n" +
            "   cri_all,\n" +
            "   maj_all,\n" +
            "   min_all,\n" +
            "   cri_aql,\n" +
            "   cri_ac,\n" +
            "   cri_re,\n" +
            "   maj_aql,\n" +
            "   maj_ac,\n" +
            "   maj_re,\n" +
            "   min_aql,\n" +
            "   min_ac,\n" +
            "   min_re,\n" +
            "   item_result_cd\n" +
            ") VALUES\n" +
            "   (\n" +
            "      #{ inspectionItemId },\n" +
            "      #{ inspectionCd },\n" +
            "      #{ itemsCd },\n" +
            "      #{ criAll },\n" +
            "      #{ majAll },\n" +
            "      #{ minAll },\n" +
            "      #{ criAQL },\n" +
            "      #{ criAc },\n" +
            "      #{ criRe },\n" +
            "      #{ majAQL },\n" +
            "      #{ majAc },\n" +
            "      #{ majRe },\n" +
            "      #{ minAQL },\n" +
            "      #{ minAc },\n" +
            "      #{ minRe },\n" +
            "      #{ itemResultCd }\n" +
            "   ) ")
    void insertIIE(InspectionItemsInfoEntity inspectionRecordEntity);

    @Insert(" INSERT INTO inspection_item_check_details\n" +
            "(\n" +
            "   problem_description,\n" +
            "   inspection_results,\n" +
            "   uuid,\n" +
            "   inspection_item_id,\n" +
            "   inspection_content,\n" +
            "   inspection_quantity,\n" +
            "   cri,\n" +
            "   maj,\n" +
            "   min,\n" +
            "   judgment_cd\n" +
            ") VALUES\n" +
            "   (\n" +
            "      #{ problemDescription },\n" +
            "      #{ inspectionResults },\n" +
            "      #{ uuid },\n" +
            "      #{ inspectionItemId },\n" +
            "      #{ inspectionContent },\n" +
            "      #{ inspectionQuantity },\n" +
            "      #{ cri },\n" +
            "      #{ maj },\n" +
            "      #{ min },\n" +
            "      #{ judgmentCd }\n" +
            "   ) ")
    void insertIICDE(InspectionItemCheckDetailsEntity dataInfoEntity);

    @Update(" UPDATE inspection_product_info\n" +
            " SET\n" +
            "   update_time                = #{ updateTime } ,\n" +
            "   annex_name                 = #{ annexName } ,\n" +
            "   sku_id                     = #{ skuId } ,\n" +
            "   inspection_date            = #{ inspectionDate } ,\n" +
            "   purchase_order_id          = #{ purchaseOrderId } ,\n" +
            "   inspection_method_cd       = #{ inspectionMethodCd } ,\n" +
            "   inspection_type_cd         = #{ inspectionTypeCd } ,\n" +
            "   test_times                 = #{ testTimes } ,\n" +
            "   inspection_level_cd        = #{ inspectionLevelCd } ,\n" +
            "   sampling_plan_cd           = #{ samplingPlanCd } ,\n" +
            "   apply_inspection_quantity  = #{ applyInspectionQuantity } ,\n" +
            "   actual_inspection_quantity = #{ actualInspectionQuantity } ,\n" +
            "   change_reason_cd           = #{ changeReasonCd } ,\n" +
            "   sample_quantity            = #{ sampleQuantity } ,\n" +
            "   conclusion_cd              = #{ conclusionCd } ,\n" +
            "   final_determine_cd         = #{ finalDetermineCd } ,\n" +
            "   qualified_quantity         = #{ qualifiedQuantity } ,\n" +
            "   unqualified_quantity       = #{ unqualifiedQuantity } ,\n" +
            "   annex_address              = #{ annexAddress } ,\n" +
            "   quality_consumption        = #{ qualityConsumption } ,\n" +
            "   source_type_cd             = #{ sourceTypeCd } ,\n" +
            "   source_document_cd         = #{ sourceDocumentCd } ,\n" +
            "   inspection_status_cd       = #{ inspectionStatusCd } ,\n" +
            "   carton_length              = #{ cartonLength } ,\n" +
            "   carton_width               = #{ cartonWidth } ,\n" +
            "   carton_height              = #{ cartonHeight } ,\n" +
            "   cross_weight               = #{ crossWeight } ,\n" +
            "   remark                     = #{ remark }, \n" +
            "   arrival_product_id           = #{ arrivalProductId } \n" +
            " where inspection_cd          = #{ inspectionCd } \n" +
            "  ")
    void updatePIIE(ProductInspectionInfoEntity productInspectionInfoEntity);

    @Insert(" INSERT INTO inspection_personnel_info\n" +
            "(\n" +
            "  uuid,\n" +
            "  inspection_cd,\n" +
            "  employee_id\n" +
            "\n" +
            ") VALUES\n" +
            "  (\n" +
            "    #{uuid},\n" +
            "    #{inspectionCd},\n" +
            "    #{employeeId}\n" +
            "  ) ")
    void insertIPI(Map map);

    @Delete(" delete from inspection_personnel_info where inspection_cd = #{inspectionCd} ")
    void deleteIPIByInspectionCd(String inspectionCd);

    @Delete(" delete from inspection_items_info where inspection_cd = #{inspectionCd} ")
    void deleteItemsByInspectionCd(String inspectionCd);

    @Delete(" DELETE FROM\n" +
            "   inspection_item_check_details\n" +
            " WHERE\n" +
            "   inspection_item_id\n" +
            "   IN\n" +
            "   (\n" +
            "      SELECT " +
            "           inspection_item_id\n" +
            "      FROM " +
            "           inspection_items_info\n" +
            "      WHERE" +
            "           inspection_cd = #{inspectionCd}\n" +
            "   ) ")
    void deleteDetailsByInspectionCd(String inspectionCd);

    @Select(" SELECT\n" +
            "   arrival_product_id         AS arrivalProductId  ,\n" +
            "   carton_length              AS cartonLength  ,\n" +
            "   carton_width               AS cartonWidth  ,\n" +
            "   carton_height              AS cartonHeight  ,\n" +
            "   cross_weight               AS crossWeight  ,\n" +
            "   annex_name                 AS annexName,\n" +
            "   sku_id                     AS skuId,\n" +
            "   inspection_cd              AS inspectionCd,\n" +
            "   inspection_date            AS inspectionDate,\n" +
            "   purchase_order_id          AS purchaseOrderId,\n" +
            "   inspection_method_cd       AS inspectionMethodCd,\n" +
            "   inspection_type_cd         AS inspectionTypeCd,\n" +
            "   test_times                 AS testTimes,\n" +
            "   inspection_level_cd        AS inspectionLevelCd,\n" +
            "   sampling_plan_cd           AS samplingPlanCd,\n" +
            "   apply_inspection_quantity  AS applyInspectionQuantity,\n" +
            "   actual_inspection_quantity AS actualInspectionQuantity,\n" +
            "   change_reason_cd           AS changeReasonCd,\n" +
            "   sample_quantity            AS sampleQuantity,\n" +
            "   conclusion_cd              AS conclusionCd,\n" +
            "   final_determine_cd         AS finalDetermineCd,\n" +
            "   qualified_quantity         AS qualifiedQuantity,\n" +
            "   unqualified_quantity       AS unqualifiedQuantity,\n" +
            "   annex_address              AS annexAddress,\n" +
            "   quality_consumption        AS qualityConsumption,\n" +
            "   source_type_cd             AS sourceTypeCd,\n" +
            "   source_document_cd         AS sourceDocumentCd,\n" +
            "   inspection_status_cd       AS inspectionStatusCd,\n" +
            "   remark                     AS remark,\n" +
            "   create_user                AS createUser,\n" +
            "   create_time                AS createTime\n" +
            " FROM inspection_product_info" +
            " where " +
            "   delete_status = 0 " +
            " and " +
            "   inspection_cd              = #{inspectionCd}\n" +
            "  ")
    LinkedHashMap getIIByInspectionCd(String inspectionCd);

    @Select(" SELECT\n" +
            "  uuid          AS uuid,\n" +
            "  inspection_cd AS inspectionCd,\n" +
            "  employee_id     AS employeeId\n" +
            " FROM inspection_personnel_info\n" +
            " WHERE inspection_cd = #{inspectionCd} ")
    List<LinkedHashMap> getPersonListByInspectionCd(String inspectionCd);


    @Select(" SELECT\n" +
            "   inspection_item_id AS inspectionItemId,\n" +
            "   inspection_cd      AS inspectionCd,\n" +
            "   items_cd           AS itemsCd,\n" +
            "   cri_all            AS criAll,\n" +
            "   maj_all            AS majAll,\n" +
            "   min_all            AS minAll,\n" +
            "   cri_aql            as criAQL,\n" +
            "   cri_ac             as criAc,\n" +
            "   cri_re             as criRe,\n" +
            "   maj_aql            as majAQL,\n" +
            "   maj_ac             as majAc,\n" +
            "   maj_re             as majRe,\n" +
            "   min_aql            as minAQL,\n" +
            "   min_ac             as minAc,\n" +
            "   min_re             as minRe,\n" +
            "   item_result_cd     AS inspectionResultCd\n" +
            " FROM inspection_items_info\n" +
            " WHERE\n" +
            "   inspection_cd = #{inspectionCd} " +
            " order by items_cd asc ")
    List<Map> getItemsListByInspectionCd(String inspectionCd);

    @Select(" SELECT\n" +
            "   uuid                AS uuid,\n" +
            "   problem_description  AS problemDescription,\n" +
            "   inspection_results  AS inspectionResults,\n" +
            "   inspection_item_id  AS inspectionItemId,\n" +
            "   inspection_content  AS inspectionContent,\n" +
            "   inspection_quantity AS inspectionQuantity,\n" +
            "   cri                 AS cri,\n" +
            "   maj                 AS maj,\n" +
            "   min                 AS min,\n" +
            "   judgment_cd         AS judgmentCd\n" +
            " FROM " +
            "   inspection_item_check_details\n" +
            " WHERE\n" +
            "   inspection_item_id IN ${inspectionItemId}\n" +
            " ")
    List<LinkedHashMap> getDataList(Map<String, String> paraMap);

    @Update(" update inspection_product_info set delete_status = 1  where inspection_cd = #{inspectionCd} and inspection_status_cd != 3 ")
    void deletePIIByInspectionCd(String inspectionCd);

    //    @Select("  <script> " +
//            "SELECT\n" +
//            "   sku_id                     AS skuId,\n" +
//            "   inspection_cd              AS inspectionCd,\n" +
//            "   inspection_date            AS inspectionDate,\n" +
//            "   purchase_order_id          AS purchaseOrderId,\n" +
//            "   inspection_method_cd       AS inspectionMethodCd,\n" +
//            "   inspection_type_cd         AS inspectionTypeCd,\n" +
//            "   test_times                 AS testTimes,\n" +
//            "   inspection_level_cd        AS inspectionLevelCd,\n" +
//            "   sampling_plan_cd           AS samplingPlanCd,\n" +
//            "   apply_inspection_quantity  AS applyInspectionQuantity,\n" +
//            "   actual_inspection_quantity AS actualInspectionQuantity,\n" +
//            "   change_reason_cd           AS changeReasonCd,\n" +
//            "   sample_quantity            AS sampleQuantity,\n" +
//            "   conclusion_cd              AS conclusionCd,\n" +
//            "   final_determine_cd         AS finalDetermineCd,\n" +
//            "   qualified_quantity         AS qualifiedQuantity,\n" +
//            "   unqualified_quantity       AS unqualifiedQuantity,\n" +
//            "   annex_address              AS annexAddress,\n" +
//            "   quality_consumption        AS qualityConsumption,\n" +
//            "   source_type_cd             AS sourceTypeCd,\n" +
//            "   source_document_cd         AS sourceDocumentCd,\n" +
//            "   inspection_status_cd       AS inspectionStatusCd,\n" +
//            "   remark                     AS remark,\n" +
//            "   create_user                AS createUser,\n" +
//            "   create_time                AS createTime\n" +
//            "FROM inspection_product_info\n" +
//            "WHERE\n" +
//            "   delete_status = 0\n" +
//            "           <if test = 'purchaseOrderId!=null'>" +
//            "           AND purchase_order_id LIKE concat('%', #{purchaseOrderId}, '%')\n" +
//            "           </if>" +
//            "           <if test = 'inspectionDateStart!=null'>" +
//            "           AND inspection_date   &gt;= #{inspectionDateStart} \n" +
//            "           </if>" +
//            "           <if test = 'inspectionDateEnd!=null'>" +
//            "           AND inspection_date  &lt;= #{inspectionDateEnd}\n" +
//            "           </if>" +
//            "           <if test = 'inspectionStatusCd!=null'>" +
//            "           AND inspection_status_cd LIKE concat('%', #{inspectionStatusCd}, '%')\n" +
//            "           </if>" +
//            "           <if test = 'finalDetermineCd!=null'>" +
//            "           AND final_determine_cd LIKE concat('%', #{finalDetermineCd}, '%')\n" +
//            "           </if>" +
//            "           <if test = 'sourceTypeCd!=null'>" +
//            "           AND source_type_cd LIKE concat('%', #{sourceTypeCd}, '%')\n" +
//            "           </if>" +
//            "           <if test = 'skuId!=null'>" +
//            "           AND skuId LIKE concat('%', #{skuId}, '%')\n" +
//            "           </if>" +
//            "           <if test = 'employeeId!=null'>" +
//            "           inspection_cd in " +
//            "               (" +
//            "                  SELECT inspection_cd\n" +
//            "                  FROM inspection_personnel_info\n" +
//            "                  WHERE employee_id LIKE concat('%', #{employeeId}, '%') " +
//            "               )  \n" +
//            "           </if>" +
//            "  </script> "
//    )
    @SelectProvider(type = ProductInspectionInfoSql.class, method = "findALl")
    List<ProductInspectionInfoEntity> fuzzyFind(GetProductInspectionInfoVO vo);

    @Select(" SELECT\n" +
            "  uuid          AS uuid,\n" +
            "  inspection_cd AS inspectionCd,\n" +
            "  employee_id   AS employeeId\n" +
            " FROM inspection_personnel_info\n" +
            " WHERE inspection_cd in ${inspectionCd} ")
    List<LinkedHashMap> getPersonList(Map<String, String> paraMap);

    @Select(" SELECT\n" +
            "   a.sku_id                     AS skuId,\n" +
            "   a.actual_inspection_quantity AS inspectionQuantity,\n" +
            "   a.qualified_quantity         AS qualifiedQualified,\n" +
            "   a.unqualified_quantity       AS unqualifiedQuantity\n" +
            " FROM inspection_product_info a\n" +
            " WHERE " +
            "      delete_status = 0 " +
            "      and a.source_document_cd = #{sourceDocumentCd} \n" +
            "      AND a.sku_id in ${skuId}")
    List<LinkedHashMap> fuzzyFindTwo(Map map);

    @Select("SELECT count(purchase_order_id) FROM inspection_product_info where purchase_order_id = #{orderId}")
    int getInspectionProductInfo(@Param("orderId") String orderId);

    @Select(" <script>" +
            " SELECT\n" +
            "  COALESCE(SUM(a.actual_inspection_quantity), 0) AS actualInspectionQuantity,\n" +
            "  COALESCE(SUM(a.qualified_quantity), 0)         AS qualifiedQuantity,\n" +
            "  COALESCE(SUM(a.unqualified_quantity), 0)       AS unqualifiedQuantity\n" +
            " FROM\n" +
            "  inspection_product_info a\n" +
            " WHERE\n" +
            "  a.purchase_order_id = #{purchaseOrderId} \n" +
            "  AND a.source_document_cd = #{sourceDocumentCd} \n" +
            "  AND a.sku_id = #{skuId} " +
            "  <if test = 'arrivalProductId!=null'>" +
            "    AND a.arrival_product_id = #{arrivalProductId} \n" +
            "  </if>" +
            " </script>")
    ProductInspectionInfoEntity getSUMDetail(ProductInspectionInfoEntity entity);


    //去掉final_determine_cd = 2（也就是最终判断为仓库练选的质检单）的单子的合计
    @Select(" <script>" +
            " SELECT\n" +
            "  COALESCE(SUM(a.actual_inspection_quantity), 0) AS actualInspectionQuantity,\n" +
            "  COALESCE(SUM(a.qualified_quantity), 0)         AS qualifiedQuantity,\n" +
            "  COALESCE(SUM(a.unqualified_quantity), 0)       AS unqualifiedQuantity\n" +
            " FROM\n" +
            "  inspection_product_info a\n" +
            " WHERE\n" +
            "  a.purchase_order_id = #{purchaseOrderId} \n" +
            "  AND a.source_document_cd = #{sourceDocumentCd} \n" +
            "  AND a.final_determine_cd != 2" +
            "  AND a.sku_id = #{skuId} " +
            "  <if test = 'arrivalProductId!=null'>" +
            "    AND a.arrival_product_id = #{arrivalProductId} \n" +
            "  </if>" +
            " </script>")
    ProductInspectionInfoEntity getSUMDetailTakeoutfinal2(ProductInspectionInfoEntity entity);


    @Insert(" <script>" +
            " UPDATE\n" +
            "   inspection_apply_details_info\n" +
            " SET\n" +
            "   <if test = 'statusCd != null'  >" +
            "   status_cd = #{statusCd} ,\n" +
            "   </if>" +
            "   inspection_quantity  = #{inspectionQuantity} ,\n" +
            "   qualified_quantity   = #{qualifiedQuantity} ,\n" +
            "   unqualified_quantity = #{unqualifiedQuantity} \n" +
            " WHERE\n" +
            "   inspection_id = #{inspectionId} \n" +
            "   and sku_id = #{skuId}" +
            " </script>")
    void modifyIADI(Map map);

    @Select(" SELECT\n" +
            "   a.sku_id                     AS skuId,\n" +
            "   a.actual_inspection_quantity AS inspectionQuantity,\n" +
            "   a.qualified_quantity         AS qualifiedQualified,\n" +
            "   a.unqualified_quantity       AS unqualifiedQuantity\n" +
            " FROM inspection_product_info a\n" +
            " WHERE " +
            "      delete_status = 0 " +
            "      and a.source_document_cd = #{sourceDocumentCd} \n" +
            "      and a.inspection_status_cd = 3 \n" +
            "      AND a.sku_id in ${skuId}")
    List<LinkedHashMap> fuzzyFindThree(Map map);

    @Select(" SELECT\n" +
            "   a.sku_id                     AS skuId,\n" +
            "   a.final_determine_cd         AS finalDetermineCd,\n" +
            "   a.actual_inspection_quantity AS inspectionQuantity,\n" +
            "   a.qualified_quantity         AS qualifiedQualified,\n" +
            "   a.unqualified_quantity       AS unqualifiedQuantity\n" +
            " FROM inspection_product_info a\n" +
            " WHERE " +
            "      delete_status = 0 " +
            "      and a.source_document_cd = #{sourceDocumentCd} \n" +
            "      and a.inspection_status_cd = 3 \n" +
            "      AND a.sku_id in ${skuId}")
    List<LinkedHashMap> fuzzyFindFour(Map map);

    @Select("SELECT apply_inspection_quantity, actual_inspection_quantity, source_document_cd, sku_id FROM inspection_product_info WHERE source_document_cd = #{arrivalId} ORDER BY sku_id")
    List<ProductInspectionInfoEntity> getByArrivalId(@Param("arrivalId") String arrivalId);

    @Select("SELECT apply_inspection_quantity, actual_inspection_quantity, source_document_cd, sku_id, final_determine_cd FROM inspection_product_info WHERE source_document_cd = #{arrivalId} AND sku_id = #{skuId}  ORDER BY sku_id")
    List<ProductInspectionInfoEntity> getByArrivalIdandSku(@Param("arrivalId") String arrivalId,
                                                           @Param("skuId") String skuId);
}
