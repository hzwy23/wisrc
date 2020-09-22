package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.dao.sql.TransferSQL;
import com.wisrc.replenishment.webapp.entity.*;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


@Mapper
public interface TransferDao {
    /**
     * 插入调拨单的基本内容
     *
     * @param transferBasicInfoEntity
     */
    @Insert("INSERT INTO transfer_order_info(warehouse_start_id, warehouse_end_id,sub_warehouse_end_id,status_cd," +
            " wh_label, remark, create_user, create_time,transfer_type_cd, random_value)" +
            " VALUES (#{warehouseStartId},#{warehouseEndId},#{subWarehouseEndId},#{statusCd},#{whLabel},#{remark},#{createUser},#{createTime},#{transferTypeCd},#{randomValue})")
    void insertTransferBasic(TransferBasicInfoEntity transferBasicInfoEntity);

    /**
     * 根据随机数查找到对应的调拨单号
     *
     * @param randomValue
     * @return
     */
    @Select("SELECT transfer_order_cd FROM transfer_order_info WHERE  random_value  = #{randomValue}")
    String findTransferIdByRandomValue(@Param("randomValue") String randomValue);

    /**
     * 插入调拨单标签信息
     *
     * @param uuid
     * @param transferId
     * @param labelCd
     */
    @Insert("INSERT INTO transfer_label_rel(uuid, transfer_order_id, label_id, delete_status) VALUES(#{uuid},#{transferId},#{labelCd},0)")
    void insertTransferLabel(@Param("uuid") String uuid,
                             @Param("transferId") String transferId,
                             @Param("labelCd") Integer labelCd);

    /**
     * 插入调拨单的商品信息
     *
     * @param transferOrderDetailsEntity
     */
    @Insert("INSERT INTO transfer_order_details(commodity_info_cd, transfer_order_cd, sku_id, msku_id,fn_sku,transfer_quantity) VALUES(#{commodityInfoCd}," +
            "#{transferOrderId},#{skuId},#{mskuId},#{fnSku},#{transferQuantity})")
    void insertTransferDetails(TransferOrderDetailsEntity transferOrderDetailsEntity);

    /**
     * 插入商品的装箱信息
     *
     * @param transferOrderPackInfoEntity
     */
    @Insert("INSERT INTO transfer_order_pack_info(uuid, commodity_info_cd, outer_box_specification_len, outer_box_specification_width, outer_box_specification_height, weight, packing_quantity, number_of_boxes, delete_status,is_standard) " +
            "VALUES (#{uuid},#{commodityInfoCd},#{outerBoxSpecificationLen},#{outerBoxSpecificationWidth},#{outerBoxSpecificationHeight},#{weight},#{packingQuantity},#{numberOfBoxes},0,#{isStandard})")
    void insertTransferPacking(TransferOrderPackInfoEntity transferOrderPackInfoEntity);

    /**
     * 审核调拨单
     *
     * @param transferId
     * @param userId
     * @param currentDateTime
     */
    @Update("UPDATE transfer_order_info SET modify_time = #{currentDateTime} ,warehouse_start_id = #{warehouseStartId} ,warehouse_end_id = #{warehouseEndId},sub_warehouse_end_id = #{subWarehouseEndId}, modify_user = #{userId}, status_cd = #{status} WHERE transfer_order_cd = #{transferId}")
    void auditTransfer(@Param("transferId") String transferId,
                       @Param("warehouseStartId") String warehouseStartId,
                       @Param("warehouseEndId") String warehouseEndId,
                       @Param("subWarehouseEndId") String subWarehouseEndId,
                       @Param("userId") String userId,
                       @Param("currentDateTime") String currentDateTime,
                       @Param("status") int status);

    /**
     * 根据transferId和skuId去查询commodityId
     *
     * @param transferOrderCd
     * @param skuId
     * @return
     */
    @Select("SELECT commodity_info_cd FROM transfer_order_details WHERE transfer_order_cd = #{transferOrderCd} AND sku_id = #{skuId}")
    String findCommodityByTransferIdAndSkuId(@Param("transferOrderCd") String transferOrderCd,
                                             @Param("skuId") String skuId);

    /**
     * 逻辑删除商品对应的所有装箱规格信息
     *
     * @param commodityId
     */
    @Select("UPDATE transfer_order_pack_info SET delete_status = 1 WHERE commodity_info_cd = #{commodityId}")
    void deletePackInfoByCommodityId(@Param("commodityId") String commodityId);

