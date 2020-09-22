package com.wisrc.order.webapp.service.impl;

import com.wisrc.order.webapp.service.BasicOutsideService;
import com.wisrc.order.webapp.service.BasicService;
import com.wisrc.order.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasicServiceImpl implements BasicService {
    @Autowired
    private BasicOutsideService basicOutsideService;

    @Override
    public Map getShipmentName(String shipmentId) throws Exception {
        Map result = new HashMap();
        Result shipNameResult;

        try {
            shipNameResult = basicOutsideService.getShipmentName(shipmentId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("basic模块接口出现错误");
        }

        if (shipNameResult.getCode() != 200) {
            throw new Exception("basic模块接口出现错误");
        }
        List<Map> shipNameData = (List) shipNameResult.getData();
        for (Map data : shipNameData) {
            result.put(data.get("shipmentId"), data.get("shipmentName"));
        }

        return result;
    }
}
