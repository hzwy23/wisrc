package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.InspectionBasisInfoSql;
import com.wisrc.purchase.webapp.entity.ArrivalBasisInfoEntity;
import com.wisrc.purchase.webapp.entity.GetInspectionEntity;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ArrivalBasisInfoDao {
    int using = 0;

    @Insert("INSERT INTO arrival_basis_info(arrival_id, purchase_order_id, employee_id, apply_date, supplier_id, haulage_days, plate_number, freight, freight_apportion_cd, estimate_arrival_date, " +
            "logistics_id, remark, create_time, create_user, modify_time, modify_user, delete_status, status_modify_time,random_value,arrival_warehouse_id, pack_warehouse_id ) VALUES(#{arrivalId}, #{purchaseOrderId}, #{employeeId}, #{applyDate}, #{supplierId}, " +
            "#{haulageDays}, #{plateNumber}, #{freight}, #{freightApportionCd}, #{estimateArrivalDate}, #{logisticsId}, #{remark}, #{createTime}, #{createUser}, #{modifyTime}, #{modifyUser}, #{deleteStatus}, #{createTime},#{arrivalId}, #{arrivalWarehouseId}, #{packWarehouseId})")
    void saveInspectionBasisInfo(ArrivalBasisInfoEntity arrivalBasisInfoEntity);

    @Update("UPDATE arrival_basis_info SET employee_id = #{employeeId}, supplier_id = #{supplierId}, haulage_days = #{haulageDays}, " +
            "apply_date = #{applyDate}, estimate_arrival_date = #{estimateArrivalDate}, freight = #{freight}, " +
            "freight_apportion_cd = #{freightApportionCd}, remark = #{remark}, modify_user = #{modifyUser}, modify_time = #{modifyTime}, arrival_warehouse_id = #{arrivalWarehouseId}, pack_warehouse_id = #{packWarehouseId} WHERE arrival_id = #{arrivalId}")
    void editInspectionBasisInfo(ArrivalBasisInfoEntity arrivalBasisInfoEntity);

    @Select("SELECT arrival_id FROM arrival_basis_info WHERE random_value = #{randomValue}")
    String getInspectionIdByOrder(@Param("randomValue") String randomValue);

    @Select("SELECT arrival_id, purchase_order_id, employee_id, apply_date, supplier_id, haulage_days, plate_number, abi.freight, fata.freight_apportion_cd, estimate_arrival_date, logistics_id, remark, freight_apportion_desc,delete_status, status_cd, status_modify_time, arrival_warehouse_id, pack_warehouse_id " +
            " FROM arrival_basis_info AS abi LEFT JOIN arrival_freight_amrt_attr AS fata ON fata.freight_apportion_cd = abi.freight_apportion_cd WHERE arrival_id = #{arrivalId} AND delete_status = " + using)
    GetInspectionEntity getInspectionById(@Param("arrivalId") String arrivalId) throws Exception;

    @SelectProvider(type = InspectionBasisInfoSql.class, method = "deleteInspectionByIds")
    void deleteInspectionByIds(@Param("arrivalIds") List arrivalIds) throws Exception;

    @SelectProvider(type = InspectionBasisInfoSql.class, method = "getInspectionSelector")
    List<String> getInspectionSelector(@Param("purchaseOrderId") String purchaseOrderId) throws Exception;

    @Update("update arrival_product_details_info set status_cd = #{statusCd}, status_modify_time = #{modifyTime} where arrival_id = #{arrivalId} AND sku_id = #{skuId}")
    void changeStatus(@Param("arrivalId") String arrivalId, @Param("skuId") String skuId, @Param("statusCd") int statusCd, @Param("modifyTime") Timestamp modifyTime);

    @Update("update arrival_product_details_info set status_cd = #{statusCd}, status_modify_time = #{modifyTime} where arrival_product_id = #{arrivalProductId}")
    void changeProductStatus(@Param("arrivalProductId") String arrivalProductId, @Param("statusCd") int statusCd, @Param("modifyTime") Timestamp modifyTime);


}
