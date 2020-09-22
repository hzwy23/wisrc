package com.wisrc.order.webapp.controller;

import com.wisrc.order.webapp.entity.OrderCommodityInfoEntity;
import com.wisrc.order.webapp.entity.OrderCustomerInfoEntity;
import com.wisrc.order.webapp.entity.OrderLogisticsInfo;
import com.wisrc.order.webapp.entity.OrderRemarkInfoEntity;
import com.wisrc.order.webapp.service.ExceptOrderService;
import com.wisrc.order.webapp.service.OrderRemarkInfoService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.utils.Time;
import com.wisrc.order.webapp.utils.UUIDutil;
import com.wisrc.order.webapp.vo.ExceptOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;
import java.util.List;

@Api(tags = "异常订单信息")
@RestController
@RequestMapping(value = "/order")
public class ExceptOrderController {
    @Autowired
    private ExceptOrderService exceptOrderService;
    @Autowired
    private OrderRemarkInfoService orderRemarkInfoService;


    @ApiOperation(value = "查询异常订单信息", notes = "查询所有的异常订单信息", response = ExceptOrderVO.class)
    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public Result getExceptOrderList(@RequestParam(value = "pageNum", required = false) String pageNum,
                                     @RequestParam(value = "pageSize", required = false) String pageSize,
                                     @RequestParam(value = "orderId", required = false) String orderId,
                                     @RequestParam(value = "originalOrderId", required = false) String originalOrderId,
                                     @RequestParam(value = "platId", required = false) String platId,
                                     @RequestParam(value = "shopId", required = false) String shopId,
                                     @RequestParam(value = "commodityId", required = false) String commodityId,
                                     @RequestParam(value = "commodityName", required = false) String commodityName,
                                     @RequestParam(value = "createStartTime", required = false) String createStartTime,
                                     @RequestParam(value = "createEndTime", required = false) String createEndTime,
                                     @RequestParam(value = "label", required = false) String label) {
        LinkedHashMap list = new LinkedHashMap();
        try {
            if (pageNum != null && pageSize != null) {
                int num = Integer.parseInt(pageNum);
                int size = Integer.parseInt(pageSize);
                list = exceptOrderService.getExceptOrderByCond(num, size, orderId, originalOrderId, platId, shopId, commodityId, commodityName, createStartTime, createEndTime, label);
            } else {
                list = exceptOrderService.getAllOrder(orderId, originalOrderId, platId, shopId, commodityId, commodityName, createStartTime, createEndTime, label);
            }
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "请联系后台人员");
        }

    }

    @ApiOperation(value = "修改订单客户信息", notes = "根据orderId修改订单客户信息")
    @RequestMapping(value = "/order/custom", method = RequestMethod.PUT)
    public Result updateOrderCustom(@RequestBody OrderCustomerInfoEntity entity, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            exceptOrderService.update(entity, userId);
            return Result.success(entity);
        } catch (Exception e) {
            return Result.failure(500, e.getMessage(), entity);
        }
    }

    @ApiOperation(value = "修改订单物流信息", notes = "根据orderId修改订单物流信息")
    @RequestMapping(value = "/order/logistics", method = RequestMethod.PUT)
    public Result updateOrderLogistics(@RequestBody OrderLogisticsInfo entity, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            exceptOrderService.update(entity, userId);
            return Result.success(entity);
        } catch (Exception e) {
            return Result.failure(500, e.getMessage(), entity);
        }
    }

    @ApiOperation(value = "修改订单商品信息", notes = "根据orderId修改订单商品信息")
    @RequestMapping(value = "/order/commodity", method = RequestMethod.PUT)
    public Result updateOrderLogistics(@RequestBody List<OrderCommodityInfoEntity> voList, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            exceptOrderService.update(voList, userId);
            return Result.success(voList);
        } catch (Exception e) {
            return Result.failure(500, e.getMessage(), voList);
        }
    }

    @RequestMapping(value = "/order/status", method = RequestMethod.PUT)
    @ApiOperation(value = "根据订单号和状态编码修改异常订单状态为已发货或作废", notes = "statusCd：4--已发货，6--已作废")
    public Result changeStatus(@RequestParam("orderId") String orderId, @RequestParam("statusCd") int statusCd, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            exceptOrderService.changeStatus(orderId, statusCd, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/order/remark", method = RequestMethod.POST)
    public Result addRemark(@RequestParam("orderId") String orderId, @RequestParam("remark") String remark, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        OrderRemarkInfoEntity entity = new OrderRemarkInfoEntity();
        entity.setOrderId(orderId);
        entity.setUuid(UUIDutil.randomUUID());
        entity.setRemark(remark);
        entity.setCreateUser(userId);
        entity.setCreateTime(Time.getCurrentDateTime());
        try {
            orderRemarkInfoService.addRemark(entity);
            return Result.success(entity);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), entity);
        }
    }


}
