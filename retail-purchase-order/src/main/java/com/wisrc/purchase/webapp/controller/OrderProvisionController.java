package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.entity.DynamicFieldsAttrEntity;
import com.wisrc.purchase.webapp.entity.ProvisionMouldEntity;
import com.wisrc.purchase.webapp.service.OrderProvisionService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderProvisionMouldVO;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderProvisionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "采购订单条款")
@RestController
@RequestMapping(value = "/purchase")
public class OrderProvisionController {
    @Autowired
    OrderProvisionService orderProvisionService;

    @RequestMapping(value = "/order/provision/mould", method = RequestMethod.POST)
    @ApiOperation(value = "新增/修改订单条款模板", notes = "新增/修改订单条款模板", response = OrderProvisionMouldVO.class)
    public Result addProvisionMould(@RequestBody OrderProvisionMouldVO vo, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (vo.getUuid() != null && !"".equals(vo.getUuid())) {
            try {
                orderProvisionService.updateProvisionMould(vo, userId);
                return Result.success(200, "修改订单条款模板成功", "");
            } catch (Exception e) {
                return Result.success(390, "修改订单条款模板失败", "");
            }
        }
        try {
            orderProvisionService.addProvisionMould(vo, userId);
            return Result.success(200, "新增订单条款模板成功", "");
        } catch (Exception e) {
            return Result.success(390, "新增订单条款模板失败", "");
        }
    }

    @RequestMapping(value = "/order/provision/mould", method = RequestMethod.GET)
    @ApiOperation(value = "查询订单条款模板", notes = "查询订单条款模板", response = ProvisionMouldEntity.class)
    @ResponseBody
    public Result findProvisionMould() {
        return Result.success(orderProvisionService.findProvisionMould());
    }

    @RequestMapping(value = "/order/provision/mould/id", method = RequestMethod.GET)
    @ApiOperation(value = "查询订单条款模板信息ById", notes = "查询订单条款模板信息ById", response = OrderProvisionMouldVO.class)
    @ResponseBody
    public Result findProvisionMouldById(@RequestParam("uuid") String uuid) {
        return Result.success(orderProvisionService.findProvisionMouldById(uuid));
    }

    @RequestMapping(value = "/order/provision/mould", method = RequestMethod.PUT)
    @ApiOperation(value = "删除条款模板", notes = "删除条款模板")
    @ResponseBody
    public Result deleteInfoByList(@RequestParam("uuid") String uuid, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            ProvisionMouldEntity ele = new ProvisionMouldEntity();
            ele.setModifyUser(userId);
            ele.setDeleteStatus(1);
            ele.setUuid(uuid);
            ele.setModifyTime(Time.getCurrentTimestamp());
            orderProvisionService.delProvisionMould(ele);
            return Result.success("删除条款模板成功");
        } catch (Exception e) {
            return Result.success(390, "删除条款模板异常", "");
        }
    }

    @RequestMapping(value = "/order/provision/id", method = RequestMethod.GET)
    @ApiOperation(value = "查询订单条款信息ById", notes = "查询订单条款信息ById", response = OrderProvisionVO.class)
    @ResponseBody
    public Result findProvisionById(@RequestParam("orderId") String orderId) {
        return Result.success(orderProvisionService.findOrderProvision(orderId));
    }

    @RequestMapping(value = "/order/provision/", method = RequestMethod.POST)
    @ApiOperation(value = "新增/修改订单条款", notes = "新增/修改订单条款", response = OrderProvisionVO.class)
    public Result addProvisionMould(@RequestBody OrderProvisionVO vo, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (vo.getUuid() != null && vo.getUuid() != "") {
            try {
                orderProvisionService.updateOrderProvision(vo);
                return Result.success(200, "修改订单条款成功", "");
            } catch (Exception e) {
                return Result.success(390, "修改订单条款失败", "");
            }
        }
        try {
            orderProvisionService.addOrderProvision(vo);
            return Result.success(200, "新增订单条款成功", "");
        } catch (Exception e) {
            return Result.success(390, "新增订单条款失败", "");
        }
    }


    @RequestMapping(value = "/order/dynamic/attr", method = RequestMethod.GET)
    @ApiOperation(value = "在光标位置插入字段码表", notes = "在光标位置插入字段码表<br/>" +
            "1--供应商收款人<br/>" +
            "2--供应商开户银行<br/>" +
            "3--供应商银行账户", response = DynamicFieldsAttrEntity.class)
    @ResponseBody
    public Result dynamicAttr() {
        return Result.success(orderProvisionService.findDynamicFieldsAttr());
    }

}
