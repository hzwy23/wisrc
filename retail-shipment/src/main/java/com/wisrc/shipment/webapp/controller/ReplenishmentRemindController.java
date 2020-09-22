package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.ReplenishmentRemindService;
import com.wisrc.shipment.webapp.service.impl.MyFeignInfoService;
import com.wisrc.shipment.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@Api(tags = "智能补货提醒")
@RequestMapping(value = "/shipment")
public class ReplenishmentRemindController {

    @Autowired
    private ReplenishmentRemindService replenishmentRemindService;

    @Autowired
    private MyFeignInfoService feignInfo;

    @ApiOperation(value = "补货提醒列表", notes = "模糊查询支持：msku 库存sku 商品名称<br>排序支持字段示例 fbaWarehouseStock:1<br>FBA在仓库存 - fbaWarehouseStock<br>FBA在途库存 - fbaWayStock<br>预估日销 - estimatedDailySales<br>可售天数 - fbaAvailableDays<br>安全库存天数 - safeStockDays")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺编号", paramType = "query"),
            @ApiImplicitParam(name = "warningType", value = "预警类型", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "salesStatus", value = "销售状态", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序 key:val（0倒 1升）", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataType = "int", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页条目数", paramType = "query", dataType = "int", defaultValue = "10", required = true)})
    @GetMapping("/replenishment")
    Result getReplenishmentList(String shopId, Integer warningType, String salesStatus, String keyword, String sort, Integer currentPage, Integer pageSize) {
        HashSet<String> mskuIds = feignInfo.getMskuIds(salesStatus, keyword);
        return Result.success(replenishmentRemindService.getReplenishmentList(shopId, warningType, mskuIds, sort, currentPage, pageSize));
    }

    @ApiOperation(value = "补货提醒详情")
    @ApiImplicitParam(name = "replenishmentId", value = "补货编号", required = true, paramType = "path")
    @GetMapping("/replenishment/{replenishmentId}")
    Result getReplenishmentInfo(@PathVariable String replenishmentId) {
        return Result.success(replenishmentRemindService.getReplenishmentInfo(replenishmentId));
    }

    @ApiOperation(value = "补货建议方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "方案编号", paramType = "query"),
            @ApiImplicitParam(name = "replenishmentId", value = "补货编号", paramType = "query")})
    @GetMapping("/replenishment/scheme")
    Result getProposalScheme(String uuid, String replenishmentId) {
        return Result.success(replenishmentRemindService.getProposalScheme(uuid, replenishmentId));
    }

    @ApiOperation(value = "预警天数信息")
    @GetMapping("/replenishment/warning")
    Result getWarningDays() {
        return Result.success(replenishmentRemindService.getWarningDays());
    }

    @ApiOperation(value = "补货提醒预警码表信息")
    @GetMapping("/replenishment/warning/attr")
    Result getWarningTypeAttr() {
        return Result.success(replenishmentRemindService.getWarningTypeAttr());
    }

    @ApiOperation(value = "预警天数设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warningId", value = "预警编号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "days", value = "预警天数", required = true, paramType = "query", dataType = "int")})
    @PutMapping("/replenishment/warning")
    Result setWarningDays(String warningId, Integer days) {
        if (replenishmentRemindService.setWarningDays(warningId, days)) {
            return Result.success();
        }
        return Result.failure(500, "预警编号不存在", null);
    }

    @ApiOperation(value = "安全库存天数设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "replenishmentId", value = "补货编号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "days", value = "天数", required = true, paramType = "query", dataType = "int")})
    @PutMapping("/replenishment/safedays")
    Result setSafeDays(String replenishmentId, Integer days) {
        if (replenishmentRemindService.setSafeDays(replenishmentId, days)) {
            return Result.success();
        }
        return Result.failure(500, "补货编号不存在", null);
    }

    @ApiOperation(value = "获取日销量-预留")
    @GetMapping("/replenishment/dailysale")
    Result getSalesPlan() {
        return Result.failure(500, "模块暂未开发-待办", null);
    }

    @ApiOperation(value = "立即补货-预留")
    @GetMapping("/replenishment/supplement")
    Result addSupplement() {
        return Result.failure(500, "模块暂未开发-待办", null);
    }

    @ApiOperation(value = "交运跟踪-预留")
    @GetMapping("/replenishment/transport")
    Result gstTransport() {
        return Result.failure(500, "模块暂未开发-待办", null);
    }

}
