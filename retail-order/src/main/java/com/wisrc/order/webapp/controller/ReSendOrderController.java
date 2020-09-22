package com.wisrc.order.webapp.controller;

import com.wisrc.order.webapp.entity.ReSendOrderEnity;
import com.wisrc.order.webapp.service.ReSendOrderService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.vo.SaleOrderCommodityInfoVO;
import com.wisrc.order.webapp.vo.SendOrderInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@Api(tags = "订单重发模块")
@RequestMapping(value = "/order")
public class ReSendOrderController {
    @Autowired
    private ReSendOrderService sendOrderService;

    @ApiOperation(value = "需要进行订单重发的详情列表", notes = "批量重发时使用", response = SendOrderInfoVo.class)
    @RequestMapping(value = "/order/redelivery/{orderId}", method = RequestMethod.GET)
    public Result getOrders(@PathVariable("orderId") String orderId) {
        List<SaleOrderCommodityInfoVO> listOrder = null;
        try {
            listOrder = sendOrderService.getAllById(orderId);
        } catch (Exception e) {
            return Result.failure(390, "未知异常", e.getMessage());
        }
        return Result.success(listOrder);
    }

    @ApiOperation(value = "重新发货订单", notes = "重新发货")
    @RequestMapping(value = "/redelivery", method = RequestMethod.PUT)
    public Result updateOrder(@RequestBody @Validated ReSendOrderEnity reSendOrderEnity, BindingResult result,
                              @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            if (result.hasErrors()) {
                return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), null);
            }
            Result sendOrderResult = sendOrderService.reSendOrders(reSendOrderEnity, userId);
            if (sendOrderResult.getCode() != 200) {
                return sendOrderResult;
            }
        } catch (Exception e) {
            return Result.failure(390, "未知异常", e.getMessage());
        }
        return Result.success("重新发货成功");
    }
}
