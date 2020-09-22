package com.wisrc.replenishment.webapp.service;

import java.util.List;
import java.util.Map;


public interface WarehouseListService {
    /**
     * 通过数组查询仓库名称
     *
     * @param idlist
     * @return
     */
    Map getWarehouseNameList(List<String> idlist) throws Exception;
}
