package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.dao.sql.FbaReplenishmentInfoSQL;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentInfoEntity;
import com.wisrc.replenishment.webapp.entity.LogisticOfferEnity;
import com.wisrc.replenishment.webapp.entity.ShipmentEnity;
import com.wisrc.replenishment.webapp.vo.FbaAmazonVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface FbaReplenishmentInfoDao {

    @SelectProvider(type = FbaReplenishmentInfoSQL.class, method = "findInfoByCond")
    List<FbaReplenishmentInfoEntity> findInfoByCond(@Param("createBeginTime") Date createBeginTime, @Param("createEndTime") Date createEndTime,
                                                    @Param("shopId") String shopId, @Param("warehouseId") String warehouseId, @Param("shipmentId") String shipmentId, @Param("fbaIds") String[] fbaIds,
                                                    @Param("statusCd") String statusCd, @Param("fbaQueryIds") String[] fbaQueryIds);

    /**
     * 通过补货单ID查找补货单信息
     */

    @Select("select fba_replenishment_id,shop_id,replenishment_quantity,packing_quantity,delivering_quantity,warehouse_id,sub_warehouse_id,create_time,create_user,replenishment_species,replenishment_count,offer_id,shipment_id," +
            "channel_name,status_cd,remark,batch_number,refercence_id,packlist_file,package_mark,fnsku_code,delete_status,cancel_reason,delivering_type_cd,pickup_type_cd,unit_cd,amazon_warehouse_address,amazon_warehouse_zipcode,collect_goods_warehouse_id from fba_replenishment_info where fba_replenishment_id=#{fbaReplenishmentId}")
    FbaReplenishmentInfoEntity findById(String fbaReplenishmentId);

    /**
     * 查找流水号，生成新的流水
     *
     * @return
     */
    @Select("SELECT MAX(SUBSTRING(fba_replenishment_id,2,11)) fbaReplenishmentId FROM fba_replenishment_info")
    String findMaxReplenishmentId();

    /**
     * 添加补货单
     *
     * @param infoEntity
     */
    @Insert("insert into fba_replenishment_info (fba_replenishment_id,shop_id,sub_warehouse_id,warehouse_id,shipment_id,channel_name,offer_id,create_time,create_user,replenishment_species,replenishment_count," +
            "remark,status_cd,modify_user,modify_time,delete_status,unit_cd,delivering_type_cd, random_value) values (#{fbaReplenishmentId},#{shopId},#{subWarehouseId},#{warehouseId},#{shipmentId},#{channelName},#{offerId},#{createTime},#{createUser}," +
            "#{replenishmentSpecies},#{replenishmentCount},#{remark},#{statusCd},#{modifyUser},#{modifyTime},#{deleteStatus},#{unitCd},#{deliveringTypeCd}, #{randomValue})")
    void saveInfo(FbaReplenishmentInfoEntity infoEntity);

    @Select("select fba_replenishment_id from fba_replenishment_info where random_value = #{randomValue}")
    String getFbaReplenishmentId(@Param("randomValue") String randomValue);

    /**
     * 修改补货单状态码
     *
     * @param modifyUser
     * @param modifyTime
     * @param statusCd
     * @param fbaReplenishmentId
     */
    @Update("update fba_replenishment_info set modify_user=#{modifyUser},modify_time=#{modifyTime},status_cd=#{statusCd} where fba_replenishment_id=#{fbaReplenishmentId}")
    void updateStatus(@Param("modifyUser") String modifyUser, @Param("modifyTime") String modifyTime, @Param("statusCd") int statusCd, @Param("fbaReplenishmentId") String fbaReplenishmentId);

    /**
     * 修改补货单物流商和渠道
     *
     * @param shipmentId
     * @param channelName
     * @param fbaReplenishmentId
     */
    @Update("update fba_replenishment_info set shipment_id=#{shipmentId},channel_name=#{channelName} where fba_replenishment_id=#{fbaReplenishmentId}")
    void updateShipAndChannel(@Param("shipmentId") String shipmentId, @Param("channelName") String channelName, @Param("fbaReplenishmentId") String fbaReplenishmentId);

    /**
     * 取消补货单
     *
     * @param infoEntity
     */
    @Update("update fba_replenishment_info set modify_user=#{modifyUser},modify_time=#{modifyTime},status_cd=#{statusCd},cancel_reason=#{cancelReason},delete_status=#{deleteStatus} where fba_replenishment_id=#{fbaReplenishmentId}")
    void cancelReplen(FbaReplenishmentInfoEntity infoEntity);

    /**
     * 补充亚马逊货件信息
     *
     * @param amazonInfo
     */
    @Update("update fba_replenishment_info set batch_number=#{batchNumber},pickup_type_cd=#{pickupTypeCd},refercence_id=#{refercenceId},packlist_file=#{packlistFile},package_mark=#{packgeMark},fnsku_code=#{fnskuCode}" +
            ",modify_user=#{modifyUser},modify_time=#{modifyTime}, amazon_warehouse_address=#{amazonWarehouseAddress},amazon_warehouse_zipcode=#{amazonWarehouseZipcode}, collect_goods_warehouse_id=#{collectGoodsWarehouseId} where fba_replenishment_id=#{fbaReplenishmentId}")
    void updateAmazonInfo(FbaAmazonVO amazonInfo);

    /**
     * 修改补货单商品补货量
     *
     * @param replenishmentQuantity
     * @param commodityId
     * @param modifyUser
     * @param modifyTime
     */
    @Update("update replenishment_msku_info set replenishment_quantity=#{replenishmentQuantity},modify_user=#{modifyUser},modify_time=#{modifyTime} where commodity_id=#{commodityId}")
    void updateRepQuantity(@Param("replenishmentQuantity") int replenishmentQuantity, @Param("commodityId") String commodityId,
                           @Param("modifyUser") String modifyUser, @Param("modifyTime") Timestamp modifyTime);

    /**
     * 通过补货单ID修改补货单状态码
     *
     * @param statusCd
     * @param fbaReplenishmentId
     */
    @Update("update fba_replenishment_info set status_cd=#{statusCd} where fba_replenishment_id=#{fbaReplenishmentId}")
    void updateStatusById(@Param("statusCd") int statusCd, @Param("fbaReplenishmentId") String fbaReplenishmentId);

    /**
     * 根据补货单Id查找运单id
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("select waybill_id from waybill_replenishment_rel where fba_replenishment_id=#{fbaReplenishmentId} and delete_status = 0")
    String findWayBillIdByFbaId(String fbaReplenishmentId);

    /**
     * 获取补货单某一状态的数量
     *
     * @param statusCd
     * @return
     */
    @Select("select count(1) from fba_replenishment_info where status_cd=#{statusCd}")
    int getStatusNumber(int statusCd);

    /**
     * 通过补货单状态码获取补货单状态码名称
     */
    @Select("SELECT status_name FROM replenishment_status_attr WHERE status_cd=#{statusCd}")
    String getStatusCdNameByCd(int statusCd);

    /**
     * 判断是否有重复的补货批次号
     *
     * @param batchNumber
     * @return
     */
    @Select("select batch_number from fba_replenishment_info where batch_number=#{batchNumber} and status_cd != 5")
    String getBatchNumber(String batchNumber);

    /**
     * 通过物流商ID查询补货单ID集合
     *
     * @param shipmentId
     * @return
     */
    @Select("select fba_replenishment_id from fba_replenishment_info where shipment_id=#{shipmentId}")
    List<String> findFbaIdByShipment(String shipmentId);

    /**
     * 通过补货单ID及补货批次查询补货单ID集合
     *
     * @param fbaReplenishmentId
     * @param batchNumber
     * @return
     */
    @SelectProvider(type = FbaReplenishmentInfoSQL.class, method = "findFbaByBatchAndId")
    List<String> findFbaByBatchAndId(@Param("fbaReplenishmentId") String fbaReplenishmentId, @Param("batchNumber") String batchNumber);

    /**
     * 通过物流交运单ID查询补货单ID集合
     *
     * @param waybillId
     * @return
     */
    @SelectProvider(type = FbaReplenishmentInfoSQL.class, method = "findFbaByWaybillId")
    List<String> findFbaByWaybillId(String waybillId);

    /**
     * 通过补货批次查询补货单ID
     *
     * @param batchNumber
     * @return
     */
    @Select("select fba_replenishment_id from fba_replenishment_info where batch_number=#{batchNumber}")
    String getFbaReplementId(String batchNumber);

    /**
     * 通过补货单ID查询报价ID
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("select offer_id from fba_replenishment_info where fba_replenishment_id=#{fbaReplenishmentId}")
    String getOfferIdByFbaId(String fbaReplenishmentId);

    /**
     * 查询待发货以及待签收(物流状态)的报价ID和物流单号实体集合
     *
     * @return
     */
    @Select("select  logistics_id, logistics_track_url from waybill_info where logistics_id is not null and logistics_type_cd in (1,2)")
    List<LogisticOfferEnity> getAllLogisticOffer();

    /**
     * 通过物流交运单ID查询补货单ID
     *
     * @param waybillId
     * @return
     */
    @Select("select fba_replenishment_id from waybill_replenishment_rel where waybill_id=#{waybillId}")
    String[] FbaId(String waybillId);

    /**
     * 查询待装箱、待发货、待签收（补货单状态）的补货批次和店铺ID
     *
     * @return
     */
    @Select("select batch_number, shop_id from v_replenishment_commodity_info where batch_number is not null and status_cd in (1,2,3)")
    List<ShipmentEnity> getAllShipmentEnity();

    @Select("select fba_replenishment_id, shop_id from fba_replenishment_info where batch_number=#{shipmentId} and status_cd!=5")
    List<FbaReplenishmentInfoEntity> findByBatchBumber(String shipmentId);

    /**
     * 通过muskId、店铺ID、补货单ID修改商品签收数量
     *
     * @param msku
     * @param fbaReplenishmentId
     * @param shopId
     * @param signInQuantity
     */
    @Update("update replenishment_msku_info set sign_in_quantity=#{signInQuantity} where msku_id=#{msku} and fba_replenishment_id=#{fbaReplenishmentId} and shop_id=#{shopId}")
    Integer batchUpdateSignNum(@Param("msku") String msku, @Param("fbaReplenishmentId") String fbaReplenishmentId, @Param("shopId") String shopId, @Param("signInQuantity") Integer signInQuantity);

    @Update("update fba_replenishment_info set status_cd=#{statusCd},modify_time=#{time} where batch_number=#{shipmentId} and status_cd!=5")
    void updateStatusByBatchNumber(@Param("statusCd") int statusCd, @Param("shipmentId") String shipmentId, @Param("time") String time);

    /**
     * 通过补货单ID查询店铺ID
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("select shop_id from fba_replenishment_info where fba_replenishment_id =#{fbaReplenishmentId}")
    String getShopId(String fbaReplenishmentId);

    /**
     * 通过补货单ID查询起运仓ID
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("SELECT warehouse_id FROM fba_replenishment_info WHERE fba_replenishment_id = #{fbaReplenishmentId}")
    String getWarehouseIdByFbaId(@Param("fbaReplenishmentId") String fbaReplenishmentId);

    @SelectProvider(type = FbaReplenishmentInfoSQL.class, method = "findAll")
    List<FbaReplenishmentInfoEntity> findAll(@Param("createBeginTime") Date createBeginTime, @Param("createEndTime") Date createEndTime,
                                             @Param("shopId") String shopId, @Param("warehouseId") String warehouseId, @Param("shipmentId") String shipmentId,
                                             @Param("statusCd") String statusCd);

    @Select("select fba_replenishment_id as fbaReplenishmentId, batch_number as bacthNumber from v_waybill_fba_replenishment_info where waybill_id=#{waybillId}")
    List<Map> getBatchNumberByWayId(String waybillId);

    @Select("select batch_number,warehouse_id,delivering_type_cd from fba_replenishment_info where fba_replenishment_id = #{fbaReplenishmentId}")
    FbaReplenishmentInfoEntity getBatchNumberByReplenishmentId(@Param("fbaReplenishmentId") String fbaReplenishmentId);

    @Select("SELECT remark from fba_replenishment_info WHERE fba_replenishment_id = #{fbaReplenishmentId}")
    String getRemarkById(@Param("fbaReplenishmentId") String fbaReplenishmentId);

    @Select("select fba_replenishment_id from waybill_replenishment_rel where waybill_id=#{waybillId}")
    List<String> getFbaReplementIdByWaybill(String waybillId);

    @Select("select waybill_id as waybillId, batch_number as batchNumber from v_waybill_fba_replenishment_info where pickup_type_cd=2 and logistics_type_cd in (1,2) and batch_number is not null")
    List<Map> getWaybillAndShipmentId();

    @Select("SELECT packlist_file FROM  fba_replenishment_info where fba_replenishment_id = #{fbaReplenishmentId}")
    String findWmsInfoById(@Param("fbaReplenishmentId") String fbaReplenishmentId);

    @Select("select status_cd from fba_replenishment_info where fba_replenishment_id = #{fbaReplenishmentId}")
    int getFbaStatusCd(@Param("fbaReplenishmentId") String fbaReplenishmentId);
}
