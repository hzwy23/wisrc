package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.AddOutWarehouseBillVO;
import com.wisrc.warehouse.webapp.vo.OutWarehouseBillDetailVO;

import java.util.LinkedHashMap;

public interface HandmadeOutWarehouseBillService {
    Result add(AddOutWarehouseBillVO vo, String userId);

    LinkedHashMap getList(int num, int size, String outBillId, String warehouseId, int outTypeCd, String skuId, String skuName, String startTime, String endTime);

    LinkedHashMap getList(String outBillId, String warehouseId, int outTypeCd, String skuId, String skuName, String startTime, String endTime);

    OutWarehouseBillDetailVO getDetail(String outBillId);

    void addRemark(String outBillId, String remark);
}
