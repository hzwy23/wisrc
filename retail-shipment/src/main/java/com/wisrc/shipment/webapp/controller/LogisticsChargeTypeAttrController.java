package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.LogisticsChargeTypeAttrService;
import com.wisrc.shipment.webapp.entity.LogisticsChargeTypeAttrEntity;
import com.wisrc.shipment.webapp.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "根据渠道类型查询计费类型")
@RequestMapping(value = "/shipment")
public class LogisticsChargeTypeAttrController {
    @Autowired
    private LogisticsChargeTypeAttrService logisticsChargeTypeAttrService;

    @RequestMapping(value = "charge/list/{channelTypeCd}", method = RequestMethod.GET)
    public Result findList(@PathVariable("channelTypeCd") int channelTypeCd) {
        List<LogisticsChargeTypeAttrEntity> list = logisticsChargeTypeAttrService.findList(channelTypeCd);
        if (list.size() > 0) {
            return Result.success(list);
        } else {
            return Result.failure(404, "没有对应结果", list);
        }
    }
}
