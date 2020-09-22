package com.wisrc.replenishment.webapp.service;

import java.util.List;
import java.util.Map;

public interface ReplenishmentShipmentListService {
    /**
     * 根据报价单ID集合查询物流报价详细内容列表
     *
     * @param idlist
     * @return
     */
    Map getShipmentList(List<String> idlist) throws Exception;
}
