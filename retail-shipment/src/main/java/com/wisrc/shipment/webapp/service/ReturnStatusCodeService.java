package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.ReturnWarehouseStatusEnity;

import java.util.List;

public interface ReturnStatusCodeService {
    List<ReturnWarehouseStatusEnity> getAllReturnStatus();
}
