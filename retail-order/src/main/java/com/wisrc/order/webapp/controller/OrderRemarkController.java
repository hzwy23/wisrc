package com.wisrc.order.webapp.controller;


import com.wisrc.order.webapp.entity.OrderRemarkInfoEntity;
import com.wisrc.order.webapp.service.OrderRemarkService;
import com.wisrc.order.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags = "备注新增管理")
@RequestMapping(value = "/order")
public class OrderRemarkController {
    @Autowired
    private OrderRemarkService orderRemarkService;

    @ApiOperation(value = "备注新增", notes = "已发货订单批量重发时需要选择重发原因")
    @RequestMapping(value = "/reMark", method = RequestMethod.POST)
    public Result addReason(@RequestBody OrderRemarkInfoEntity orderRemarkInfoEntity,
                            @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        orderRemarkInfoEntity.setCreateUser(userId);
        try {
            orderRemarkService.add(orderRemarkInfoEntity);
        } catch (Exception e) {
            return Result.failure(390, "新增失败", e.getMessage());
        }
        return Result.success("新增成功");
    }
}
