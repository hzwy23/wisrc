package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.service.StockDetailService;
import com.wisrc.warehouse.webapp.entity.FbaStockDetailEntity;
import com.wisrc.warehouse.webapp.service.StockService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.FnSkuStockVO;
import com.wisrc.warehouse.webapp.vo.StockDetailVO;
import com.wisrc.warehouse.webapp.vo.WarehouseOutEnterInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "库存明细")
@RequestMapping(value = "/warehouse")
public class StockDetailController {
    @Autowired
    private StockDetailService stockDetailService;
    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/stock/detail", method = RequestMethod.GET)
    @ApiOperation(value = "根据库存stockId查询库存明细信息", response = WarehouseOutEnterInfoVO.class)
    public Result getDetailById(@RequestParam(value = "pageNum", required = false) String pageNum,
                                @RequestParam(value = "pageSize", required = false) String pageSize,
                                @RequestParam(value = "skuId") String skuId,
                                @RequestParam(value = "warehouseId") String warehouseId) {

        try {
            //String warehouseId = stockService.getWarehouseId(stockId);

            String warehouseType = null;
            if (warehouseId != null) {
                warehouseType = warehouseId.substring(0, 1);
            } else {
                return Result.failure(300, "改库存暂无明细", "");
            }
            if (pageNum != null && pageSize != null) {
                int num = Integer.valueOf(pageNum);
                int size = Integer.valueOf(pageSize);
                if ("F".equals(warehouseType)) {
                    LinkedHashMap list = stockDetailService.getFbaDetailById(num, size, skuId, warehouseId);
                    return Result.success(list);
                } else {
                    LinkedHashMap list = stockDetailService.getDetailById(num, size, skuId, warehouseId);
                    return Result.success(list);
                }
            } else {
                if ("F".equals(warehouseType)) {
                    List<FbaStockDetailEntity> list = stockDetailService.getFbaDetailById(skuId, warehouseId);
                    return Result.success(list);
                } else {
                    List<StockDetailVO> list = stockDetailService.getDetailById(skuId, warehouseId);
                    return Result.success(list);
                }
            }
        } catch (NumberFormatException e) {
            return Result.failure();
        }

    }

    @RequestMapping(value = "/stock/fnSkuId/warehouseId", method = RequestMethod.GET)
    @ApiOperation(value = "根据fnSkuId和warehouseId查询库存明细信息", response = WarehouseOutEnterInfoVO.class)
    public Result getFnSkuStock(@RequestParam("fnSkuId") String fnSkuId, @RequestParam("warehouseId") String warehouseId) {
        List<FnSkuStockVO> list = new ArrayList<>();
        try {
            list = stockDetailService.getFnSkuStock(fnSkuId, warehouseId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure();
        }


    }

    @RequestMapping(value = "/stock/skuId/warehouseId", method = RequestMethod.GET)
    @ApiOperation(value = "根据fnSkuId和warehouseId查询库存明细信息", response = WarehouseOutEnterInfoVO.class)
    public Result getSkuStock(@RequestParam("skuId") String skuId, @RequestParam("warehouseId") String warehouseId) {
        List<FnSkuStockVO> list = new ArrayList<>();
        try {
            list = stockDetailService.getSkuStock(skuId, warehouseId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure();
        }


    }


    @RequestMapping(value = "/stock/{skuId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据fnSkuId查询海外仓库存明细信息")
    public Result getFnSkuStockOversea(@PathVariable("skuId") String skuId) {
        List<FnSkuStockVO> list = new ArrayList<>();
        try {
            list = stockDetailService.getFnSkuStockOversea(skuId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure();
        }


    }

    @RequestMapping(value = "/stock/fbaStockDetail", method = RequestMethod.PUT)
    @ApiOperation(value = "更新库存明细", response = WarehouseOutEnterInfoVO.class)
    public Result updateFbaStockDetail(@RequestBody List<Map> mapList) {
        try {
            Result result = stockDetailService.updateFbaStockDetail(mapList);
            return result;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), null);
        }


    }
}
