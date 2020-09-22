package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.dao.sql.WaybillInfoSQL;
import com.wisrc.replenishment.webapp.entity.*;
import com.wisrc.replenishment.webapp.query.waybillInfo.WayBillInfoQuery;
import com.wisrc.replenishment.webapp.vo.waybill.PerfectWaybillInfoVO;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface WaybillInfoDao {
    /**
     * 根据条件查询物流跟踪单信息集合
     *
     * @param waybillOrderDateBegin
     * @param waybillOrderDateEnd
     * @param offerIds
     * @param customsCd
     * @param warehouseId
     * @param logisticsTypeCd
     * @param Keyword
     * @return
     */
    @SelectProvider(type = WaybillInfoSQL.class, method = "search")
    List<WaybillInfoEntity> search(@Param("waybillOrderDateBegin") Date waybillOrderDateBegin,
                                   @Param("waybillOrderDateEnd") Date waybillOrderDateEnd,
                                   @Param("offerId") List<String> offerIds,
                                   @Param("customsCd") Integer customsCd,
                                   @Param("warehouseId") String warehouseId,
                                   @Param("logisticsTypeCd") Integer logisticsTypeCd,
                                   @Param("Keyword") String Keyword,
                                   @Param("waybillId") String waybillId,
                                   @Param("batchNumber") String batchNumber,
                                   @Param("fbaReplenishmentId") String fbaReplenishmentId,
                                   @Param("mskuListStr") String mskuListStr,
                                   @Param("isLackLogistics") String isLackLogistics,
                                   @Param("isLackShipment") String isLackShipment,
                                   @Param("isLackCustomsDeclare") String isLackCustomsDeclare,
                                   @Param("isLackCustomsClearance") String isLackCustomsClearance);

    /**
     * 查询跟踪单产品信息
     *
     * @param waybillId
     * @return
     */
    @Select("select msku_id,replenishment_quantity from v_waybill_msku_info where waybill_id = #{waybillId}")
    List<WaybillMskuInfoEntity> findMskuList(@Param("waybillId") String waybillId);

    /**
     * 查询跟踪单补货单信息
     *
     * @param waybillId
     * @return
     */
    @Select("select fba_replenishment_id,replenishment_quantity,batch_number,warehouse_id,pickup_type_cd,pickup_type_name from v_waybill_fba_replenishment_info where waybill_id = #{waybillId}")
    List<WaybillReplenishmentEntity> findReplenishmentList(@Param("waybillId") String waybillId);

    /**
     * 查询运费估算信息
     *
     * @param waybillId
     * @return
     */
    @Select(" select waybill_id,weigh_type_cd,customs_type_cd,estimate_date,unit_price,total_weight,freight_total,annex_cost,discounted_amount,total_cost,delete_status,create_user,create_time,modify_user,modify_time from freight_estimate_info where waybill_id = #{waybillId}")
    List<FreightEstimateinfoEntity> findFreightInfo(@Param("waybillId") String waybillId);

    /**
     * 查询物流信息
     *
     * @param logisticsId
     * @return
     */
    @Select("select logistics_id,record_time,description from logistics_track_info where logistics_id=#{logisticsId}")
    List<LogisticsTrackInfoEntity> findLogisticsList(@Param("logisticsId") String logisticsId);

    /**
     * 跟踪单ID查询跟踪单基本信息
     *
     * @param waybillId
     * @return
     */
    @Select("select logistics_track_url,so_no_url,waybill_id,waybill_order_date,offer_id,logistics_id,sign_in_date,logistics_surface_url,examining_report_url,logistics_type_cd,remark,delete_status,delete_random,create_time,create_user,modify_time,modify_user,is_lack_shipment,is_lack_logistics,is_lack_customs_declare,is_lack_customs_clearance, code_cd from waybill_info where waybill_id =#{waybillId}")
    WaybillInfoEntity findInfoById(@Param("waybillId") String waybillId);

    /**
     * 查询运单异常信息
     *
     * @param waybillId
     * @return
     */
    @Select("select uuid,exception_type_cd,waybill_id,exception_type_desc from waybill_exception_info where waybill_id = #{waybillId}")
    List<WaybillExceptionInfoEntity> findExcList(@Param("waybillId") String waybillId);

    @Delete("delete from waybill_exception_info where uuid = #{uuid}")
    void deleteExc(@Param("uuid") String uuid);

    /**
     * 查询数据库跟踪号最大的Id
     *
     * @return
     */
    @Select("SELECT MAX(SUBSTRING(waybill_id,2,11)) waybillId FROM waybill_info")
    String findWaybillId();

    /**
     * 新增物流跟踪单基本信息
     *
     * @param ele
     * @return
     */
    @Insert("insert into waybill_info (waybill_id,waybill_order_date,offer_id,logistics_id,sign_in_date,logistics_surface_url,examining_report_url,logistics_type_cd,remark,delete_status,delete_random ,create_time,create_user,modify_time,modify_user,is_lack_shipment,is_lack_logistics,is_lack_customs_declare,is_lack_customs_clearance,random_value)" +
            " values(#{waybillId},#{waybillOrderDate},#{offerId},#{logisticsId},#{signInDate},#{logisticsSurfaceUrl},#{examiningReportUrl},#{logisticsTypeCd},#{remark},#{deleteStatus},#{deleteRandom} ,#{createTime},#{createUser},#{modifyTime},#{modifyUser},0,0,0,0,#{randomValue})")
    void addInfo(WaybillInfoEntity ele);

    /**
     * 新增运费估算信息
     *
     * @param ele
     * @return
     */
    @Insert("insert into freight_estimate_info (waybill_id,weigh_type_cd,customs_type_cd,estimate_date,unit_price,total_weight,freight_total,annex_cost,other_fee,discounted_amount,total_cost,delete_status,create_user,create_time,modify_user,modify_time)" +
            " values(#{waybillId},#{weighTypeCd},#{customsTypeCd},#{estimateDate},#{unitPrice},#{totalWeight},#{freightTotal},#{annexCost},#{otherFee},#{discountedAmount},#{totalCost},#{deleteStatus},#{createUser},#{createTime},#{modifyUser},#{modifyTime})")
    void addFreighInfo(FreightEstimateinfoEntity ele);

    /**
     * 修改运费估算信息
     *
     * @param ele
     * @return
     */
    @Update("update freight_estimate_info set weigh_type_cd=#{weighTypeCd},customs_type_cd=#{customsTypeCd},estimate_date=#{estimateDate},unit_price=#{unitPrice}," +
            "total_weight=#{totalWeight},freight_total=#{freightTotal},annex_cost=#{annexCost},discounted_amount=#{discountedAmount},total_cost=#{totalCost},delete_status=#{deleteStatus},create_user=#{createUser},create_time=#{createTime},modify_user=#{modifyUser},modify_time=#{modifyTime} where waybill_id=#{waybillId}")
    void updateFreighInfo(FreightEstimateinfoEntity ele);

    /**
     * 修改物流商渠道（物流跟踪单）
     */
    @Update("update waybill_info set offer_id = #{offerId} where waybill_id=#{waybillId}")
    void updateOfferId(@Param("waybillId") String waybillId, @Param("offerId") String offerId);

    /**
     * 修改物流商渠道（补货单）
     */
    @Update("update fba_replenishment_info set shipment_id = #{shipmentId},channel_name= #{channelName} where fba_replenishment_id=#{fbaReplenishmentId}")
    void updateFbaOfferId(@Param("fbaReplenishmentId") String fbaReplenishmentId, @Param("shipmentId") String shipmentId, @Param("channelName") String channelName);


    @Update("update fba_replenishment_info set shipment_id = #{shipmentId},offer_id=#{offerId} where fba_replenishment_id=#{fbaReplenishmentId}")
    void updateFba(@Param("fbaReplenishmentId") String fbaReplenishmentId, @Param("shipmentId") String shipmentId, @Param("offerId") String offerId);

    /**
     * 判断此补货单是否之前有交运过
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("select count(*) from v_waybill_fba_replenishment_info where fba_replenishment_id = #{fbaReplenishmentId} and logistics_type_cd !=4 and delete_status = 0")
    int ifDeliveries(@Param("fbaReplenishmentId") String fbaReplenishmentId);

    /**
     * 新增运单异常信息
     *
     * @param ele
     * @return
     */
    @Insert("insert into waybill_exception_info (uuid,exception_type_cd,waybill_id,exception_type_desc,delete_status,delete_random,modify_user,modify_time) " +
            "values(#{uuid},#{exceptionTypeCd},#{waybillId},#{exceptionTypeDesc},#{deleteStatus},#{deleteRandom},#{modifyUser},#{modifyTime})")
    void addException(WaybillExceptionInfoEntity ele);

    @Select("SELECT exception_type_desc FROM waybill_exception_info WHERE waybill_id = #{waybillId}")
    List<String> getExceptionTypeDescList(@Param("waybillId") String waybillId);

    @Delete("delete from waybill_exception_info where waybill_id = #{waybillId}")
    void deleteException(@Param("waybillId") String waybillId);

    /**
     * 补货交运信息
     *
     * @param ele
     */
    @Insert("insert into waybill_replenishment_rel (uuid,fba_replenishment_id,waybill_id,delete_status) " +
            "values (#{uuid},#{fbaReplenishmentId},#{waybillId},0)")
    void addRel(WaybillRelEntity ele);

    /**
     * 逻辑删除物流跟踪单
     *
     * @param waybillId
     */
    @Update("update waybill_info set logistics_type_cd = 4,modify_time=#{modifyTime}, modify_user=#{modifyUser} where waybill_id = #{waybillId}")
    void deleteWaybill(@Param("waybillId") String waybillId, @Param("modifyTime") Timestamp modifyTime, @Param("modifyUser") String modifyUser);

    /**
     * 修改物流跟踪单状态
     *
     * @param waybillId
     */
    @Update("update waybill_info set logistics_type_cd = #{logisticsTypeCd},modify_time=#{modifyTime}, modify_user=#{modifyUser},delivery_time=#{modifyTime} where waybill_id = #{waybillId}")
    void upWaybillStatus(@Param("waybillId") String waybillId, @Param("modifyTime") Timestamp modifyTime, @Param("modifyUser") String modifyUser, @Param("logisticsTypeCd") int logisticsTypeCd);

    /**
     * 逻辑删除预估运费信息
     *
     * @param waybillId
     */
    @Update("update freight_estimate_info set delete_status = 1,modify_time=#{modifyTime}, modify_user=#{modifyUser} where waybill_id = #{waybillId} ")
    void deleteFreight(@Param("waybillId") String waybillId, @Param("modifyTime") Timestamp modifyTime, @Param("modifyUser") String modifyUser);

    /**
     * 完善更新物流信息
     *
     * @param vo
     */
    @Update("update waybill_info set  logistics_id=#{logisticsId},logistics_surface_url=#{logisticsSurfaceUrl}, examining_report_url=#{examiningReportUrl}, estimate_date=#{estimateDate},modify_time=#{modifyTime},modify_user=#{modifyUser} where waybill_id = #{waybillId}")
    void updateWaybill(PerfectWaybillInfoVO vo);

    /**
     * 是否缺少报关信息
     *
     * @param waybillId
     * @return
     */
    @Select("select count(*) from customs_info where waybill_id = #{waybillId}")
    int numCustoms(@Param("waybillId") String waybillId);

    /**
     * 是否缺少清关发票
     *
     * @param waybillId
     * @return
     */
    @Select("select count(*) from customs_clearance_invoice_info where waybill_id = #{waybillId}")
    int numClearance(@Param("waybillId") String waybillId);

    /**
     * 少物流信息数量 不含亚马逊自提的 代码2
     */
    @Select("select count(distinct waybill_id) from v_waybill_info wi where is_lack_logistics = 0 AND exists ( SELECT 1 FROM v_waybill_fba_replenishment_info wfri WHERE wfri.pickup_type_cd != 2 " +
            "AND wi.waybill_id = wfri.waybill_id ) AND logistics_type_cd < 4")
    int logisticsInfoNum();

    /**
     * 少发货数数量
     */
    @Select("select count(distinct waybill_id) from v_waybill_info where is_lack_shipment = 0 and left(warehouse_id,1) not in ('A','F') AND logistics_type_cd < 4")
    int deliveryNum();

    /**
     * 少报关资料数量
     */
    @Select("select count(distinct waybill_id) from v_waybill_info where is_lack_customs_declare = 0 and customs_type_cd = 1 AND logistics_type_cd < 4")
    int icustomsInfoNum();

    /**
     * 少清关发票数量,不含海外仓 代码B
     */
    @Select("select count(distinct waybill_id) from v_waybill_info where is_lack_customs_clearance = 0 and left(warehouse_id,1) != 'B' AND logistics_type_cd < 4 ")
    int clearanceNum();

    /**
     * 逻辑删除物流跟踪单与补货单的关联关系
     *
     * @param waybillId
     * @param deleteStatus
     */
    @Update("update waybill_replenishment_rel set delete_status = #{deleteStatus} where waybill_id = #{waybillId} ")
    void deleteRelation(@Param("waybillId") String waybillId, @Param("deleteStatus") int deleteStatus);

    @Update("update waybill_info set is_lack_customs_declare = 1 where waybill_id=#{waybillId} ")
    void updateCustoms(String waybillId);

    @Update("update waybill_info set is_lack_shipment =1 where waybill_id=(select waybill_id from waybill_replenishment_rel where fba_replenishment_id = (select fba_replenishment_id from replenishment_msku_info where replenishment_commodity_id=#{replenishmentCommodityId} ))")
    void updateReplenishment(@Param("replenishmentCommodityId") String replenishmentCommodityId);

    @Update("update waybill_info set is_lack_logistics = 1 where waybill_id=#{waybillId} ")
    void updateLogistics(String waybillId);

    /**
     *
     */
    @Update("update waybill_info set sign_in_date = #{signInDate},logistics_type_cd = 3 where waybill_id =#{waybillId}")
    void updatesignInDate(@Param("waybillId") String waybillId, @Param("signInDate") Date signInDate);

    @Select("select waybill_id from v_waybill_fba_replenishment_info where fba_replenishment_id = #{fbaReplenishmentId} and logistics_type_cd !=4 and delete_status = 0 limit 0,1")
    String findWaybillIdByFbaId(String fbaReplenishmentId);

    @Update("update waybill_info set sign_in_date=#{signTime} where logistics_id=#{trackingNumber}")
    void updateWaybillTransfer(Map tracingRecordMap);

    @Update("update logistics_track_info set description=#{trackingInfo} where logistics_id=#{trackingNumber}")
    void updateTrackInfo(Map tracingRecordMap);

    @Select("select logistics_id from logistics_track_info where logistics_id=#{trackingNumber}")
    String getId(Map tracingRecordMap);

    @Insert("insert into logistics_track_info (logistics_id, record_time, description) values (#{trackingNumber}, now(), #{trackingInfo})")
    void insertTrackInfo(Map tracingRecordMap);

    @Select("select waybill_id from waybill_info where logistics_id=#{trackingNumber}")
    String getWayBillId(Map tracingRecordMap);

    @Update("update freight_estimate_info set estimate_date=#{signInDate} where waybill_id=#{waybillId}")
    void updateFreighInfoByWayBIllId(@Param("signInDate") String signInDate, @Param("waybillId") String waybillId);

    @Select("SELECT sum(sign_in_quantity) as sign_number ,sum(replenishment_quantity) as replenishment_number ,sum(delivery_number) as delivery_number FROM replenishment_msku_pack_info " +
            "where replenishment_commodity_id in (SELECT replenishment_commodity_id FROM replenishment_msku_info " +
            "where fba_replenishment_id in (SELECT fba_replenishment_id FROM waybill_replenishment_rel where waybill_id=#{waybillId} and delete_status=0) and delete_status=0) and delete_status=0")
    SumNum getNum(@Param("waybillId") String waybillId);

    @Update("update waybill_info SET remark = #{remark} WHERE waybill_id = #{waybillId}")
    void updateRemark(@Param("waybillId") String waybillId,
                      @Param("remark") String remark);

    @Update("update freight_estimate_info set estimate_date=#{expectDeliveryDate} where waybill_id=#{waybillId}")
    void updateFright(@Param("expectDeliveryDate") String expectDeliveryDate, @Param("waybillId") String waybillId);


    @SelectProvider(type = WaybillInfoSQL.class, method = "waybillExcel")
    List<LogisticWaybillExcel> waybillExcel(WayBillInfoQuery wayBillInfoQuery);

    @Update("update waybill_info set sign_in_date=#{time}, logistics_type_cd=#{status} where waybill_id=#{waybillId}")
    void updateStatusAndTime(@Param("time") String time, @Param("status") int status, @Param("waybillId") String waybillId);

    @Select("SELECT logistics_surface_url FROM waybill_info WHERE waybill_id = #{waybillId}")
    String findLogisticsUrlById(String waybillId);

    @Select("SELECT fba_replenishment_id from waybill_replenishment_rel WHERE waybill_id = #{waybillId}")
    List<String> findFbaIdByWayBillId(@Param("waybillId") String waybillId);

    @Select("select sum(sign_in_quantity) as sign_number ,sum(replenishment_quantity) as replenishment_number ,sum(delivery_number) as delivery_number from replenishment_msku_info where fba_replenishment_id=#{fbaId}")
    SumNum getSumNum(String fbaId);

    @Select("SELECT waybill_id from waybill_info WHERE random_value = #{randomValue}")
    String findWaybillIdByRandomValue(@Param("randomValue") String randomValue);

    @Update("update waybill_info set logistics_type_cd = #{statusCd},modify_time=#{time} where waybill_id =#{waybillId}")
    void updateStatus(@Param("statusCd") int statusCd, @Param("waybillId") String waybillId, @Param("time") String time);

    @Update("update transfer_order_info set status_cd=5, sign_in_date=#{signTime} where waybill_id=#{waybillId}")
    void updateTransferOrder(Map tracingRecordMap);

    @Select("SELECT discounted_amount FROM freight_estimate_info WHERE waybill_id = #{waybillId}")
    double findDiscountedAmountByWaybillId(@Param("waybillId") String waybillId);
}
