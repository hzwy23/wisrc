package com.wisrc.quality.webapp.service.impl;

import com.wisrc.quality.webapp.service.PurchaseService;
import com.wisrc.quality.webapp.service.externalService.ExternalPurchaseService;
import com.wisrc.quality.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private ExternalPurchaseService externalPurchaseService;

    @Override
    public Map getQuality(String orderId, List skuIds) throws Exception {
        Map result = new HashMap();

        Result qualityResult = externalPurchaseService.getQuality(orderId, skuIds);
        if (qualityResult.getCode() != 200) {
            throw new Exception(qualityResult.getMsg());
        }

        List<Map> qualityList = (List) qualityResult.getData();
        for (Map qualityMap : qualityList) {
            Map quality = new HashMap();
            quality.put("quantity", qualityMap.get("quantity"));
            quality.put("spareRate", qualityMap.get("spareRate"));

            if (qualityMap.get("skuId") != null) {
                result.put(qualityMap.get("skuId"), quality);
            }
        }

        return result;
    }
}
