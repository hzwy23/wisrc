package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.AddEnterWarehouseBillVO;

import java.util.LinkedHashMap;

public interface HandmadeEnterWarehouseBillService {
    Result add(AddEnterWarehouseBillVO vo, String userId);

    LinkedHashMap getList(int num, int size, String warehouseId, Integer enterTypeCd, Integer status, String startTime, String endTime, String keyword);

    LinkedHashMap getList(String enterBillId, String warehouseId, int enterTypeCd, String skuId, String skuName, String startTime, String endTime);

    Result getDetail(String enterBillId);

    void addRemark(String enterBillId, String remark, String userId);

    /**
     * 取消手工入库单
     *
     * @param enterBillId
     */
    void cancel(String enterBillId, String cancelReason);

    /**
     * 获取所有状态
     *
     * @return
     */
    Result getAllStatus();
}
