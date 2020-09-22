package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.entity.CustomsAttrEntity;
import com.wisrc.replenishment.webapp.entity.ExceptionTypeAttrEntity;
import com.wisrc.replenishment.webapp.entity.LogisticsTypeAttrEntity;
import com.wisrc.replenishment.webapp.entity.WeightTypeAttrEntity;
import com.wisrc.replenishment.webapp.service.WaybillInfoAttrService;
import com.wisrc.replenishment.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "物流跟踪单相关码表")
@RequestMapping(value = "/replenishment")
public class WaybillInfoAttrController {
    @Autowired
    WaybillInfoAttrService waybillInfoAttrService;

    @RequestMapping(value = "/waybill/customs/attr", method = RequestMethod.GET)
    @ApiOperation(value = "报关类型", notes = "查询报关类型<br/>" +
            "1--需要报关<br/>" +
            "2--不需要报关", response = CustomsAttrEntity.class)
    @ResponseBody
    public Result cusomsAttr() {
        return Result.success(waybillInfoAttrService.findCustomsAttr());
    }

    @RequestMapping(value = "/waybill/exception/attr", method = RequestMethod.GET)
    @ApiOperation(value = "运单异常状态", notes = "运单异常状态<br/>" +
            "1--国内海关查验<br/>" +
            "2--美国海关查验<br/>" +
            "3--航班因天气原因或飞机故障延误<br/>" +
            "4--排仓<br/>" +
            "5--货代漏发件<br/>", response = ExceptionTypeAttrEntity.class)
    @ResponseBody
    public Result exceptionAttr() {
        return Result.success(waybillInfoAttrService.findExceptionAttr());
    }

    @RequestMapping(value = "/waybill/logistics/attr", method = RequestMethod.GET)
    @ApiOperation(value = "物流状态", notes = "物流状态<br/>" +
            "1--待发货<br/>" +
            "2--待签收<br/>" +
            "3--已签收<br/>" +
            "4--已取消", response = LogisticsTypeAttrEntity.class)
    @ResponseBody
    public Result logisticsAttr() {
        return Result.success(waybillInfoAttrService.findLogisticsAttr());
    }

    @RequestMapping(value = "/waybill/weight/attr", method = RequestMethod.GET)
    @ApiOperation(value = "重量类型", notes = "重量类型<br/>" +
            "1--抛重<br/>" +
            "2--实重", response = WeightTypeAttrEntity.class)
    @ResponseBody
    public Result weightAttr() {
        return Result.success(waybillInfoAttrService.findWeightAttr());
    }


    @RequestMapping(value = "/waybill/msku/unit/attr", method = RequestMethod.GET)
    @ApiOperation(value = "报关资料单位单位码表", notes = "单位<br/>" +
            "1--个<br/>" +
            "2--只<br/>" +
            "3--副<br/>" +
            "4--张<br/>")
    @ResponseBody
    public Result unitAttr(@RequestParam(value = "unitCd", required = false) Integer unitCd) {
        return Result.success(waybillInfoAttrService.findUnitAttr(unitCd));
    }
}
