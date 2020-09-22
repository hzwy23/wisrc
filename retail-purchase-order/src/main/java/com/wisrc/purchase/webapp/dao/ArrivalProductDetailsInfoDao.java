package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.InspectionProductDetailsInfoSQL;
import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.query.ArrivalPageQuery;
import com.wisrc.purchase.webapp.query.InspectionPageQuery;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface ArrivalProductDetailsInfoDao {
    int using = 0;

    @Insert("INSERT INTO arrival_product_details_info(arrival_product_id, arrival_id, sku_id, warehouse_owe_num, delivery_quantity, delivery_spare_quantity, freight, carton_num, mantissa_num, " +
            "receipt_quantity, receipt_spare_quantity, delete_status, status_cd) VALUES(#{arrivalProductId}, #{arrivalId}, #{skuId}, #{warehouseOweNum}, #{deliveryQuantity}, " +
            "#{deliverySpareQuantity}, #{freight}, #{cartonNum}, #{mantissaNum}, #{receiptQuantity}, #{receiptSpareQuantity}, #{deleteStatus}, #{statusCd})")
    void saveInspectionProductDetailsInfo(ArrivalProductDetailsInfoEntity arrivalProductDetailsInfo);

    @Update("UPDATE arrival_product_details_info SET sku_id = #{skuId}, delivery_quantity = #{deliveryQuantity}, delivery_spare_quantity = #{deliverySpareQuantity}, freight = #{freight}, " +
            "carton_num = #{cartonNum}, mantissa_num = #{mantissaNum}, receipt_quantity = #{receiptQuantity}, receipt_spare_quantity = #{receiptSpareQuantity}  WHERE arrival_product_id = #{arrivalProductId}")
    void editInspectionProductDetailsInfo(ArrivalProductDetailsInfoEntity arrivalProductDetailsInfo);

    @Update("UPDATE arrival_product_details_info SET status_cd = #{statusCd}, status_modify_time = #{statusModifyTime} WHERE arrival_product_id = #{arrivalProductId}")
    void editInspectionProductStatus(@Param("statusCd") Integer statusCd, @Param("arrivalProductId") String arrivalProductId, @Param("statusModifyTime") Date statusModifyTime);

    @SelectProvider(type = InspectionProductDetailsInfoSQL.class, method = "deleteBatch")
    void deleteInspectionBasisInfoBatch(@Param("arrivalProductIds") List arrivalProductIds);

    @SelectProvider(type = InspectionProductDetailsInfoSQL.class, method = "deleteInspectionBasisInfo")
    void deleteInspectionBasisInfo(@Param("arrivalProductIds") List arrivalProductIds) throws Exception;

    @Select("SELECT inspection_quantity, status_cd FROM arrival_product_details_info WHERE arrival_product_id = #{arrivalProductId} AND delete_status = " + using)
    ArrivalProductDetailsInfoEntity getInspection(@Param("arrivalProductId") String arrivalProductId);

    @Select("SELECT arrival_product_id, inspection_quantity, status_cd FROM arrival_product_details_info WHERE arrival_id = #{arrivalId} AND delete_status = " + using)
    List<ArrivalProductDetailsInfoEntity> getProductEditDetails(@Param("arrivalId") String arrivalId);

    @SelectProvider(type = InspectionProductDetailsInfoSQL.class, method = "getInspectionPage")
    List<InspectionPageEntity> getInspectionPage(InspectionPageQuery inspectionPageQuery) throws Exception;

    @Select("SELECT arrival_product_id, arrival_id, sku_id, warehouse_owe_num, delivery_quantity, delivery_spare_quantity, freight, carton_num, mantissa_num, " +
            "receipt_quantity, receipt_spare_quantity, status_cd FROM arrival_product_details_info " +
            " WHERE arrival_id = #{arrivalId} AND delete_status = " + using)
    List<GetInspectionProduct> getInspectionProductByInspectionId(String arrivalId) throws Exception;

    @SelectProvider(type = InspectionProductDetailsInfoSQL.class, method = "getInspectionIdByProductId")
    List<ArrivalProductDetailsInfoEntity> getInspectionIdByProductIds(@Param("arrivalProductIds") List arrivalProductIds) throws Exception;

    @SelectProvider(type = InspectionProductDetailsInfoSQL.class, method = "getInspectionPageInId")
    List<InspectionPageEntity> getInspectionPageInId(@Param("arrivalProductIds") List arrivalProductIds) throws Exception;

    @SelectProvider(type = InspectionProductDetailsInfoSQL.class, method = "getArrivalProduct")
    List<InspectionPageEntity> getArrivalProduct(InspectionPageQuery inspectionPageQuery) throws Exception;

    @Update("UPDATE arrival_product_details_info SET inspection_quantity = #{inspectionQuantity}, qualified_qualified = #{qualifiedQualified}, unqualified_quantity = #{unqualifiedQuantity} " +
            "WHERE arrival_product_id = #{arrivalProductId} ")
    void editArrivalProduct(ArrivalProductDetailsInfoEntity arrivalProductDetailsInfo);


    @Select("SELECT delivery_quantity\n" +
            "  FROM arrival_product_details_info\n" +
            "WHERE sku_id = #{skuId} AND  arrival_id = #{arrivalId} ")
    int getDeliveryQuantity(@Param("arrivalId") String arrivalId,
                            @Param("skuId") String skuId);

    @SelectProvider(type = InspectionProductDetailsInfoSQL.class, method = "getArrivalPage")
    List<ArrivalPageEntity> getArrivalPage(ArrivalPageQuery inspectionPageQuery) throws Exception;

    @Select("SELECT arrival_product_id, arrival_id, sku_id, warehouse_owe_num, delivery_quantity, delivery_spare_quantity, freight, carton_num, mantissa_num, " +
            "receipt_quantity, receipt_spare_quantity, status_desc FROM arrival_product_details_info AS apdi LEFT JOIN arrival_basic_status_attr AS absa ON absa.status_cd = apdi.status_cd " +
            " WHERE arrival_id = #{arrivalId} AND delete_status = " + using)
    List<GetArrivalProduct> getArrivalProductByInspectionId(String arrivalId) throws Exception;

    @SelectProvider(type = InspectionProductDetailsInfoSQL.class, method = "getArrivalPageInId")
    List<ArrivalPageEntity> getArrivalPageInId(@Param("arrivalId") List arrivalId) throws Exception;

    @SelectProvider(type = InspectionProductDetailsInfoSQL.class, method = "getProductByArrival")
    List<String> getProductByArrival(@Param("arrivalIds") List arrivalIds);
}
