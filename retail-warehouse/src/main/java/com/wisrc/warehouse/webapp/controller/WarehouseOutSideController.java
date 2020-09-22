package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.service.StockService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.SkuWarehouseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "外部接口服务调用")
@RequestMapping(value = "/warehouse")
public class WarehouseOutSideController {
    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/stock/skuIds/total", method = RequestMethod.GET)
    @ApiOperation(value = "根据库存SKU批量查询商品库存总数")
    public Result getStockBySku(@RequestParam("skuIds") String[] skuIds) {

        try {
            List<String> skuIdList = Arrays.asList(skuIds);
            List<Map> list = stockService.getStockTotalBySku(skuIdList);
            return Result.success(list);
        } catch (NumberFormatException e) {
            return Result.failure(390, "根据库存SKU批量查询商品库存总数和日期失败", e.getMessage());
        }

    }

    @RequestMapping(value = "/stock/skuIdAndDate/total", method = RequestMethod.POST)
    @ApiOperation(value = "根据库存SKU和时间批量查询商品库存总数")
    public Result getStockBySku(@RequestBody List<Map> paramaterList) {

        try {
            List<Map> list = stockService.getStockTotalBySkuAndDate(paramaterList);
            return Result.success(list);
        } catch (NumberFormatException e) {
            return Result.failure(390, "根据库存SKU批量查询商品库存总数失败", e.getMessage());
        }

    }

    @RequestMapping(value = "/stock/skuIdAndWahouse/total", method = RequestMethod.POST)
    @ApiOperation(value = "根据库存SKU和warehouse批量查询商品库存总数")
    public Result getStockBySkuAndWarehouse(@RequestBody List<SkuWarehouseVo> paramaterList) {

        try {
            List<Map> list = stockService.getStockBySkuAndWarehouse(paramaterList);
            return Result.success(list);
        } catch (NumberFormatException e) {
            return Result.failure(390, "根据库存SKU和warehouse批量查询商品库存总数失败", e.getMessage());
        }

    }


}
