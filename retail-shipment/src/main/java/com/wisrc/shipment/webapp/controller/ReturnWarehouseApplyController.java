package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.entity.ReturnWarehouseApplyEnity;
import com.wisrc.shipment.webapp.service.ReturnWarehouseApplyService;
import com.wisrc.shipment.webapp.entity.PackingDetailEnity;
import com.wisrc.shipment.webapp.entity.ReceiveWarehouseEnity;
import com.wisrc.shipment.webapp.entity.ReturnWarehouseCheckEnity;
import com.wisrc.shipment.webapp.utils.DateUtil;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.vo.ChangeLableViewVo;
import com.wisrc.shipment.webapp.vo.ProductDetaiVo;
import com.wisrc.shipment.webapp.vo.ReturnWareHouseEnityVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "退仓管理服务")
@RestController
@RequestMapping(value = "/shipment")
public class ReturnWarehouseApplyController {
    @Autowired
    private ReturnWarehouseApplyService returnWarehouseApplyService;

    @ApiOperation(value = "申请/修改退仓", notes = "申请/修改退仓")
    @RequestMapping(value = "/returnWarehouse", method = RequestMethod.POST)
    public Result add(@RequestBody @Validated ReturnWarehouseApplyEnity returnWarehouseApplyEnity, BindingResult result,
                      @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        returnWarehouseApplyEnity.setCreateUser(userId);
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), null);
        }
        try {
            Result returnResult = returnWarehouseApplyService.insert(returnWarehouseApplyEnity);
        } catch (Exception e) {
            return Result.failure(390, "申请退仓失败", e.getMessage());
        }
        return Result.success("申请/修改退仓成功");
    }

    @ApiOperation(value = "分页模糊查询退仓信息", notes = "分页模糊查询退仓信息", response = ChangeLableViewVo.class)
    @RequestMapping(value = "/returnWarehouse/list", method = RequestMethod.GET)
    public Result findByCond(@RequestParam(value = "pageNum", required = false) String pageNum,
                             @RequestParam(value = "pageSize", required = false) String pageSize,
                             @RequestParam(value = "shopId", required = false) String shopId,
                             @RequestParam(value = "employeeId", required = false) String employeeId,
                             @RequestParam(value = "statusCd", required = false, defaultValue = "0") int statusCd,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "productName", required = false) String productName,
                             @RequestParam(value = "startTime", required = false) String startTime,
                             @RequestParam(value = "endTime", required = false) String endTime) {
        LinkedHashMap linkedHashMap = null;
        try {
            if (endTime != null) {
                Date date = DateUtil.convertStrToDate(endTime, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
                Date endDate = DateUtil.addDate("dd", 1, date);
                endTime = DateUtil.convertDateToStr(endDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            }
            linkedHashMap = returnWarehouseApplyService.findByCond(pageNum, pageSize, shopId, employeeId, statusCd, keyword, productName, startTime, endTime);
        } catch (Exception e) {
            return Result.failure(390, "模糊查询失败", e.getMessage());
        }
        return Result.success(linkedHashMap);
    }

    @ApiOperation(value = "退仓详细", notes = "退仓详细")
    @RequestMapping(value = "/returnWarehouse/{returnApplyId}", method = RequestMethod.GET)
    public Result getReturnDetail(@PathVariable("returnApplyId") String returnApplyId,
                                  @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        ReturnWareHouseEnityVo returnWareHouseEnityVo = null;
        try {
            returnWareHouseEnityVo = returnWarehouseApplyService.getWarehouseDetail(returnApplyId);
        } catch (Exception e) {
            return Result.failure(390, "查询退仓详细失败", e.getMessage());
        }
        return Result.success(returnWareHouseEnityVo);
    }

    @ApiOperation(value = "编辑查看", notes = "编辑查看")
    @RequestMapping(value = "/returnWarehouse/getDetail/{returnApplyId}", method = RequestMethod.GET)
    public Result getSimpleDetail(@PathVariable("returnApplyId") String returnApplyId,
                                  @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        ReturnWareHouseEnityVo returnWareHouseEnityVo = null;
        try {
            returnWareHouseEnityVo = returnWarehouseApplyService.getWarehouseSimpleDetail(returnApplyId);
        } catch (Exception e) {
            return Result.failure(390, "查询退仓详细失败", e.getMessage());
        }
        return Result.success(returnWareHouseEnityVo);
    }

    @ApiOperation(value = "取消申请", notes = "取消申请")
    @RequestMapping(value = "/returnWarehouse/{returnApplyId}/{reason}", method = RequestMethod.GET)
    public Result cancelReturnApply(@PathVariable("returnApplyId") String returnApplyId, @PathVariable("reason") String reason,
                                    @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        ReturnWareHouseEnityVo returnWareHouseEnityVo = null;
        try {
            returnWarehouseApplyService.deleteApply(returnApplyId, reason, userId);
        } catch (Exception e) {
            return Result.failure(390, "取消申请失败", e.getMessage());
        }
        return Result.success(returnWareHouseEnityVo);
    }

    @ApiOperation(value = "补充移除订单号", notes = "补充移除订单号")
    @RequestMapping(value = "/returnWarehouse/removeOrder", method = RequestMethod.POST)
    public Result addRemoveOrder(@RequestParam(value = "returnApplyId", required = true) String returnApplyId, @RequestParam(value = "removeOrderList", required = true) String[] orderIds,
                                 @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            returnWarehouseApplyService.addRemoveOrder(returnApplyId, orderIds);
        } catch (Exception e) {
            return Result.failure(390, "补充移除订单号败", e.getMessage());
        }
        return Result.success("成功");
    }

    @ApiOperation(value = "指定收货仓", notes = "指定收货仓")
    @RequestMapping(value = "/returnWarehous/receiveWarehouse", method = RequestMethod.POST)
    public Result assignReceiveWarehouse(@RequestBody @Validated ReceiveWarehouseEnity receiveWarehouseEnity, BindingResult result,
                                         @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            if (result.hasErrors()) {
                return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), null);
            }
            receiveWarehouseEnity.setCreateUser(userId);
            receiveWarehouseEnity.setCreateTime(Time.getCurrentDateTime());
            returnWarehouseApplyService.addReceiveWarehouse(receiveWarehouseEnity);
        } catch (IllegalStateException e) {
            return Result.failure(1020, e.getMessage(), null);
        } catch (Exception e) {
            return Result.failure(390, "指定收货仓失败", e.getMessage());
        }
        return Result.success("成功");
    }

    @ApiOperation(value = "审核", notes = "审核")
    @RequestMapping(value = "/returnWarehous/check", method = RequestMethod.PUT)
    public Result checkReturnWarehouseApply(@RequestBody @Validated ReturnWarehouseCheckEnity returnWarehouseCheckEnity, BindingResult result,
                                            @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            if (result.hasErrors()) {
                return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), null);
            }
            returnWarehouseCheckEnity.setUpdateUser(userId);
            returnWarehouseCheckEnity.setUpdateTime(Time.getCurrentDateTime());
            returnWarehouseApplyService.checkReturnWarehouseApply(returnWarehouseCheckEnity);
        } catch (Exception e) {
            return Result.failure(390, "指定收货仓失败", e.getMessage());
        }
        return Result.success("成功");
    }

    @RequestMapping(value = "/returnWarehous/productDetailInfo/{shopId}/{mskuId}", method = RequestMethod.PUT)
    @ApiOperation(value = "根据shopId和mskuId获取产品信息和库存信息", notes = "根据shopId和mskuId获取产品信息和库存信息）", response = ProductDetaiVo.class)
    @ResponseBody
    public Result getProductAndWareHouse(@PathVariable("shopId") String shopId, @PathVariable("mskuId") String mskuId, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            ProductDetaiVo productDetaiVo = returnWarehouseApplyService.getProdetail(shopId, mskuId);
            return Result.success(productDetaiVo);
        } catch (Exception e) {
            return Result.failure(390, "根据shopId和mskuId获取产品信息和库存信息失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/returnWarehous/packingDetail", method = RequestMethod.GET)
    @ApiOperation(value = "根据skuId获取装箱规格信息", notes = "根据skuId获取装箱规格信息）", response = PackingDetailEnity.class)
    @ResponseBody
    public Result getPackingDetail(@RequestParam("skuIds") String[] skuIds, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            List<PackingDetailEnity> packingDetailEnityList = returnWarehouseApplyService.getPackingDetailList(skuIds);
            return Result.success(packingDetailEnityList);
        } catch (Exception e) {
            return Result.failure(390, "根据shopId和mskuId获取产品信息和库存信息失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/returnWarehous/getRemoveOrder/{returnApplyId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据returnApplyId获取所有移除订单号", notes = "根据returnApplyId获取所有移除订单号）", response = PackingDetailEnity.class)
    @ResponseBody
    public Result getRemoveOrder(@PathVariable("returnApplyId") String returnApplyId) {
        try {
            List<String> removeOrderIdList = returnWarehouseApplyService.getRemoveOrderIdList(returnApplyId);
            return Result.success(removeOrderIdList);
        } catch (Exception e) {
            return Result.failure(390, "根据returnApplyId获取所有移除订单号信息失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/returnWarehous/getAllRemoveOrderDetail/{returnApplyId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据returnApplyId获取所有移除订单号详细信息", notes = "根据returnApplyId获取所有移除订单号详细信息）")
    @ResponseBody
    public Result getAllRemoveOrderDetail(@PathVariable("returnApplyId") String returnApplyId) {
        try {
            List<Map> removeOrderInfoEnityList = returnWarehouseApplyService.getAllRemoveOrderDetail(returnApplyId);
            return Result.success(removeOrderInfoEnityList);
        } catch (Exception e) {
            return Result.failure(390, "根据returnApplyId获取所有移除订单号信息失败", e.getMessage());
        }
    }
}
