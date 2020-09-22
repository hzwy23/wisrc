package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.ReturnWarehouseApplyEnity;
import com.wisrc.shipment.webapp.entity.PackingDetailEnity;
import com.wisrc.shipment.webapp.entity.ReceiveWarehouseEnity;
import com.wisrc.shipment.webapp.entity.RemoveOrderEnity;
import com.wisrc.shipment.webapp.entity.ReturnWarehouseCheckEnity;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.vo.ProductDetaiVo;
import com.wisrc.shipment.webapp.vo.ReturnWareHouseEnityVo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ReturnWarehouseApplyService {

    Result insert(ReturnWarehouseApplyEnity returnWarehouseApplyEnity);

    LinkedHashMap findByCond(String pageNum, String pageSize, String shopId, String employeeId, int statusCd, String keyword, String productName, String startTime, String endTime);

    ReturnWareHouseEnityVo getWarehouseDetail(String returnApplyId);

    ReturnWareHouseEnityVo getWarehouseSimpleDetail(String returnApplyId);

    void deleteApply(String returnApplyId, String reason, String userId);

    void addRemoveOrder(String returnApplyId, String[] orderIds);

    void addReceiveWarehouse(ReceiveWarehouseEnity receiveWarehouseEnity);

    void checkReturnWarehouseApply(ReturnWarehouseCheckEnity returnWarehouseApplyEnity);

    ProductDetaiVo getProdetail(String shopId, String mskuId);

    List<PackingDetailEnity> getPackingDetailList(String[] skuIds);

    List<String> getRemoveOrderIdList(String returnApplyId);

    List<RemoveOrderEnity> getRemoveOrderEnity();

    List<Map> getAllRemoveOrderDetail(String returnApplyId);
}
