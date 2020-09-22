package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.LogisticsOfferHistoryInfoEntity;
import com.wisrc.shipment.webapp.utils.Result;

import java.util.LinkedHashMap;

public interface LogisticsOfferHistoryInfoService {
    /**
     * 新增物流报价同时增加详细价格表记录。
     *
     * @param ele
     */
    void add(LogisticsOfferHistoryInfoEntity ele);

    /**
     * 根据报价ID查询历史报价记录
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @param offerId  报价ID
     * @return
     */
    LinkedHashMap findHistory(int pageNum, int pageSize, String offerId);

    Result batchPrice(String[] offerId, double num);

    //Result deletePrice(String uuid);

    void delete(String offerId, String userId);

    void physicalDeleteHis(String offerId);
}