    /**
     * 根据唯一标识获取调拨单基本信息
     *
     * @param transferId
     * @return
     */
    @Select("SELECT transfer_order_cd,waybill_id, warehouse_start_id, warehouse_end_id,sub_warehouse_end_id, status_cd, offer_id, shipment_id, wh_label, remark, create_user, create_time, modify_user, modify_time, cancel_reason, delivery_date," +
            " sign_in_date, transfer_type_cd, random_value, serial_number FROM transfer_order_info" +
            " WHERE transfer_order_cd =#{transferId}")
    TransferBasicInfoEntity findById(@Param("transferId") String transferId);

    /**
     * 补充完成调拨单的物流信息，并修改调拨单的状态
     *
     * @param waybillId       物流单号
     * @param transferOrderCd 调拨单号
     * @param offerId         渠道报价id
     * @param shipmentId      物流商id
     * @param userId          修改人id
     * @param currentDateTime 修改时间
     */
    @Update("UPDATE transfer_order_info SET shipment_id = #{shipmentId},offer_id = #{offerId},waybill_id = #{waybillId},status_cd = 4,modify_user = #{userId} ,modify_time = #{currentDateTime}" +
            "WHERE transfer_order_cd = #{transferOrderCd} ")
    void updateWaybillAndLogistics(@Param("transferOrderCd") String transferOrderCd,
                                   @Param("waybillId") String waybillId,
                                   @Param("offerId") String offerId,
                                   @Param("shipmentId") String shipmentId,
                                   @Param("userId") String userId,
                                   @Param("currentDateTime") String currentDateTime);

    /**
     * 根据调拨单号去查询出该调拨单下面的
     *
     * @param transferId
     * @return
     */
    @Select("SELECT commodity_info_cd, transfer_order_cd, sku_id, msku_id, fn_sku, transfer_quantity, delivery_quantity, sign_in_quantity, packing_quantity" +
            " FROM transfer_order_details WHERE transfer_order_cd = #{transferId}")
    List<TransferOrderDetailsEntity> findTransferDetailsById(@Param("transferId") String transferId);

    /**
     * 根据调拨单的明细id查询出所有的装箱信息
     *
     * @param commodityInfoCd 调拨明细id
     * @return
     */
    @Select("SELECT uuid, commodity_info_cd, outer_box_specification_len, outer_box_specification_width, outer_box_specification_height, weight, packing_quantity, number_of_boxes,is_standard , delivery_quantity,pack_quantity,sign_quantity,transfer_quantity" +
            " FROM transfer_order_pack_info WHERE commodity_info_cd = #{commodityInfoCd} AND delete_status = 0;")
    List<TransferOrderPackInfoEntity> findPackInfoByCommodityId(@Param("commodityInfoCd") String commodityInfoCd);

    /**
     * 根据调拨单号查询出所有对应的标签信息
     *
     * @param transferId
     * @return
     */
    @Select("SELECT b.label_cd AS labelCd,b.label_name AS labelName,b.label_color AS labelColor  " +
            " FROM transfer_label_rel a LEFT JOIN  replenishment_label_attr b ON a.label_id = b.label_cd WHERE a.transfer_order_id = #{transferId} AND a.delete_status = 0")
    List<TransferLabelEntity> findTransferLabelByTransferId(@Param("transferId") String transferId);

    /**
     * 通过调拨单号查询调拨单详细信息
     *
     * @param transferOrderCds
     * @return
     */
    @SelectProvider(type = TransferSQL.class, method = "getSkuInfoByCd")
    List<TransferOrderDetailsEntity> getDetailByCd(@Param("transferOrderCd") String[] transferOrderCds);

    /**
     * 通过明细同一编码查询装箱信息
     *
     * @param commodityInfoCd
     * @return
     */
    @Select("SELECT * FROM transfer_order_pack_info WHERE commodity_info_cd = #{commodityInfoCd} AND delete_status = 0")
    List<TransferOrderPackInfoEntity> getPackInfoByCd(@Param("commodityInfoCd") String commodityInfoCd);

