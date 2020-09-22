package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.entity.LogisticOfferEnity;
import com.wisrc.replenishment.webapp.entity.ShipmentEnity;
import com.wisrc.replenishment.webapp.entity.ShipmentInfoEnity;
import com.wisrc.replenishment.webapp.utils.Result;

import java.util.List;
import java.util.Map;

public interface ExternalService {

    List<LogisticOfferEnity> getAllLogisticOffer();

    void updateWaybill(List<Map> tracingRecordMapList);

    List<ShipmentEnity> getShipmentEnity();

    void batchUpdateSignNum(List<ShipmentInfoEnity> shipmentInfoEnityList);

    Result getWaybillTransferInfo(String waybillId);

    Result updateStatus(String[] waybillIds);

    List<Map> getShipment();
}
