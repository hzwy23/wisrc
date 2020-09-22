package com.wisrc.crawler.webapp.controller;

import com.wisrc.crawler.webapp.dao.MskuStockDao;
import com.wisrc.crawler.webapp.dao.TracingRecordDao;
import com.wisrc.crawler.webapp.entity.MskuInfoEnity;
import com.wisrc.crawler.webapp.entity.MskuSaleNumEnity;
import com.wisrc.crawler.webapp.entity.RemoveOrderInfoEnity;
import com.wisrc.crawler.webapp.entity.ShipmentInfoEnity;
import com.wisrc.crawler.webapp.service.ExternalService.MskuService;
import com.wisrc.crawler.webapp.service.ExternalService.ReplenishmentService;
import com.wisrc.crawler.webapp.service.ExternalService.WarehouseService;
import com.wisrc.crawler.webapp.service.ShipmentInfoService;
import com.wisrc.crawler.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api(tags = "获取物流相关信息")
@RequestMapping(value = "/crawler")
public class ShipmentInfoController {
    @Autowired
    private ShipmentInfoService shipmentInfoService;

    @Autowired
    private ReplenishmentService replenishmentService;

    @Autowired
    private TracingRecordDao tracingRecordDao;

    @Autowired
    private MskuStockDao mskuStockDao;

    @Autowired
    private MskuService mskuService;

    @Autowired
    private WarehouseService warehouseService;


    @RequestMapping(value = "/shipment/shipmentTracingRecord/{shipmentId}/{shipmentType}", method = RequestMethod.GET)
    @ApiOperation(value = "根据物流单号获取物流跟踪记录")
    @ResponseBody
    public Result getShipmentTracingRecord(@PathVariable("shipmentId") String shipmentId, @PathVariable("shipmentType") String shipmentType) {
        try {
            Map map = shipmentInfoService.getShipmentTracingRecord(shipmentId, shipmentType);
            return Result.success(map);
        } catch (Exception e) {
            return Result.failure(390, "亚马逊接口异常", null);
        }
    }

    @RequestMapping(value = "/shipment/removeOrderInfo/{removeOrderId}/{sellerId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据移除订单号获取移除明细")
    @ResponseBody
    public Result getRemoveOrderInfo(@PathVariable("removeOrderId") String removeOrderId, @PathVariable("sellerId") String sellerId) {
        try {
            RemoveOrderInfoEnity removeOrderInfoEnity = shipmentInfoService.getRemoveOrderInfo(removeOrderId, sellerId);
            return Result.success(removeOrderInfoEnity);
        } catch (Exception e) {
            return Result.failure(390, "根据移除订单号获取移除明细接口异常", e.getMessage());
        }
    }


    @RequestMapping(value = "/shipment/shipmentInfo/{shipmentId}/{sellerId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据Shipment ID获取货件创建信息")
    @ResponseBody
    public Result getShipmentInfo(@PathVariable("shipmentId") String shipmentId, @PathVariable("sellerId") String sellerId) {
        try {
            ShipmentInfoEnity shipmentInfoEnity = shipmentInfoService.getShipmentInfo(shipmentId, sellerId);
            return Result.success(shipmentInfoEnity);
        } catch (Exception e) {
            return Result.failure(390, "根根据Shipment ID获取货件创建信息接口异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/mskuInfo/{mskuId}/{sellerId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据msku编号获取亚马逊商品信息")
    @ResponseBody
    public Result getShipmentMskuInfo(@PathVariable("mskuId") String mskuId, @PathVariable("sellerId") String sellerId) {
        try {
            MskuInfoEnity mskuInfoEnity = shipmentInfoService.getShipmentMskuInfo(mskuId, sellerId);
            return Result.success(mskuInfoEnity);
        } catch (Exception e) {
            return Result.failure(390, "根据msku编号获取亚马逊商品信息接口异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/mskuSaleNum/{mskuId}/{sellerId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取亚马逊近几日销量")
    @ResponseBody
    public Result getMskuSaleNum(@PathVariable("mskuId") String mskuId, @PathVariable("sellerId") String sellerId) {
        try {
            MskuSaleNumEnity mskuSaleNumEnity = shipmentInfoService.getMskuSaleNum(mskuId, sellerId);
            return Result.success(mskuSaleNumEnity);
        } catch (Exception e) {
            return Result.failure(390, "亚马逊接口异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/mskuStockInfo/{mskuId}/{sellerId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取亚马库存信息")
    @ResponseBody
    public Result getMskuStockInfo(@PathVariable("mskuId") String mskuId, @PathVariable("sellerId") String sellerId) {
        try {
            Map map = shipmentInfoService.getMskuStockInfo(mskuId, sellerId);
            return Result.success(map);
        } catch (Exception e) {
            return Result.failure(390, "亚马逊接口异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/mskuInfoCond/{mskuId}/{shopId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据msku编号和店铺Id获取亚马逊商品信息")
    @ResponseBody
    public Result mskuInfo(@PathVariable("mskuId") String mskuId, @PathVariable("shopId") String shopId) {
        try {
            MskuInfoEnity mskuInfoEnity = shipmentInfoService.getShipmentMskuInfoByShopIdAndMskuId(mskuId, shopId);
            return Result.success(mskuInfoEnity);
        } catch (Exception e) {
            return Result.failure(390, "根据msku编号获取亚马逊商品信息接口异常", e.getMessage());
        }
    }

}
