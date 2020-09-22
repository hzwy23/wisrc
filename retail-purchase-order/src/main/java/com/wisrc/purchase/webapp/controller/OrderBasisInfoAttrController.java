package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.entity.CustomsTypeAtrEntity;
import com.wisrc.purchase.webapp.entity.DeliveryTypeAttrEntity;
import com.wisrc.purchase.webapp.entity.TiicketOpenAttrEntity;
import com.wisrc.purchase.webapp.service.OrderBasisInfoAttrService;
import com.wisrc.purchase.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "采购订单相关码表")
@RequestMapping(value = "/purchase")
public class OrderBasisInfoAttrController {
    @Autowired
    OrderBasisInfoAttrService orderBasisInfoAttrService;

    @RequestMapping(value = "/order/cusoms/attr", method = RequestMethod.GET)
    @ApiOperation(value = "报关类型", notes = "查询报关类型<br/>" +
            "1--我司报关<br/>" +
            "2--工厂报关<br/>" +
            "3--不报关", response = CustomsTypeAtrEntity.class)
    @ResponseBody
    public Result cusomsAttr() {
        return Result.success(orderBasisInfoAttrService.cusomsAttr());
    }

    @RequestMapping(value = "/order/delivery/attr", method = RequestMethod.GET)
    @ApiOperation(value = "交货状态", notes = "查询交货状态<br/>" +
            "1--待交货<br/>" +
            "2--部分交货<br/>" +
            "3--完成", response = DeliveryTypeAttrEntity.class)
    @ResponseBody
    public Result deliveryAttr() {
        return Result.success(orderBasisInfoAttrService.deliveryAttr());
    }

    @RequestMapping(value = "/order/tiicket/attr", method = RequestMethod.GET)
    @ApiOperation(value = "是否开票", notes = "查询是否开票<br/>" +
            "1--不开票<br/>" +
            "2--开票", response = TiicketOpenAttrEntity.class)
    @ResponseBody
    public Result tiicketAttr() {
        return Result.success(orderBasisInfoAttrService.tiicketAttr());
    }
}
