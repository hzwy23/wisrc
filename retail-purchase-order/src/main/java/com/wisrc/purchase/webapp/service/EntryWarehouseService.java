package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.entity.EntryWarehouseEntity;
import com.wisrc.purchase.webapp.entity.EntryWarehouseProductEntity;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.EntryWarehouseExportVo;
import com.wisrc.purchase.webapp.vo.entrywarehouse.EntryWarehouseAllVO;
import com.wisrc.purchase.webapp.vo.entrywarehouse.ReturnEntryPara;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;

public interface EntryWarehouseService {
    /**
     * 查询入库订单内产品信息
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
    LinkedHashMap infoList(int startPage, int pageSize, String entryId, Date entryTimeBegin, Date entryTimeEnd, String supplierDeliveryNum, String inspectionId, String entryUser, String warehouseId, String supplierId);

    /**
     * 查询入库订单内产品信息
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
    LinkedHashMap infoList(String entryId, Date entryTimeBegin, Date entryTimeEnd, String supplierDeliveryNum, String inspectionId, String entryUser, String warehouseId, String supplierId);

    /**
     * 修改入库订单信息
     *
     * @param ele
     */
    Result updateInfoById(EntryWarehouseEntity ele, List<EntryWarehouseProductEntity> productList);

    /**
     * 新增采购入库单信息
     *
     * @param entryAllVO
     * @param userId
     */
    Result addInfo(EntryWarehouseAllVO entryAllVO, String userId);

    /**
     * 逻辑删除入库订单信息（delete_status 0--正常  1--删除）
     *
     * @param entryId
     */
    void updateStatusById(String entryId, String user);

    /**
     * 通过入库单号查询所有信息
     *
     * @param entryId
     * @return
     */
    EntryWarehouseAllVO findInfoById(String entryId);

    /**
     * 批量删除入库单
     */
    void delete(ReturnEntryPara returnEntryPara, String userId);

    /**
     * 根据条件查询采购入库订单信息
     *
     * @return
     */
    LinkedHashMap findInfoIds(String[] ids);

    /**
     * 根据到货通知单号去查询所有的采购入库单号
     *
     * @param inspectionId
     * @return
     */
    List<String> findAllEntryIdByInspectionId(String inspectionId);

    /**
     * 根据采购入库单和skuId 去查询出相对应的采购入库的货物信息
     *
     * @param entryId
     * @param skuId
     * @return
     */
    EntryWarehouseProductEntity getEntryProductByEntryIdAndSkuId(String entryId, String skuId);

    List<EntryWarehouseProductEntity> getEntryProductByEntryIds(List<String> ids);


    LinkedHashMap infoListNew(int num, int size, String entryId, Date entryTimeBegin, Date entryTimeEnd, String supplierDeliveryNum, String inspectionId, String entryUser, String warehouseId, String supplierName, String orderId, String skuId, String productName);

    LinkedHashMap infoListNew(String entryId, Date entryTimeBegin, Date entryTimeEnd, String supplierDeliveryNum, String inspectionId, String entryUser, String warehouseId, String supplierName, String orderId, String skuId, String productName);

    List<EntryWarehouseExportVo> infoListNewExport(String entryId, Date entryTimeBegin, Date entryTimeEnd, String supplierDeliveryNum, String inspectionId, String entryUser, String warehouseId, String supplierName, String orderId, String skuId, String productName);
}
