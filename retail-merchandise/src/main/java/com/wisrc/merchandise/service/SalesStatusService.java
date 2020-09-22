package com.wisrc.merchandise.service;


import com.wisrc.merchandise.utils.Result;

import java.util.List;
import java.util.Map;

public interface SalesStatusService {
    List<Map<String, Object>> getSalesStatusList() throws Exception;

    Map<Integer, Object> getSalesStatusMap() throws Exception;

    List<Map<String, Object>> mapToList(Map<String, Map> salesStatusMap);

    Result getSelector();

    void editStatus();
}