    /**
     * 根据条件查询出所有的基本调拨单信息
     *
     * @param warehouseStartId
     * @param warehouseEndId
     * @param startDate
     * @param endDate
     * @param status
     * @return
     */
    @SelectProvider(type = TransferSQL.class, method = "findByCond")
    List<TransferBasicInfoEntity> findTransferInfoByCondition(@Param("warehouseStartId") String warehouseStartId,
                                                              @Param("warehouseEndId") String warehouseEndId,
                                                              @Param("startDate") String startDate,
                                                              @Param("endDate") String endDate,
                                                              @Param("shipmentId") String shipmentId,
                                                              @Param("status") Integer status);

    /**
     * 查询所有的状态码
     */
    @Select("SELECT status_cd,status_name FROM transfer_order_status_attr")
    List<TransferStatusEntity> findAllStatus();


    /**
     * 查询出待确认的接口
     *
     * @return
     */
    @Select("SELECT count(*) FROM transfer_order_info WHERE status_cd = 1")
    Integer findCountOfWaitConfirm();


    /**
     * 查询所有待装箱的调拨单
     *
     * @return
     */
    @Select("SELECT count(*) FROM transfer_order_info WHERE status_cd = 2")
    Integer findCountOfWaitPack();


    /**
     * 查询出所有的待选择渠道数目
     *
     * @return
     */
    @Select("SELECT count(*) FROM transfer_order_info WHERE status_cd = 3")
    Integer findCountOfWaitSelectChannel();

    /**
     * 查询出所有的待发货数量
     *
     * @return
     */
    @Select("SELECT count(*) FROM transfer_order_info WHERE status_cd = 4")
    Integer findCountOfWaitDelivery();

    /**
     * 查询出所有的代签收的调拨单数量
     *
     * @return
     */
    @Select("SELECT count(*) FROM transfer_order_info WHERE status_cd = 5")
    Integer findCountOfWaitAssign();

    /**
     * 取消调拨单
     *
     * @param transferOrderId
     * @param userId
     * @param currentDateTime
     * @param cancelReason
     */
    @Update("UPDATE transfer_order_info set cancel_reason = #{cancelReason},status_cd = 7 ,modify_user = #{userId},modify_time = #{currentDateTime} WHERE transfer_order_cd = #{transferOrderId}")
    void cancelTransfer(@Param("transferOrderId") String transferOrderId,
                        @Param("userId") String userId,
                        @Param("currentDateTime") String currentDateTime,
                        @Param("cancelReason") String cancelReason);

    /**
     * 调拨单取消交运
     *
     * @param waybillId
     * @param time
     * @param user
     */
    @Update("UPDATE transfer_order_info SET status_cd = 3 , waybill_id = NULL , offer_id = NULL ,shipment_id = NULL,modify_time = #{time},modify_user =#{user}  WHERE waybill_id = #{waybillId}")
    void cancelShipment(@Param("waybillId") String waybillId,
                        @Param("time") Timestamp time,
                        @Param("user") String user);

    @Select("SELECT transfer_order_cd, waybill_id, warehouse_start_id, warehouse_end_id, sub_warehouse_end_id, status_cd, offer_id, shipment_id, wh_label, remark, create_user, modify_user," +
            " modify_time, cancel_reason, delivery_date, sign_in_date, transfer_type_cd, random_value, serial_number FROM transfer_order_info WHERE waybill_id = #{waybillId}")
    TransferBasicInfoEntity getTransferBasicInfoByWayBillId(@Param("waybillId") String waybillId);

    /**
     * 为调拨单添加标签
     *
     * @param transferId
     * @param labelId
     * @param uuid
     */
    @Insert("INSERT INTO transfer_label_rel(uuid, transfer_order_id, label_id, delete_status) VALUES (#{uuid},#{transferId},#{labelId},0)")
    void addLabelForTransferId(@Param("uuid") String uuid,
                               @Param("transferId") String transferId,
                               @Param("labelId") Integer labelId);

    /**
     * 删除调拨单标签
     *
     * @param transferId
     * @param labelId
     * @return
     */
    @Update("UPDATE transfer_label_rel SET delete_status = 1 WHERE transfer_order_id = #{transferId} AND label_id = #{labelId}")
    void deleteLabel(@Param("transferId") String transferId,
                     @Param("labelId") String labelId);

    /**
     * 根据物流单号查询出对应的调拨单号
     *
     * @param waybillId
     * @return
     */
    @Select("SELECT transfer_order_cd FROM transfer_order_info WHERE waybill_id = #{waybillId}")
    String findTransferIdByWaybillId(@Param("waybillId") String waybillId);

