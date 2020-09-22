package com.wisrc.merchandise.service.impl;

import com.wisrc.merchandise.dto.replenishment.VReplenishmentMskuDto;
import com.wisrc.merchandise.service.ReplenishmentOutService;
import com.wisrc.merchandise.service.ReplenishmentService;
import com.wisrc.merchandise.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReplenishmentServiceImpl implements ReplenishmentService {
    @Autowired
    private ReplenishmentOutService replenishmentOutService;

    @Override
    public Map<String, VReplenishmentMskuDto> getFBAUnderWay(List mskuIds) throws Exception {
        Map result = new HashMap();
        Result underWayResult = replenishmentOutService.getFBAUnderWay(mskuIds);
        if (underWayResult.getCode() != 200) {
            throw new Exception(underWayResult.getMsg());
        }

        List<Map> underWayData = (List) underWayResult.getData();
        for (Map underWayMap : underWayData) {
            VReplenishmentMskuDto replenishment = new VReplenishmentMskuDto();
            replenishment.setCommodityId((String) underWayMap.get("commodityId"));
            replenishment.setDeliveryNumberTotal((String) underWayMap.get("deliveryNumberTotal "));
            replenishment.setSignInQuantityTotal((String) underWayMap.get("signInQuantityTotal "));
            replenishment.setUnderWayQuantity((String) underWayMap.get("underWayQuantity "));

            result.put(replenishment.getCommodityId(), replenishment);
        }

        return result;
    }
}
