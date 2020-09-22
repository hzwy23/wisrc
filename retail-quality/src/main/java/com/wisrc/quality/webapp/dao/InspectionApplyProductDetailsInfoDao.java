package com.wisrc.quality.webapp.dao;

import com.wisrc.quality.webapp.entity.InspectionApplyProductDetailsInfoEntity;
import com.wisrc.quality.webapp.entity.InspectionApplyProductDetailsStatusAttrEntity;
import com.wisrc.quality.webapp.vo.inspectionApply.InspectionProdEditVO;
import com.wisrc.quality.webapp.dao.sql.InspectionApplyProductDetailsInfoSQL;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface InspectionApplyProductDetailsInfoDao {

    @Insert("INSERT INTO inspection_apply_details_info (inspection_product_id,inspection_id,sku_id,apply_inspection_quantity,status_cd) " +
            "VALUES (#{inspectionProductId},#{inspectionId},#{skuId},#{applyInspectionQuantity},#{statusCd})")
    void saveProductInfo(InspectionApplyProductDetailsInfoEntity productInfo);

    @Update("UPDATE inspection_apply_details_info SET status_cd=#{statusCd} where inspection_product_id=#{inspectionProductId}")
    void updateProductInfo(InspectionApplyProductDetailsInfoEntity productInfo);

    @Delete("DELETE FROM inspection_apply_details_info where inspection_id=#{inspectionId}")
    void deleteProductInfo(String inspectionId);

    @Select("select inspection_product_id,inspection_id,sku_id,apply_inspection_quantity,status_cd,inspection_quantity,qualified_quantity,unqualified_quantity from " +
            "inspection_apply_details_info where inspection_product_id=#{inspectionProductId}")
    InspectionApplyProductDetailsInfoEntity findById(String inspectionProductId);

    @Select("select inspection_product_id,inspection_id,sku_id,apply_inspection_quantity,status_cd,inspection_quantity,qualified_quantity,unqualified_quantity from " +
            "inspection_apply_details_info where inspection_id=#{inspectionId}")
    /*@Select("select inspection_cd, inspection_date, purchase_order_id, inspection_method_cd, inspection_type_cd, " +
            "test_times, inspection_level_cd, sampling_plan_cd, apply_inspection_quantity, actual_inspection_quantity, " +
            "change_reason_cd, sample_quantity, conclusion_cd, final_determine_cd, qualified_quantity, unqualified_quantity, " +
            "annex_address, quality_consumption, source_type_cd, source_document_cd, inspection_status_cd as status_cd, remark, create_user, " +
            "create_time, delete_status, sku_id, annex_name, carton_length, carton_width, carton_height, cross_weight from inspection_product_info where source_document_cd=#{inspectionId}")*/
    List<InspectionApplyProductDetailsInfoEntity> findByInspectionId(String inspectionId);

    @SelectProvider(type = InspectionApplyProductDetailsInfoSQL.class, method = "findByInspectionIds")
    List<InspectionApplyProductDetailsInfoEntity> findByInspectionIds(@Param("inspectionIds") String[] inspectionIds);

    @Select("SELECT status_desc from inspection_apply_details_status_attr where status_cd=#{statusCd}")
    String getStatusNameByCd(int statusCd);

    @Select("SELECT status_cd, status_desc from inspection_apply_details_status_attr")
    List<InspectionApplyProductDetailsStatusAttrEntity> findAllProdStatus();

    @Update("UPDATE inspection_apply_details_info SET status_cd=#{statusCd} where inspection_id=#{inspectionId}")
    void updateStatus(@Param("inspectionId") String inspectionId, @Param("statusCd") int statusCd);

    @Update("UPDATE inspection_apply_details_info SET apply_inspection_quantity = #{applyInspectionQuantity} WHERE inspection_product_id = #{inspectionProductId}")
    void editApplyInspection(InspectionProdEditVO prodeditVO);
}
