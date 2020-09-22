package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.LogisticDestService;
import com.wisrc.shipment.webapp.entity.FbaDestinationEnity;
import com.wisrc.shipment.webapp.entity.LittlePacketDestEnity;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.vo.swagger.LogisticsOfferBasisInfoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "目的地管理")
@RequestMapping(value = "/shipment")
public class LogisticsDestinationController {
    @Autowired
    private LogisticDestService logisticDestService;


    @ApiOperation(value = "获取所有fba目的地", notes = "获取fba目的地。", response = LogisticsOfferBasisInfoModel.class)
    @RequestMapping(value = "logistics/FbaDestination/batch", method = RequestMethod.GET)
    public Result getFbaDest(@RequestParam(value = "countryCd", required = false, defaultValue = "US") String countryCd) {
        try {
            List<FbaDestinationEnity> list = logisticDestService.getAllDest(countryCd);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(390, "获取目的地异常", e.getMessage());
        }
    }

    @ApiOperation(value = "获取所有小包目的地", notes = "获取fba目的地。", response = LogisticsOfferBasisInfoModel.class)
    @RequestMapping(value = "logistics/LittlePacketDestination/batch", method = RequestMethod.GET)
    public Result getLittleDest() {
        try {
            List<LittlePacketDestEnity> list = logisticDestService.getAllLittleDest();
            Map<String, String> map = new HashMap<>();
            List resultList = new ArrayList();
            for (LittlePacketDestEnity enity : list) {
                map.put(enity.getCountryName(), enity.getDestinationCd());
            }
            for (String key : map.keySet()) {
                LittlePacketDestEnity littleDest = new LittlePacketDestEnity();
                littleDest.setDestinationCd(map.get(key));
                littleDest.setCountryName(key);
                resultList.add(littleDest);
            }
            return Result.success(resultList);
        } catch (Exception e) {
            return Result.failure(390, "获取目的地异常", e.getMessage());
        }
    }
}
