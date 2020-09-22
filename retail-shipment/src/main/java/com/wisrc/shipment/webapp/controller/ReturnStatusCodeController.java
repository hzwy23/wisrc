package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.ReturnStatusCodeService;
import com.wisrc.shipment.webapp.entity.ReturnWarehouseStatusEnity;
import com.wisrc.shipment.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "退仓申请单状态码表查询")
@RestController
@RequestMapping(value = "/shipment")
public class ReturnStatusCodeController {
    @Autowired
    private ReturnStatusCodeService returnStatusCodeService;

    @ApiOperation(value = "获取所有退仓申请状态", notes = "获取所有支付方式")
    @RequestMapping(value = "/returnWarehouse/statusList", method = RequestMethod.GET)
    public Result getPayStatus() {
        try {
            List<ReturnWarehouseStatusEnity> returnWarehouseStatusEnityList = returnStatusCodeService.getAllReturnStatus();
            return Result.success(returnWarehouseStatusEnityList);
        } catch (Exception e) {
            return Result.failure(390, "获取所有退仓申请状态失败", e.getMessage());
        }
    }
}
