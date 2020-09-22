package com.wisrc.merchandise.service;


import java.util.List;
import java.util.Map;

public interface MskuDeliveryModeAttrService {
    List<Map<String, Object>> getMskuDeliveryModeAttrList() throws Exception;

    Map<Integer, Object> getMskuDeliveryModeAttrMap() throws Exception;

    List<Map<String, Object>> mapToList(Map<Object, Map> MskuDeliveryModeAttrMap);

    Map keyToValue() throws Exception;

    Map valueToKey() throws Exception;

    Map ListToRelation(List<Map> list);
}


