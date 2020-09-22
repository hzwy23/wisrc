package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.EntryWarehouseSQL;
import com.wisrc.purchase.webapp.entity.EntryWarehouseEntity;
import com.wisrc.purchase.webapp.vo.EntryWarehouseExportVo;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface EntryWarehouseDao {
    /**
     * 根据条件查询采购入库订单信息
     *
     * @param entryId
     * @param entryTimeBegin
     * @param entryTimeEnd
     * @param supplierDeliveryNum
     * @param inspectionId
     * @param entryUser
     * @param warehouseId
     * @return
     */
    @SelectProvider(type = EntryWarehouseSQL.class, method = "findInfo")
    List<EntryWarehouseEntity> findInfo(@Param("entryId") final String entryId,
                                        @Param("entryTimeBegin") final Date entryTimeBegin,
                                        @Param("entryTimeEnd") final Date entryTimeEnd,
                                        @Param("supplierDeliveryNum") String supplierDeliveryNum,
                                        @Param("inspectionId") final String inspectionId,
                                        @Param("entryUser") final String entryUser,
                                        @Param("warehouseId") final String warehouseId,
                                        @Param("supplierId") final String supplierId);

    /**
     * 根据条件查询采购入库订单信息
     *
     * @return
     */
    @SelectProvider(type = EntryWarehouseSQL.class, method = "findInfoIds")
    List<EntryWarehouseEntity> findInfoIds(@Param("ids") final String[] ids);

    /**
     * 查询所有入库订单信息
     *
     * @return
     */
    @Select("select entry_id,entry_time,entry_user,warehouse_id,inspection_id,supplier_cd,supplier_delivery_num,create_time,create_user,order_id,remark,modify_user,modify_time,delete_status, pack_warehouse_id from entry_warehouse_info where delete_status=0")
    List<EntryWarehouseEntity> findInfoAll();

    /**
     * 通过入库单查询入库订单信息
     *
     * @return
     */
    @Select("select entry_id,entry_time,entry_user,warehouse_id,inspection_id,supplier_cd,supplier_delivery_num,create_time,create_user,order_id,remark,modify_user,modify_time,delete_status,pack_warehouse_id from entry_warehouse_info where delete_status=0 and entry_id = #{entryId}")
    EntryWarehouseEntity findInfoById(@Param("entryId") String entryId);

    /**
     * 通过采购订单查询入库订单信息
     *
     * @return
     */
    @Select("select entry_id,entry_time,entry_user,warehouse_id,inspection_id,supplier_cd,supplier_delivery_num,create_time,create_user,order_id,remark,modify_user,modify_time,delete_status,pack_warehouse_id from entry_warehouse_info where delete_status=0 and order_id = #{orderId}")
    List<EntryWarehouseEntity> findInfoxById(@Param("orderId") String orderId);

    /**
     * 逻辑删除入库订单信息（delete_status 0--正常  1--删除）
     *
     * @param entryId
     */
    @Update("update entry_warehouse_info set delete_status = 1,modify_time=#{modifyTime},modify_user=#{modifyUser} where entry_id = #{entryId}")
    void updateStatusById(@Param("entryId") String entryId, @Param("modifyTime") Timestamp modifyTime, @Param("modifyUser") String modifyUser);

    /**
     * 修改入库订单信息
     *
     * @param ele
     */
    @Update("update entry_warehouse_info set entry_time=#{entryTime},entry_user=#{entryUser},warehouse_id=#{warehouseId},inspection_id=#{inspectionId},supplier_cd=#{supplierCd},supplier_delivery_num=#{supplierDeliveryNum},order_id=#{orderId},remark=#{remark},modify_user=#{modifyUser},modify_time=#{modifyTime}, pack_warehouse_id = #{packWarehouseId} where entry_id = #{entryId}")
    void updateInfoById(EntryWarehouseEntity ele);

    /**
     * 查询采购入库单是否已存在
     *
     * @param entryId
     * @return
     */
    @Select("select count(*) from entry_warehouse_info where  entry_id = #{entryId}")
    int numByid(@Param("entryId") String entryId);

    /**
     * 新增采购入库单信息
     *
     * @param ele
     */
    @Insert("insert into entry_warehouse_info (entry_id,entry_time,entry_user,warehouse_id,inspection_id,supplier_cd,supplier_delivery_num,create_time,create_user,order_id,remark,modify_user,modify_time,delete_status, pack_warehouse_id, random_value) values (#{entryId},#{entryTime},#{entryUser},#{warehouseId},#{inspectionId},#{supplierCd},#{supplierDeliveryNum},#{createTime},#{createUser},#{orderId},#{remark},#{modifyUser},#{modifyTime},#{deleteStatus},#{packWarehouseId},#{entryId})")
    void addInfo(EntryWarehouseEntity ele);

    /**
     * 查询数据库入库单号最大的Id
     *
     * @return
     */
    @Select("select entry_id from entry_warehouse_info where random_value = #{randomValue}")
    String findEntryId(String randomValue);

    @Select("SELECT entry_id\n" +
            "  FROM entry_warehouse_info\n" +
            "WHERE inspection_id = #{inspectionId}")
    List<String> findAllEntryIdByInspectionId(String inspectionId);

    @SelectProvider(type = EntryWarehouseSQL.class, method = "findInfoNew")
    List<EntryWarehouseEntity> findInfoNew(@Param("entryId") String entryId, @Param("entryTimeBegin") Date entryTimeBegin, @Param("entryTimeEnd") Date entryTimeEnd, @Param("supplierDeliveryNum") String supplierDeliveryNum, @Param("inspectionId") String inspectionId, @Param("entryUser") String entryUser, @Param("warehouseId") String warehouseId, @Param("supplierName") String supplierName, @Param("suppliers") String suppliers, @Param("skuIds") String skuIds, @Param("orderId") String orderId, @Param("skuId") String skuId);

    @SelectProvider(type = EntryWarehouseSQL.class, method = "findInfoNewExport")
    List<EntryWarehouseExportVo> findInfoNewExport(@Param("entryId") String entryId, @Param("entryTimeBegin") Date entryTimeBegin, @Param("entryTimeEnd") Date entryTimeEnd, @Param("supplierDeliveryNum") String supplierDeliveryNum, @Param("inspectionId") String inspectionId, @Param("entryUser") String entryUser, @Param("warehouseId") String warehouseId, @Param("supplierName") String supplierName, @Param("suppliers") String suppliers, @Param("skuIds") String skuIds, @Param("orderId") String orderId, @Param("skuId") String skuId);
}
