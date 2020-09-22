package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.service.ReplenishmentShipmentListService;
import com.wisrc.replenishment.webapp.service.ShipmentService;
import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReplenishmentShipmentListServiceImpl implements ReplenishmentShipmentListService {
    @Autowired
    ShipmentService shipmentService;

    @Override
    public Map getShipmentList(List<String> idlist) throws Exception {
        Map shipmentMap = new HashMap();

        String shipmentIds = "";
        for (int m = 0; m < idlist.size(); m++) {
            if (m == 0) {
                shipmentIds += idlist.get(0);
            } else {
                shipmentIds += ("," + idlist.get(m));
            }
        }
        Result warehouseResult = shipmentService.getShipmentList(shipmentIds);
        if (warehouseResult.getCode() != 200) {
            throw new Exception("物流商报价接口发生错误");
        }
        List<Map> shipmentList = (List) warehouseResult.getData();
        for (Map shipment : shipmentList) {
            Map map = new HashMap();
            map.put("channelName", shipment.get("channelName"));
            map.put("shipmentId", shipment.get("shipmentId"));
            map.put("shipmentName", shipment.get("shipMentName"));
            map.put("pickupTypeCd", shipment.get("pickupTypeCd"));
            map.put("pickupTypeName", shipment.get("pickupTypeName"));
            //获取物流时效信息   数据拼接
            List<Map> effectiveList = (List<Map>) shipment.get("effectiveList");
            if (effectiveList.size() > 0) {
                List<String> effList = new ArrayList<>();
                for (Map m : effectiveList) {
                    effList.add(m.get("startDays") + "-" + m.get("endDays"));
                }
                map.put("effList", effList);
            }
            shipmentMap.put(shipment.get("offerId"), map);
        }
        return shipmentMap;
    }
}
