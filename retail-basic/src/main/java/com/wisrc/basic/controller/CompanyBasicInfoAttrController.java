package com.wisrc.basic.controller;

import com.wisrc.basic.entity.*;
import com.wisrc.basic.service.CompanyBasicInfoAttrService;
import com.wisrc.basic.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "公司档案相关码表信息接口")
@RequestMapping(value = "/basic")
public class CompanyBasicInfoAttrController {
    @Autowired
    CompanyBasicInfoAttrService companyBasicInfoAttrService;

    @RequestMapping(value = "/delivery/port/attr", method = RequestMethod.GET)
    @ApiOperation(value = "出口口岸码表信息", notes = "出口口岸ID(空运默认0--全选，海运默认1--深圳)", response = DeliveryPortAttrEntity.class)
    @ResponseBody
    public Result findDeliveryPortAttr() {
        return Result.success(companyBasicInfoAttrService.findDeliveryPortAttr());
    }

    @RequestMapping(value = "/exempting/nature/attr", method = RequestMethod.GET)
    @ApiOperation(value = "征免性质码表信息", notes = "征免性质ID(0--全选，1--一般征税（默认）)", response = ExemptingNatureAttrEntity.class)
    @ResponseBody
    public Result findExemptingNatureAttr() {
        return Result.success(companyBasicInfoAttrService.findExemptingNatureAttr());
    }

    @RequestMapping(value = "/exemption/mode/attr", method = RequestMethod.GET)
    @ApiOperation(value = "征免模式码表信息", notes = "征免模式ID(0--全选,1--照章征税（默认）,2--折半征税,3--全免)", response = ExemptionModeAttrEntity.class)
    @ResponseBody
    public Result findExemptionModeAttr() {
        return Result.success(companyBasicInfoAttrService.findExemptionModeAttr());
    }

    @RequestMapping(value = "/packing/type/attr", method = RequestMethod.GET)
    @ApiOperation(value = "包装类型码表信息", notes = "包装类型ID(0--全选，1--纸箱（默认），2--其他包装)", response = PackingTypeAttrEntity.class)
    @ResponseBody
    public Result findPackingTypeAttr() {
        return Result.success(companyBasicInfoAttrService.findPackingTypeAttr());
    }

    @RequestMapping(value = "/trade/mode/attr", method = RequestMethod.GET)
    @ApiOperation(value = "贸易方式码表信息", notes = "贸易方式ID(0--全选，1--一般贸易（默认）)", response = TradeModeAttrEntity.class)
    @ResponseBody
    public Result findTradeModeAttr() {
        return Result.success(companyBasicInfoAttrService.findTradeModeAttr());
    }

    @RequestMapping(value = "/transaction/mode/attr", method = RequestMethod.GET)
    @ApiOperation(value = "交易模式码表信息", notes = "交易模式ID(0--全选，1--FOB(默认),2--CIF,3--C&F,4--其他))", response = TransactionModeAttrEntity.class)
    @ResponseBody
    public Result findTransactionModeAttr() {
        return Result.success(companyBasicInfoAttrService.findTransactionModeAttr());
    }

    @RequestMapping(value = "/monetary/system/attr", method = RequestMethod.GET)
    @ApiOperation(value = "货币制度码表信息", notes = "货币制度ID(0--全选，1--USD（默认），2--HKD)", response = MonetarySystemAttrEntity.class)
    @ResponseBody
    public Result findMonetarySystemAttr() {
        return Result.success(companyBasicInfoAttrService.findMonetarySystemAttr());
    }


}
