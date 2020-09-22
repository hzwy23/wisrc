package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.entity.LogisticsBillEnity;
import com.wisrc.replenishment.webapp.vo.CustomsClerlanceVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LogisticsBillService {

    /**
     * 新增清关发票
     *
     * @param billEnity 清关发票信息
     */
    void addBillInfo(LogisticsBillEnity billEnity);

    /**
     * 查询清关发票信息
     *
     * @param cusVo 查询时用到的参数
     */
    LogisticsBillEnity findBillDetail(CustomsClerlanceVo cusVo);

    /**
     * 删除清关发票信息
     *
     * @param wayBillId 运单号ID
     */
    void deleteByWayBIllId(String wayBillId);

    /**
     * 删除清关发票信息
     *
     * @param shopId 商店ID
     * @param mskuId 商品ID
     */
    void deleteClearanceProduct(String shopId, String mskuId);

    /**
     * 查询清关发票信息
     *
     * @param wayBillId 运单ID
     */
    LogisticsBillEnity findClearanceDetail(String wayBillId);

    /**
     * 下载清关发票信息
     *
     * @param wayBillId 运单ID
     */
    void waybillExcel(String wayBillId, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
