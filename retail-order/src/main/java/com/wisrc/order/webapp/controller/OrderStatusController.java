package com.wisrc.order.webapp.controller;

import com.wisrc.order.webapp.entity.OrderStatusAttr;
import com.wisrc.order.webapp.service.OrderStatusService;
import com.wisrc.order.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "订单状态码表服务")
@RestController
@RequestMapping(value = "/order")
public class OrderStatusController {
    @Autowired
    private OrderStatusService orderStatusService;


    @ApiOperation(value = "获取所有订单状态", notes = "获取所有订单状态")
    @RequestMapping(value = "/order/orderStatus", method = RequestMethod.GET)
    public Result getPayTypes() {
        List<OrderStatusAttr> orderStatusAttrs = orderStatusService.getAllStatus();
        return Result.success(orderStatusAttrs);
    }
}
