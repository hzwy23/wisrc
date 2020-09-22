package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.dao.sql.LabelSql;
import com.wisrc.shipment.webapp.vo.ChangeLabelDetailVo;
import com.wisrc.shipment.webapp.vo.ChangeLableViewVo;
import com.wisrc.shipment.webapp.vo.ChangeLableVo;
import com.wisrc.shipment.webapp.vo.LabelDetailDealVo;
import com.wisrc.shipment.webapp.entity.ChangeLableDetail;
import com.wisrc.shipment.webapp.entity.ChangeLableEnity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LabelDao {
    @Insert("insert into shipment_change_label_basis_info (change_label_id, source_id, wareHouse_id, remark, status_cd, cancel_reason, create_time, modify_time, create_user, modify_user, fnsku, sub_warehouse_id) " +
            "values (#{changeLabelId}, #{sourceId}, #{warehouseId}, #{remark}, #{statusCd}, #{cancelReason}, #{createTime}, #{modifyTime}, #{createUser}, #{modifyUser}, #{fnsku}, #{subWarehouseId})")
    void insert(ChangeLableEnity changeLableEnity);

    @Insert("insert into change_label_detail (uuid, change_quantity, fnsku, sub_warehouse_id, operation_status_cd, modify_time, modify_user, change_label_id) values (#{uuid}, #{changeQuantity}, #{fnsku}, #{subWarehouseId}, #{operationStatusCd}, #{modifyTime}, #{modifyUser}, #{changeLabelId})")
    void addLabelDatail(ChangeLableDetail changeLableDetail);

    @Select("select change_label_id, source_id, sub_warehouse_id, warehouse_id, remark, status_cd, cancel_reason, create_time, create_user, fnsku ,fnsku_code_id from shipment_change_label_basis_info where change_label_id=#{changeLabelId}")
    ChangeLableVo getLabelById(String changeLabelId);

    @Select("select uuid, change_quantity,sub_warehouse_id , fnsku, warehouse_position, operation_status_cd, cancel_reason, modify_time, modify_user, change_label_id, changed_quantity from change_label_detail where change_label_id=#{changeLabelId}")
    List<ChangeLabelDetailVo> getLabelDetail(String changeLabelId);

    @UpdateProvider(type = LabelSql.class, method = "update")
    void update(ChangeLableEnity changeLableEnity);

    @Delete("delete from change_label_detail where change_label_id=#{changeLabelId}")
    void deleteDetailByLabelId(String changeLabelId);

    @SelectProvider(type = LabelSql.class, method = "findByCond")
    List<ChangeLableViewVo> findByCond(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("wareHouseId") String wareHouseId, @Param("statusCd") int statusCd, @Param("keyword") String keyword);

    @Update("update change_label_detail set operation_status_cd=#{operationStatusCd},changed_quantity=#{changedQuantity},complete_time=#{completeTime},cancel_reason=#{cancelReason},modify_user=#{modifyUser},modify_time=#{completeTime} where uuid=#{uuid}")
    void updateDetail(LabelDetailDealVo labelDetailDealVo);

    @Update("update shipment_change_label_basis_info set status_cd=3, cancel_reason=#{reason}, modify_user=#{userId} where change_label_id=#{changeLabelId}")
    void cancelChangeLabel(@Param("changeLabelId") String changeLabelId, @Param("reason") String reason, @Param("userId") String userId);

    @Update("update change_label_detail set operation_status_cd=#{operationStatusCd} where change_label_id=#{changeLabelId}")
    void cancelChangeLabelDetail(@Param("operationStatusCd") int operationStatusCd, @Param("changeLabelId") String changeLabelId);

    @Update("update shipment_change_label_basis_info set status_cd=#{statusCd} where change_label_id=#{changeLabelId}")
    void changeStatus(@Param("changeLabelId") String changeLabelId, @Param("statusCd") int statusCd);

    /**
     * 更新fnsku条码文件id
     *
     * @param changeLabelId
     * @param fnskuCodeId
     */
    @Update("update shipment_change_label_basis_info set fnsku_code_id=#{fnskuCodeId} where change_label_id=#{changeLabelId}")
    void changeFnskuCodeFileId(@Param("changeLabelId") String changeLabelId, @Param("fnskuCodeId") String fnskuCodeId);

    @Select("select fnsku, sub_warehouse_id, change_quantity from change_label_detail where uuid=#{uuid}")
    ChangeLabelDetailVo getById(String uuid);
}
