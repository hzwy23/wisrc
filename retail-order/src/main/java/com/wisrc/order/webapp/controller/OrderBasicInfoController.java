package com.wisrc.order.webapp.controller;

import com.wisrc.order.webapp.entity.OrderBasicInfoEntity;
import com.wisrc.order.webapp.service.OrderBasisInfoService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.vo.ProductAndWareHouseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;

/**
 * 订单基础信息
 */
@Api(tags = "订单基础服务")
@RestController
@RequestMapping(value = "/order")
public class OrderBasicInfoController {
    @Autowired
    private OrderBasisInfoService orderBasisInfoService;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ApiOperation(value = "创建订单", notes = "手工创建订单）", response = OrderBasicInfoEntity.class)
    @ResponseBody
    public Result add(@RequestBody @Validated OrderBasicInfoEntity orderBasicInfoEntity, BindingResult result, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), null);
        }
        orderBasicInfoEntity.setCreateUser(userId);
        try {
            orderBasisInfoService.addOrder(orderBasicInfoEntity);
        } catch (Exception e) {
            return Result.failure(390, "创建失败", e.getMessage());
        }

        return Result.success("创建订单成功成功");
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取订单详情", notes = "订单详情）", response = OrderBasicInfoEntity.class)
    @ResponseBody
    public Result getOrderInfo(@PathVariable("orderId") String orderId, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            OrderBasicInfoEntity basicInfoEntity = orderBasisInfoService.getOrderInfo(orderId);
            return Result.success(basicInfoEntity);
        } catch (Exception e) {
            return Result.failure(390, "获取订单详情失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "订单作废", notes = "订单作废）", response = OrderBasicInfoEntity.class)
    @ResponseBody
    public Result delteOrder(@PathVariable("orderId") String orderId, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            Result result = orderBasisInfoService.deleteOrderById(orderId, userId);
            if (result.getCode() != 200) {
                return result;
            }
        } catch (Exception e) {
            return Result.failure(390, "订单作废失败", e.getMessage());
        }
        return Result.success("作废成功");
    }


    @RequestMapping(value = "/order/reActive", method = RequestMethod.PUT)
    @ApiOperation(value = "订单激活", notes = "订单激活）", response = OrderBasicInfoEntity.class)
    @ResponseBody
    public Result orderReActive(@RequestParam("orderId") String orderId, @RequestParam(value = "ifSend", defaultValue = "0") int ifSend, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            Result result = orderBasisInfoService.activeOrder(orderId, ifSend, userId);
            if (result.getCode() != 200) {
                return result;
            }
        } catch (Exception e) {
            return Result.failure(390, "订单激活失败", e.getMessage());
        }
        return Result.success("激活成功");
    }

    @RequestMapping(value = "/order/productDetailInfo/{shopId}/{mskuId}", method = RequestMethod.PUT)
    @ApiOperation(value = "根据shopId和mskuId获取产品信息和库存信息", notes = "根据shopId和mskuId获取产品信息和库存信息）", response = OrderBasicInfoEntity.class)
    @ResponseBody
    public Result getProductAndWareHouse(@PathVariable("shopId") String shopId, @PathVariable("mskuId") String mskuId, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            ProductAndWareHouseVo productAndWareHouseVo = orderBasisInfoService.getProAndWareHouse(shopId, mskuId);
            return Result.success(productAndWareHouseVo);
        } catch (Exception e) {
            return Result.failure(390, "根据shopId和mskuId获取产品信息和库存信息失败", e.getMessage());
        }
    }


    @ApiOperation(value = "获取订单列表", notes = "根据所选条件获取订单", response = OrderBasicInfoEntity.class)
    @RequestMapping(value = "/order/list", method = RequestMethod.GET)
    public Result getOrder(@RequestParam(value = "pageNum", required = false) String pageNum,
                           @RequestParam(value = "pageSize", required = false) String pageSize,
                           @RequestParam(value = "orderId", required = false) String orderId,
                           @RequestParam(value = "originalOrderId", required = false) String originalOrderId,
                           @RequestParam(value = "platId", required = false) String platId,
                           @RequestParam(value = "shopId", required = false) String shopId,
                           @RequestParam(value = "createTime", required = false) String createTime,
                           @RequestParam(value = "exceptTypeCd", required = false) String exceptTypeCd,
                           @RequestParam(value = "mskuId", required = false) String mskuId,
                           @RequestParam(value = "mskuName", required = false) String mskuName,
                           @RequestParam(value = "countryCd", required = false) String countryCd,
                           @RequestParam(value = "statusCd", required = false, defaultValue = "0") int statusCd
    ) {
        try {
            LinkedHashMap map = orderBasisInfoService.getOrder(pageNum, pageSize, orderId, originalOrderId, platId, shopId, createTime, exceptTypeCd, mskuId, mskuName, statusCd, countryCd);
            return Result.success(map);
        } catch (Exception e) {
            return Result.failure(390, "获取订单列表失败", e.getMessage());
        }

    }


}
