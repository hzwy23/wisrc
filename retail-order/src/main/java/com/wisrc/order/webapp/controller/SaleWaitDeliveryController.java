package com.wisrc.order.webapp.controller;

import com.wisrc.order.webapp.entity.OrderRemarkInfoEntity;
import com.wisrc.order.webapp.service.SaleWaitDeliveryService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.utils.Time;
import com.wisrc.order.webapp.utils.UUIDutil;
import com.wisrc.order.webapp.vo.SplitOrderInfoListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags = "转入待配货")
@RequestMapping(value = "/order")
public class SaleWaitDeliveryController {
    @Autowired
    SaleWaitDeliveryService saleWaitDeliveryService;

    @ApiOperation(value = "异常订单转入待配货")
    @RequestMapping(value = "/delivery/wait", method = RequestMethod.PUT)
    public Result delete(@RequestParam("orderId") String orderId,
                         @RequestBody OrderRemarkInfoEntity entity,
                         @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        try {
            entity.setUuid(UUIDutil.randomUUID());
            entity.setCreateTime(Time.getCurrentDateTime());
            entity.setCreateUser(userId);
            entity.setOrderId(orderId);
            saleWaitDeliveryService.updateWaitStatus(orderId, entity, userId);
            return Result.success(200, "订单转入待配货成功", "");
        } catch (Exception e) {
            return Result.success(500, e.getMessage(), "");
        }
    }

    @ApiOperation(value = "订单拆分", notes = "订单拆分，需要传入被拆分订单ID，订单内拆分出来的商品UUID和商品数量的集合", response = SplitOrderInfoListVO.class)
    @RequestMapping(value = "/delivery/split", method = RequestMethod.POST)
    public Result splitOrder(@RequestBody SplitOrderInfoListVO splitOrderInfoListVO, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        try {

            return saleWaitDeliveryService.splitOrder(splitOrderInfoListVO, userId);
        } catch (Exception e) {
            return Result.success(500, e.getMessage(), "订单拆分失败");
        }
    }
}
