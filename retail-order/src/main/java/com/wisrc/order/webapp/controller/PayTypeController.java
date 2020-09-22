package com.wisrc.order.webapp.controller;

import com.wisrc.order.webapp.entity.PayTypeEnity;
import com.wisrc.order.webapp.service.PayTypeService;
import com.wisrc.order.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "订单支付方式码表服务")
@RestController
@RequestMapping(value = "/order")
public class PayTypeController {
    @Autowired
    private PayTypeService payTypeService;


    @ApiOperation(value = "获取所有支付方式", notes = "获取所有支付方式")
    @RequestMapping(value = "/order/payTypeList", method = RequestMethod.GET)
    public Result getPayTypes() {
        List<PayTypeEnity> listPayTypes = payTypeService.getAllPayType();
        return Result.success(listPayTypes);
    }
}
