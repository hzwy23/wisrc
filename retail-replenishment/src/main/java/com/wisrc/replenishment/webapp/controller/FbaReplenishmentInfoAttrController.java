package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.entity.FbaPackUnitAttrEntity;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentPickupAttrEntity;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentStatusAttrEntity;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentPickupAttrService;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentStatusAttrService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.service.impl.FbaPackUnitAttrServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "补货单单相关码表")
@RequestMapping(value = "/replenishment")
public class FbaReplenishmentInfoAttrController {

    @Autowired
    private FbaReplenishmentPickupAttrService fbaReplenishmentPickupAttrService;
    @Autowired
    private FbaReplenishmentStatusAttrService fbaReplenishmentStatusAttrService;
    @Autowired
    private FbaPackUnitAttrServiceImpl fbaPackUnitAttrService;

    @ApiOperation(value = "提货类型", notes = "查询提货类型<br/>" +
            "1--物流派送<br/>" +
            "2--Amazon自提", response = FbaReplenishmentPickupAttrEntity.class)
    @RequestMapping(value = "/fba/pickup/attr", method = RequestMethod.GET)
    public Result pickupAttr() {
        return Result.success(fbaReplenishmentPickupAttrService.findAllPickupAttr());
    }

    @ApiOperation(value = "状态类型", notes = "查询状态<br/>" +
            "0--待同步Amazon<br/>" +
            "1--待装箱<br/>" +
            "2--待发货<br/>" +
            "3--待签收<br/>" +
            "4--已签收<br/>" +
            "5--已取消" +
            "6--待选择渠道", response = FbaReplenishmentStatusAttrEntity.class)
    @RequestMapping(value = "/fba/status/attr", method = RequestMethod.GET)
    public Result fbaStatusAttr() {
        return Result.success(fbaReplenishmentStatusAttrService.findAll());
    }

    @ApiOperation(value = "单位类型", notes = "查询单位类型<br/>" +
            "1--cm/kg<br/>" +
            "2--in/lb", response = FbaPackUnitAttrEntity.class)
    @RequestMapping(value = "/fba/unit/attr", method = RequestMethod.GET)
    public Result fbaPackUnitAttr() {
        return Result.success(fbaPackUnitAttrService.findAll());
    }
}
