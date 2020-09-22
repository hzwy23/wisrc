package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.service.LogisticsPlanService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.logisticsPlan.GetSalesDemandQuantityVo;
import com.wisrc.replenishment.webapp.vo.logisticsPlan.LogisticsPlanPageVo;
import com.wisrc.replenishment.webapp.vo.logisticsPlan.MskuLogisticsPlanEditVo;
import com.wisrc.replenishment.webapp.vo.logisticsPlan.MskuLogisticsPlanSaveVo;
import com.wisrc.replenishment.webapp.vo.swagger.LogisticsPlanByMskuIdModel;
import com.wisrc.replenishment.webapp.vo.swagger.LogisticsPlanPageModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/replenishment/logistics/plan")
@RestController
@Api(value = "/logistics/plan", description = "FBA补货", tags = "物流计划")
public class LogisticsPlanController {
    @Autowired
    private LogisticsPlanService logisticsPlanService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = LogisticsPlanPageModel.class)
    })
    @ApiOperation(value = "物流计划列表", notes = "")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Result getLogisticsPlanPage(@Valid LogisticsPlanPageVo logisticsPlanPageVo, BindingResult bindingResult,
                                       @RequestHeader(value = "X-AUTH-ID") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        return logisticsPlanService.getLogisticsPlanPage(logisticsPlanPageVo, userId);
    }

    @ApiOperation(value = "物流计划保存", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commodityId", value = "商品ID", paramType = "path", dataType = "String", required = true)
    })
    @RequestMapping(value = "/detail/{commodityId}/save", method = RequestMethod.POST)
    public Result saveLogisticsPlan(@Valid @RequestBody MskuLogisticsPlanSaveVo mskuLogisticsPlanSaveVo, BindingResult bindingResult, @PathVariable("commodityId") String commodityId,
                                    @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        if (logisticsPlanService.checkSalesDate(mskuLogisticsPlanSaveVo, commodityId)) {
            return new Result(400, "", "销售截止日期区间有误");
        }
        return logisticsPlanService.saveLogisticsPlan(mskuLogisticsPlanSaveVo, userId, commodityId);
    }

    @ApiOperation(value = "物流计划编辑", notes = "")
    @RequestMapping(value = "/detail/{logisticsPlanId}/edit", method = RequestMethod.PUT)
    public Result editLogisticsPlan(@Valid @RequestBody MskuLogisticsPlanEditVo mskuLogisticsPlanEditVo, BindingResult bindingResult, @PathVariable("logisticsPlanId") String logisticsPlanId,
                                    @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        return logisticsPlanService.editLogisticsPlan(mskuLogisticsPlanEditVo, userId, logisticsPlanId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = LogisticsPlanByMskuIdModel.class)
    })
    @ApiOperation(value = "物流计划详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commodityId", value = "商品ID", paramType = "path", dataType = "String", required = true)
    })
    @RequestMapping(value = "/detail/{commodityId}", method = RequestMethod.GET)
    public Result logisticsPlanByMskuId(@PathVariable("commodityId") String commodityId) {
        return logisticsPlanService.logisticsPlanByMskuId(commodityId);
    }

    @ApiOperation(value = "删除物流计划", notes = "")
    @RequestMapping(value = "/detail/{logisticsPlanId}", method = RequestMethod.DELETE)
    public Result deleteLogisticsPlan(@PathVariable("logisticsPlanId") String logisticsPlanId, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        return logisticsPlanService.deleteLogisticsPlan(logisticsPlanId, userId);
    }

    @ApiOperation(value = "获取销售需求量", notes = "")
    @RequestMapping(value = "/detail/{commodityId}/demand", method = RequestMethod.GET)
    public Result getSalesDemandQuantity(@Valid GetSalesDemandQuantityVo getSalesDemandQuantityVo, BindingResult bindingResult, @PathVariable("commodityId") String commodityId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        if (getSalesDemandQuantityVo.getSalesStartTime().getTime() > getSalesDemandQuantityVo.getSalesEndTime().getTime()) {
            return new Result(400, "", "销售截止日期区间有误");
        }
        return logisticsPlanService.getSalesDemandQuantity(getSalesDemandQuantityVo, commodityId);
    }

    @ApiOperation(value = "获取最近一次发货计划的销售截止日期", notes = "")
    @RequestMapping(value = "/detail/{commodityId}/deadline", method = RequestMethod.GET)
    public Result getsalesLaseEndTime(@PathVariable("commodityId") String commodityId) {
        return logisticsPlanService.getSalesLaseEndTime(commodityId);
    }
}
