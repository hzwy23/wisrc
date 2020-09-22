package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.entity.OrderTracingDetailEnity;
import com.wisrc.purchase.webapp.vo.OrderTracingCompleteVo;
import com.wisrc.purchase.webapp.vo.OrderTracingEntryVo;
import com.wisrc.purchase.webapp.vo.OrderTracingReturnVo;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

public interface OrderTracingService {
    /**
     * 条件查询采购单相关信息(包含条数信息)
     *
     * @return
     */
    LinkedHashMap findTracingOrderByCond(String pageNum, String pageSize, String orderId, String startTime, String endTime, String employeeId, String supplierId, int tiicketOpenCd, int customsTypeCd, String sku, int deliveryTypeCd, String keywords, String skuName) throws ParseException;

    /**
     * 查询满足条件的记录的条数
     *
     * @return
     */
    int getCount(String orderId, String startTime, String endTime, String employeeId, String supplierId, int tiicketOpenCd, int customsTypeCd, String sku, int deliveryTypeCd, String keywords, String skuName) throws ParseException;

    /**
     * 条件查询采购单相关信息(不包含条数信息)
     *
     * @return
     */
    List<OrderTracingDetailEnity> findTracingOrderListByCond(int pageNum, int pageSize, String orderId, String startTime, String endTime, String employeeId, String supplierId, int tiicketOpenCd, int customsTypeCd, String sku, int deliveryTypeCd, String keywords, String skuName) throws ParseException;

    List<OrderTracingEntryVo> getEntry(String orderId, String skuId);

    List<OrderTracingReturnVo> getReturn(String orderId, String skuId);

    List<OrderTracingCompleteVo> getStore(String orderId, String skuId);
}
