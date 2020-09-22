package com.wisrc.shipment.webapp.service;

import java.util.List;
import java.util.Map;

public interface ShipmentListService {

    /**
     * 通过数组查询仓库名称
     *
     * @param idlist
     * @return
     */
    Map getShipmentListService(List<String> idlist) throws Exception;
}