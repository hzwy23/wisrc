package com.wisrc.quality.webapp.dao;

import com.wisrc.quality.webapp.entity.InspectionApplyInfoEntity;
import com.wisrc.quality.webapp.entity.InspectionApplyTypeAttrEntity;
import com.wisrc.quality.webapp.vo.inspectionApply.InspectionDataVO;
import com.wisrc.quality.webapp.vo.inspectionApply.OrderIdQuerySumVO;
import com.wisrc.quality.webapp.vo.inspectionApply.OrderIdQueryVO;
import com.wisrc.quality.webapp.dao.sql.InspectionApplyInfoSQL;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
@Mapper
public interface InspectionApplyInfoDao {

    @SelectProvider(type = InspectionApplyInfoSQL.class, method = "findByCond")
    List<InspectionApplyInfoEntity> findByCond(@Param("orderId") String orderId, @Param("employeeId") String employeeId, @Param("inspectionStartTime") Date inspectionStartTime,
                                               @Param("inspectionEndTime") Date inspectionEndTime, @Param("statusCd") String statusCd, @Param("inspectionType") String inspectionType, @Param("supplierIds") String[] supplierIds);

    @Select("SELECT inspection_id,order_id,employee_id,apply_date,expect_inspection_time,inspection_type_cd,supplier_id,supplier_contact_user,supplier_phone,supplier_addr," +
            "remark,create_time,create_user,modify_user,modify_time FROM inspection_apply_info WHERE inspection_id=#{inspectionId}")
    InspectionApplyInfoEntity getApplyInfoById(@Param("inspectionId") String inspectionId);

    @Insert("INSERT INTO inspection_apply_info (inspection_id, order_id, employee_id, apply_date, supplier_id, supplier_contact_user, supplier_phone, supplier_addr, remark, expect_inspection_time, " +
            " inspection_type_cd, create_time, create_user) VALUES(#{inspectionId}, #{orderId}, #{employeeId}, #{applyDate}, #{supplierId}, " +
            "#{supplierContactUser}, #{supplierPhone}, #{supplierAddr}, #{remark}, #{expectInspectionTime}, #{inspectionTypeCd}, #{createTime}, #{createUser})")
    void saveInspectionInfo(InspectionApplyInfoEntity inspectionApplyInfoEntity);

    @Update("UPDATE inspection_apply_info SET supplier_contact_user = #{supplierContactUser}, supplier_phone = #{supplierPhone}, supplier_addr = #{supplierAddr}, remark = #{remark}, expect_inspection_time = #{expectInspectionTime}, " +
            "modify_user=#{modifyUser},modify_time=#{modifyTime}  WHERE inspection_id = #{inspectionId}")
    void editInspectionInfo(InspectionApplyInfoEntity inspectionApplyInfoEntity);

    @Update("UPDATE inspection_apply_info SET inspection_type_cd = #{inspectionTypeCd},modify_user=#{modifyUser},modify_time=#{modifyTime}  WHERE inspection_id = #{inspectionId}")
    void updateType(InspectionApplyInfoEntity inspectionApplyInfoEntity);

    //查找流水号，生成新的流水
    @Select("SELECT MAX(SUBSTRING(inspection_id,3,8)) inspectionId FROM inspection_apply_info")
    String findMaxinspectionId();

    @Delete("delete from inspection_apply_info where inspection_id=#{inspectionId}")
    void deleteById(@Param("inspectionId") String inspectionId);

    //查找验货方式
    @Select("select inspection_type_desc from inspection_apply_type_attr where inspection_type_cd=#{inspectionTypeCd}")
    String findApplyTypeDesc(int inspectionTypeCd);

    //查找所有验货方式
    @Select("select inspection_type_cd, inspection_type_desc from inspection_apply_type_attr")
    List<InspectionApplyTypeAttrEntity> findAllApplyTypeDesc();

    //检验单调用
    @SelectProvider(type = InspectionApplyInfoSQL.class, method = "findInspectionData")
    List<InspectionDataVO> findByOrderIds(@Param("orderId") String orderId, @Param("supplierIds") String[] supplierIds, @Param("skuId") String skuId,
                                          @Param("skuIds") String[] skuIds, @Param("inspectionId") String inspectionId);

    //检验单调用
    @SelectProvider(type = InspectionApplyInfoSQL.class, method = "findInspectionDataByWords")
    List<InspectionDataVO> findByWords(@Param("orderId") String orderId, @Param("supplierIds") String[] supplierIds, @Param("skuIds") String[] skuIds, @Param("skuId") String skuId);

    @Select("select inspection_cd from inspection_product_info where purchase_order_id=#{orderId} and sku_id=#{skuId} and source_document_cd=#{inspectionId} and delete_status=0")
    String findProductInspCd(@Param("orderId") String orderId, @Param("skuId") String skuId, @Param("inspectionId") String inspectionId);

    @SelectProvider(type = InspectionApplyInfoSQL.class, method = "findInspecByOrderId")
    List<OrderIdQueryVO> findInspecByOrderId(@Param("orderIds") String[] orderIds, @Param("skuIds") String[] skuIds);

    @SelectProvider(type = InspectionApplyInfoSQL.class, method = "findInspecSumByOrderId")
    List<OrderIdQuerySumVO> findInspecSumByOrderId(@Param("orderIds") String[] orderIds, @Param("skuIds") String[] skuIds);

    @Select("SELECT count(order_Id) FROM inspection_apply_info where order_id = #{orderId}")
    int getInspectionApplyInfo(@Param("orderId") String orderId);
}