    /**
     * 通过skuid和transferOrderCd搜索出对应的主键
     *
     * @param transferOrderCd
     * @param skuId
     * @return
     */
    @Select("SELECT commodity_info_cd FROM transfer_order_details WHERE transfer_order_cd = #{transferOrderCd} AND sku_id = #{skuId}")
    String findCommodityInfoCdByCdAndSku(@Param("transferOrderCd") String transferOrderCd,
                                         @Param("skuId") String skuId);

    /**
     * WMS 装箱数据回写改变状态
     *
     * @param statusCd
     * @param transferOrderCd
     */
    @Update("UPDATE transfer_order_info SET status_cd = #{statusCd}, modify_user = #{modifyUser} , modify_time = #{time} WHERE transfer_order_cd = #{transferOrderCd}")
    void updateStatusCdByCd(@Param("statusCd") int statusCd,
                            @Param("modifyUser") String modifyUser,
                            @Param("transferOrderCd") String transferOrderCd,
                            @Param("time") String time);

    @Update("UPDATE transfer_order_details SET delivery_quantity = #{deliveryNumber}, packing_quantity = #{packingQuantity} WHERE commodity_info_cd = #{commodityInfoCd}")
    void updatePackingQuantityByCd(@Param("deliveryNumber") int deliveryNumber,
                                   @Param("packingQuantity") int packingQuantity,
                                   @Param("commodityInfoCd") String commodityInfoCd);

    /**
     * 根据调拨单号查询出所有的发货签收调拨总数
     *
     * @param transferId
     * @return
     */
    @Select("SELECT sum(delivery_quantity) as deliveryNumber ,sum(sign_in_quantity) as signNumber ,sum(transfer_quantity) as replenishmentNumber FROM transfer_order_details WHERE transfer_order_cd = #{transferId}")
    SumNum getSumByTransferId(@Param("transferId") String transferId);

    @Select("SELECT wayBill_id FROM transfer_order_info WHERE transfer_order_cd = #{transferOrderCd}")
    String getWayBillIdByCd(@Param("transferOrderCd") String transferOrderCd);


    /**
     * 根据调拨单号和skuid去查询出清关信息
     *
     * @param transferId
     * @param skuId
     * @return
     */
    @Select("SELECT country_of_origin,texture_of_materia,purpose_desc,clearance_name,clearance_unit_price  FROM transfer_order_details WHERE transfer_order_cd = #{transferId} AND  sku_id = #{skuId}")
    TransferClearanceEntity findClearanceByTransferIdAndSkuId(@Param("transferId") String transferId,
                                                              @Param("skuId") String skuId);

    /**
     * 更新清关发票信息
     *
     * @param map
     */
    @Select("UPDATE transfer_order_details SET country_of_origin = #{countryOfOrigin} ,texture_of_materia = #{textureOfMateria} ,purpose_desc = #{purposeDesc} ,clearance_name = #{clearanceName} ,clearance_unit_price = #{clearanceUnitPrice} WHERE transfer_order_cd = #{transferId} AND sku_id = #{skuId} ")
    void updateClearanceInfo(Map map);

    /**
     * 根据运单号查询出下载所需的清关资料
     *
     * @param wayBillId
     */
    @Select("SELECT b.sku_id,b.clearance_name,b.country_of_origin,b.texture_of_materia,b.purpose_desc\n" +
            ",b.clearance_unit_price,b.transfer_quantity AS replenishmentQuantity FROM transfer_order_info a LEFT JOIN transfer_order_details b ON a.transfer_order_cd = b.transfer_order_cd\n" +
            "WHERE waybill_id = #{wayBillId}")
    List<ClearanceExcelEntity> findClearanceDownloadInfo(@Param("wayBillId") String wayBillId);


    /**
     * 根据调拨单号和skuid查询对应的申报信息
     *
     * @param transferId
     * @param skuId
     * @return
     */
    @Select("SELECT declare_unit_price,sku_unit_cd,declaration_elements FROM transfer_order_details WHERE  transfer_order_cd = #{transferId} AND  sku_id = #{skuId}")
    TransferDeclareEntity findDeclareInfoByTransferIdAndSkuId(@Param("transferId") String transferId,
                                                              @Param("skuId") String skuId);

