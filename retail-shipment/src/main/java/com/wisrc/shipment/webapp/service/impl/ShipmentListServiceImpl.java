package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.ShipMentService;
import com.wisrc.shipment.webapp.service.ShipmentListService;
import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShipmentListServiceImpl implements ShipmentListService {
    @Autowired
    private ShipMentService shipMentService;

    @Override
    public Map getShipmentListService(List<String> idlist) throws Exception {

        Map shipmentMap = new HashMap();

        String shipmentIds = "";
        for (int m = 0; m < idlist.size(); m++) {
            if (m == 0) {
                shipmentIds += idlist.get(0);
            } else {
                shipmentIds += ("," + idlist.get(m));
            }
        }
        Result shipment = shipMentService.getProducts(shipmentIds);
        if (shipment.getCode() != 200) {
            throw new Exception("物流商接口发生错误");
        }
        List<Map> shipmentList = (List) shipment.getData();
        for (Map map : shipmentList) {
            shipmentMap.put(map.get("shipmentId"), map.get("shipmentName"));
        }
        return shipmentMap;
    }

}
