package com.wisrc.order.webapp.controller;

import com.wisrc.order.webapp.entity.ReSendReasonEnity;
import com.wisrc.order.webapp.service.ReSendReasonService;
import com.wisrc.order.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@Api(tags = "重发原因管理")
@RequestMapping(value = "/order")
public class ReSendReasonController {
    @Autowired
    private ReSendReasonService reSendReasonService;


    @ApiOperation(value = "新增重发原因", notes = "已发货订单批量重发时需要选择重发原因")
    @RequestMapping(value = "/reSendReason", method = RequestMethod.POST)
    public Result addReason(@RequestBody ReSendReasonEnity reasonEnity,
                            @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        reSendReasonService.addReason(reasonEnity);
        return Result.success("新增成功");
    }

    @ApiOperation(value = "获取所有重发原因", notes = "获取所有重发原因")
    @RequestMapping(value = "/ReSendOrder/list", method = RequestMethod.GET)
    public Result getReasons(String[] orderIDs) {
        List<ReSendReasonEnity> listReaseon = reSendReasonService.getAllReason();
        return Result.success(listReaseon);
    }


    @ApiOperation(value = "删除重发原因", notes = "已发货订单批量重发时需要选择重发原因")
    @RequestMapping(value = "/reSendReason/{reasonId}", method = RequestMethod.DELETE)
    public Result deleteReason(@PathVariable(value = "reasonId") String reasonId,
                               @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        reSendReasonService.deleteReason(reasonId);
        return Result.success("删除成功");
    }

}
