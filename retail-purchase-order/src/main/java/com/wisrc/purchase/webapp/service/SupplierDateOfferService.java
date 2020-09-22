package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.entity.PurchaseDateOfferLogEntity;
import com.wisrc.purchase.webapp.entity.SupplierDateOfferEntity;
import com.wisrc.purchase.webapp.entity.TeamStatusAttrEntity;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.supplierDateOffer.SupplierDateOfferPageVo;
import com.wisrc.purchase.webapp.vo.supplierDateOffer.SupplierDateOfferVO;

import java.util.LinkedHashMap;
import java.util.List;

public interface SupplierDateOfferService {
    /**
     * 通过条件查询供应商交期及报价信息
     *
     * @param employeeId
     * @param supplierId
     * @param skuId
     * @param statusCd
     * @return
     */
    LinkedHashMap findInfo(int startPage, int pageSize, String employeeId, String supplierId, String skuId, int statusCd);

    LinkedHashMap findInfo(String employeeId, String supplierId, String skuId, int statusCd);

    /**
     * ID查询信息
     *
     * @param supplierOfferId
     * @return
     */
    SupplierDateOfferVO findInfoById(String supplierOfferId);

    /**
     * 根据供应商ID查询上一次选择供应商时候的运输时间-国内
     *
     * @param supplierId
     * @return
     */
    SupplierDateOfferEntity findInfoBySupplier(String supplierId);

    /**
     * 修改全表信息
     *
     * @param supplierDateOfferEntity
     */
    Result updateInfo(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 逻辑删除
     *
     * @param supplierDateOfferEntity
     */
    Result delInfo(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 变更采购员
     *
     * @param supplierDateOfferEntity
     */
    Result upEmployee(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 变更状态
     *
     * @param supplierDateOfferEntity
     */
    Result upStatus(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 变更运输时间
     *
     * @param supplierDateOfferEntity
     */
    Result upDate(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 新增信息
     *
     * @param supplierDateOfferEntity
     */
    Result insertInfo(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 删除信息
     *
     * @param supplierOfferId
     */
    @Deprecated
    void deleteInfo(String supplierOfferId);

    /**
     * 查询状态码表
     *
     * @return
     */
    List<TeamStatusAttrEntity> findTeamAttr();

    /**
     * 判断供应商交期及报价限制库存sku和供应商是否同时重复
     *
     * @param supplierId
     * @param skuId
     * @return
     */
    int findRepeat(String supplierId, String skuId);

    /**
     * 获取物流报价的操作日志信息
     */
    List<PurchaseDateOfferLogEntity> findLogsById(String supplierOfferId);

    /**
     * 通过条件查询供应商交期及报价信息
     *
     * @return
     */
    Result supplierOfferPage(SupplierDateOfferPageVo supplierDateOfferPageVo);
}
