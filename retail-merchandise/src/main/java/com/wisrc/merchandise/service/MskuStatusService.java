package com.wisrc.merchandise.service;


import java.util.List;
import java.util.Map;

public interface MskuStatusService {
    List<Map<String, Object>> getMskuStatusList();

    Map<Integer, Object> getMskuStatusMap();

    List<Map<String, Object>> mapToList(Map<String, Map> mskuStatusMap);
}


