package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.service.SkuDetailService;
import com.wisrc.warehouse.webapp.service.StockService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.stockVO.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "库存汇总根据SKU查明细")
@RequestMapping(value = "/warehouse")
public class SkuStockDetailController {
    @Autowired
    private StockService stockService;
    @Autowired
    private SkuDetailService skuDetailService;

    @ApiOperation(value = "根据SkuId查询虚拟仓在仓库存明细信息", response = ProxyVirtual.class)
    @RequestMapping(value = "/virtual/stock/detail", method = RequestMethod.GET)
    public Result getVirtualStockDetail(@RequestParam("skuId") String skuId, @RequestParam("date") String date) {
        List<ProxyVirtual> list = stockService.getVirtualStock(skuId, date);
        return Result.success(list);
    }


    @RequestMapping(value = "/oversea/stock/detail", method = RequestMethod.GET)
    @ApiOperation(value = "根据SkuId查询海外仓在仓库存明细信息", response = ProxyVirtual.class)
    public Result getOverseaStockDetail(@RequestParam("skuId") String skuId, @RequestParam("date") String date) {
        List<ProxyVirtual> list = new ArrayList<>();
        try {
            list = skuDetailService.getSkuStockOversea(skuId, date);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/local/stock/detail", method = RequestMethod.GET)
    @ApiOperation(value = "根据skuId查询本地仓在仓库存明细信息", response = ProxyVirtual.class)
    public Result getLocalStockDetail(@RequestParam("skuId") String skuId, @RequestParam("date") String date) {
        List<ProxyVirtual> list = new ArrayList<>();
        try {
            list = skuDetailService.getLocalStockDetail(skuId, date);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/fba/onway/detail", method = RequestMethod.GET)
    @ApiOperation(value = "根据skuId，mskuId和日期查询FBA在途明细信息")
    public Result getFbaOnwayDetail(@RequestParam("skuId") String skuId, @RequestParam("date") String date, @RequestParam("mskuId") String mskuId) {
        List<FbaOnwayDetailVO> list = new ArrayList<>();
        try {
            list = skuDetailService.getFbaOnwayDetail(skuId, date, mskuId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/fba/return/detail", method = RequestMethod.GET)
    @ApiOperation(value = "根据msku和店铺查询FBA退仓明细信息")
    public Result getFbaReturnDetail(@RequestParam("skuId") String skuId, @RequestParam("mskuId") String mskuId, @RequestParam("date") String date) {
        List<FbaReturnDetailVO> list = new ArrayList<>();
        try {
            list = skuDetailService.getFbaReturnDetail(skuId, date, mskuId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/oversea/transfer/detail", method = RequestMethod.GET)
    @ApiOperation(value = "根据skuId查询海外仓国内调拨明细信息")
    public Result getTransferDetail(@RequestParam("skuId") String skuId, @RequestParam("date") String date) {
        List<OverseaTransferDetailVO> list = new ArrayList<>();
        try {
            list = skuDetailService.getTransferDetail(skuId, date);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/local/onway/detail", method = RequestMethod.GET)
    @ApiOperation(value = "根据skuId查询本地仓在途明细信息")
    public Result getLocalOnwayDetail(@RequestParam("skuId") String skuId, @RequestParam("date") String date) {
        List<LocalOnwayDetailVO> list = new ArrayList<>();
        try {
            list = skuDetailService.getLocalOnwayDetail(skuId, date);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/product/detail", method = RequestMethod.GET)
    @ApiOperation(value = "根据skuId查询生产中明细信息", response = ProductDetailVO.class)
    public Result getProductDetail(@RequestParam("skuId") String skuId, @RequestParam("date") String date) {
        List<ProductDetailVO> list = new ArrayList<>();
        try {
            list = skuDetailService.getProductDetail(skuId, date);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure();
        }
    }
}
