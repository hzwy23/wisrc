package com.wisrc.merchandise.service.impl;

import com.wisrc.merchandise.dao.MskuDeliveryModeAttrDao;
import com.wisrc.merchandise.entity.MskuDeliveryModeAttrEntity;
import com.wisrc.merchandise.service.MskuDeliveryModeAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MskuDeliveryModeAttrServiceImpl implements MskuDeliveryModeAttrService {
    @Autowired
    private MskuDeliveryModeAttrDao mskuDeliveryModeAttrDao;

    @Override
    public List<Map<String, Object>> getMskuDeliveryModeAttrList() throws Exception {
        List<Map<String, Object>> deliveryModeList = new ArrayList<>();

        List<MskuDeliveryModeAttrEntity> deliveryModes = mskuDeliveryModeAttrDao.getMskuDeliveryModeAttr();
        for (MskuDeliveryModeAttrEntity deliveryMode : deliveryModes) {
            Map<String, Object> delivery = new HashMap<>();
            delivery.put("deliveryMode", deliveryMode.getDeliveryMode());
            delivery.put("deliveryModeDesc", deliveryMode.getDeliveryModeDesc());
            deliveryModeList.add(delivery);
        }

        return deliveryModeList;
    }

    @Override
    public Map<Integer, Object> getMskuDeliveryModeAttrMap() throws Exception {
        Map<Integer, Object> deliveryModeMap = new HashMap<>();

        List<MskuDeliveryModeAttrEntity> deliveryModes = mskuDeliveryModeAttrDao.getMskuDeliveryModeAttr();
        for (MskuDeliveryModeAttrEntity getDeliveryMode : deliveryModes) {
            Map deliveryMode = new HashMap();
            deliveryMode.put("deliveryMode", getDeliveryMode.getDeliveryMode());
            deliveryMode.put("deliveryModeDesc", getDeliveryMode.getDeliveryModeDesc());
            deliveryModeMap.put(getDeliveryMode.getDeliveryMode(), deliveryMode);
        }

        return deliveryModeMap;
    }

    @Override
    public List<Map<String, Object>> mapToList(Map<Object, Map> deliveryModeMap) {
        List<Map<String, Object>> deliveryModeList = new ArrayList<>();

        for (Object id : deliveryModeMap.keySet()) {
            Map deliveryMode = deliveryModeMap.get(id);
            deliveryModeList.add(deliveryMode);
        }

        return deliveryModeList;
    }

    @Override
    public Map keyToValue() throws Exception {
        Map<Integer, String> deliveryModeMap = new HashMap<>();

        List<MskuDeliveryModeAttrEntity> deliveryModes = mskuDeliveryModeAttrDao.getMskuDeliveryModeAttr();
        for (MskuDeliveryModeAttrEntity deliveryMode : deliveryModes) {
            deliveryModeMap.put(deliveryMode.getDeliveryMode(), deliveryMode.getDeliveryModeDesc());
        }

        return deliveryModeMap;
    }

    @Override
    public Map valueToKey() throws Exception {
        Map<String, Integer> deliveryModeMap = new HashMap<>();

        List<MskuDeliveryModeAttrEntity> deliveryModes = mskuDeliveryModeAttrDao.getMskuDeliveryModeAttr();

        for (MskuDeliveryModeAttrEntity deliveryMode : deliveryModes) {
            deliveryModeMap.put(deliveryMode.getDeliveryModeDesc(), deliveryMode.getDeliveryMode());
        }

        return deliveryModeMap;
    }

    @Override
    public Map ListToRelation(List<Map> list) {
        Map backMap = new HashMap();
        for (Map deliveryMode : list) {
            backMap.put(deliveryMode.get("deliveryMode"), deliveryMode.get("deliveryModeDesc"));
        }

        return backMap;
    }
}
