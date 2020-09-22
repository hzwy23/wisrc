package com.wisrc.order.webapp.controller;

import com.wisrc.order.webapp.entity.PayStatusEnity;
import com.wisrc.order.webapp.service.PayStatusService;
import com.wisrc.order.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "订单支付状态服务")
@RestController
@RequestMapping(value = "/order")
public class PayStatusController {
    @Autowired
    private PayStatusService payStatusService;


    @ApiOperation(value = "获取所有订单支付状态", notes = "获取所有支付方式")
    @RequestMapping(value = "/order/payStatusList", method = RequestMethod.GET)
    public Result getPayStatus() {
        List<PayStatusEnity> listPayStatus = payStatusService.getAllPayStatus();
        return Result.success(listPayStatus);
    }

}