    /**
     * 更新调拨单的报关资料信息
     *
     * @param transferId
     * @param skuId
     * @param declareUnitPrice
     * @param mskuUnitCd
     * @param declarationElements
     */
    @Update("UPDATE transfer_order_details SET declare_unit_price = #{declareUnitPrice} ,sku_unit_cd = #{mskuUnitCd},declaration_elements = #{declarationElements} WHERE transfer_order_cd = #{transferId} AND sku_id = #{skuId}")
    void updateDeclareInfo(@Param("transferId") String transferId,
                           @Param("skuId") String skuId,
                           @Param("declareUnitPrice") double declareUnitPrice,
                           @Param("mskuUnitCd") String mskuUnitCd,
                           @Param("declarationElements") String declarationElements);

    @Select("select transfer_order_cd from transfer_order_info where waybill_id=#{waybillId}")
    String getTransferInfo(@Param("waybillId") String waybillId);

    /**
     * 更新调拨的单发货信息
     *
     * @param transferId
     * @param skuId
     * @param packingNumber
     * @param deliveryNumber
     */
    @Update("UPDATE transfer_order_details SET packing_quantity = #{packingNumber} ,delivery_quantity = #{deliveryNumber} WHERE transfer_order_cd = #{transferId} AND  sku_id = #{skuId}")
    void upDeliveryInfo(@Param("transferId") String transferId,
                        @Param("skuId") String skuId,
                        @Param("packingNumber") int packingNumber,
                        @Param("deliveryNumber") int deliveryNumber);

    /**
     * 调拨单确认发货
     *
     * @param transferId
     * @param userId
     * @param currentDateTime
     */
    @Update("UPDATE transfer_order_info SET modify_user = #{userId} ,modify_time = #{currentDateTime}, status_cd = 5 ,delivery_date = #{currentDateTime} WHERE transfer_order_cd = #{transferId}")
    void delivery(@Param("transferId") String transferId,
                  @Param("userId") String userId,
                  @Param("currentDateTime") String currentDateTime);

    /**
     * 根据uuid去更新签收数量
     *
     * @param uuid
     * @param signInQuantity
     */
    @Update("UPDATE transfer_order_pack_info SET sign_quantity = #{signInQuantity}  WHERE uuid = #{uuid}")
    void updateSignInfoByUUid(@Param("uuid") String uuid,
                              @Param("signInQuantity") int signInQuantity);

    /**
     * 根据uuid查询出CommodityInfoCd
     *
     * @param uuid
     * @return
     */
    @Select("SELECT commodity_info_cd FROM transfer_order_pack_info WHERE uuid = #{uuid}")
    String getCommodityInfoCdByUUid(@Param("uuid") String uuid);

    /**
     * 更新调拨单的商品详情编号去更新签收数量
     *
     * @param commodityInfoCd
     * @param signNum
     */
    @Select("UPDATE transfer_order_details SET sign_in_quantity = #{signNum} WHERE commodity_info_cd = #{commodityInfoCd}")
    void updateSignInfoByCommodityInfoCd(@Param("commodityInfoCd") String commodityInfoCd,
                                         @Param("signNum") int signNum);

    /**
     * 签收调拨单
     *
     * @param userId
     * @param transferId
     * @param signInDate
     * @param currentDateTime
     */
    @Update("UPDATE transfer_order_info SET sign_in_date = #{signInDate} ,status_cd = 6,modify_user =#{userId} ,modify_time = #{currentDateTime}  WHERE transfer_order_cd = #{transferId} ")
    void sign(@Param("userId") String userId,
              @Param("transferId") String transferId,
              @Param("signInDate") Date signInDate,
              @Param("currentDateTime") String currentDateTime);

    /**
     * 更新调拨数量，调整装箱规格的时候调用
     *
     * @param transferNumber
     * @param commodityId
     */
    @Update("UPDATE transfer_order_details SET transfer_quantity = #{transferNumber} WHERE commodity_info_cd = #{commodityId}")
    void updateTransferQuantity(@Param("transferNumber") int transferNumber,
                                @Param("commodityId") String commodityId);

    /**
     * 根据单位编码查询出单位名称
     *
     * @param skuUnitCd
     * @return
     */
    @Select("SELECT msku_unit_name FROM declare_msk_unit_attr WHERE msku_unit_cd = #{skuUnitCd} ")
    String findClearanUnitNameById(@Param("skuUnitCd") String skuUnitCd);
}
