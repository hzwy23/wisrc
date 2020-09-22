package com.wisrc.crawler.webapp.controller;

import com.wisrc.crawler.webapp.entity.*;
import com.wisrc.crawler.webapp.service.ShipmentInfoLocalService;
import com.wisrc.crawler.webapp.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/crawler")
public class ShipmentInfoLocalController {
    @Autowired
    private ShipmentInfoLocalService shipmentInfoLocalService;

    @RequestMapping(value = "/shipment/shipmentTracingLocalRecord/{trackingNumber}/{carrier}", method = RequestMethod.GET)
    @ApiOperation(value = "根据物流单号获取物流跟踪记录")
    @ResponseBody
    public Result getShipmentTracingRecord(@PathVariable("trackingNumber") String trackingNumber, @PathVariable("carrier") String carrier) {
        try {
            TracingRecordEnity tracingRecordEnity = shipmentInfoLocalService.getShipmentTracingRecord(trackingNumber, carrier);
            return Result.success(tracingRecordEnity);
        } catch (Exception e) {
            return Result.failure(390, "本地接口异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/removeLocalOrderInfo/{removeOrderId}/{sellerId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据移除订单号获取移除明细")
    @ResponseBody
    public Result getRemoveOrderInfo(@PathVariable("removeOrderId") String removeOrderId, @PathVariable("sellerId") String sellerId) {
        try {
            RemoveOrderInfoEnity removeOrderInfoEnity = shipmentInfoLocalService.getRemoveOrderInfo(removeOrderId, sellerId);
            return Result.success(removeOrderInfoEnity);
        } catch (Exception e) {
            return Result.failure(390, "本地接口异常", e.getMessage());
        }
    }


    @RequestMapping(value = "/shipment/localShipmentInfo/{shipmentId}/{sellerId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据Shipment ID获取货件创建信息")
    @ResponseBody
    public Result getShipmentInfo(@PathVariable("shipmentId") String shipmentId, @PathVariable("sellerId") String sellerId) {
        try {
            ShipmentInfoEnity shipmentInfoEnity = shipmentInfoLocalService.getShipmentInfo(shipmentId, sellerId);
            return Result.success(shipmentInfoEnity);
        } catch (Exception e) {
            return Result.failure(390, "本地接口异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/mskuLocalSaleNum/{mskuId}/{sellerId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取亚马逊近几日销量")
    @ResponseBody
    public Result getMskuSaleNum(@PathVariable("mskuId") String mskuId, @PathVariable("sellerId") String sellerId) {
        try {
            MskuSaleNumEnity mskuSaleNumEnity = shipmentInfoLocalService.getMskuSaleNum(mskuId, sellerId);
            return Result.success(mskuSaleNumEnity);
        } catch (Exception e) {
            return Result.failure(390, "亚马逊接口异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/mskuLocalStockInfo/{mskuId}/{sellerId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取亚马库存信息")
    @ResponseBody
    public Result getMskuStockInfo(@PathVariable("mskuId") String mskuId, @PathVariable("sellerId") String sellerId) {
        try {
            ShipmentStockEnity shipmentStockEnity = shipmentInfoLocalService.getMskuStockInfo(mskuId, sellerId);
            return Result.success(shipmentStockEnity);
        } catch (Exception e) {
            return Result.failure(390, "获取亚马库存信息异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/removeLocalOrderInfoList", method = RequestMethod.POST)
    @ApiOperation(value = "根据批量移除订单号获取移除明细")
    @ResponseBody
    public Result getRemoveOrderInfoList(@RequestBody List<RemoveOrderEnity> removeOrderEnityList) {
        try {
            List<RemoveOrderInfoEnity> removeOrderInfoEnityList = shipmentInfoLocalService.getRemoveOrderInfoList(removeOrderEnityList);
            return Result.success(removeOrderInfoEnityList);
        } catch (Exception e) {
            return Result.failure(390, "根据批量移除订单号获取移除明细异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/tracingRecordList", method = RequestMethod.POST)
    @ApiOperation(value = "批量物流信息")
    @ResponseBody
    public Result geTracingRecordList(@RequestBody List<Map<String, String>> tracingMapList) {
        try {
            List<TracingRecordEnity> tracingRecordEnityList = shipmentInfoLocalService.getTracingRecordList(tracingMapList);
            return Result.success(tracingRecordEnityList);
        } catch (Exception e) {
            return Result.failure(390, "批量获取物流信息", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/recentMskuSaleNum", method = RequestMethod.GET)
    @ApiOperation(value = "查询日期内销量")
    @ResponseBody
    public Result geRecentMskuSaleNum(@RequestParam("stratTime") String stratTime, @RequestParam("endTime") String endTime, @RequestParam("shopId") String shopId, @RequestParam("msku") String msku) {
        try {
            List<RecentSaleNumEnity> recentSaleNumEnityList = shipmentInfoLocalService.geRecentMskuSaleNum(stratTime, endTime, shopId, msku);
            return Result.success(recentSaleNumEnityList);
        } catch (Exception e) {
            return Result.failure(390, "查询如期内销量异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/mskuPreSevenDaySaleNum/{mskuId}/{shopId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取亚马逊前7日内的销量")
    @ResponseBody
    public Result getPreSevenDaySaleNum(@PathVariable("mskuId") String mskuId, @PathVariable("shopId") String shopId) {
        try {
            List<RecentSaleNumEnity> recentSaleNumEnityList = shipmentInfoLocalService.getPreSevenDaySaleNum(mskuId, shopId);
            return Result.success(recentSaleNumEnityList);
        } catch (Exception e) {
            return Result.failure(390, "获取亚马逊前7日内的销量接口异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/updateSignTime/{trackingNumber}/{carrier}/{fnsku}", method = RequestMethod.GET)
    @ApiOperation(value = "确认签收")
    @ResponseBody
    public Result updateSignTime(@PathVariable("trackingNumber") String trackingNumber, @PathVariable("carrier") String carrier, @PathVariable("fnsku") String fnsku) {
        try {
            shipmentInfoLocalService.updateSignTime(trackingNumber, carrier,fnsku);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(390, "确认签收接口异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/removeLocalOrderInfoBatch", method = RequestMethod.POST)
    @ApiOperation(value = "批量获取退仓的移除订单的出库数量,签收数量")
    @ResponseBody
    public Result removeLocalOrderInfoBatch(@RequestBody List<Map> parameterMapList) {
        try {
            Map<String, Integer> map = shipmentInfoLocalService.getRemoveOrderInfoBatch(parameterMapList);
            return Result.success(map);
        } catch (Exception e) {
            return Result.failure(390, "批量获取退仓的移除订单的出库数量,签收数量异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/shipmentTransferBatch", method = RequestMethod.POST)
    @ApiOperation(value = "根据某批次物流号及签收时间")
    @ResponseBody
    public Result getShipmentTransfer(@RequestBody List<Map> mapList) {
        try {
            List<ShipmentTransferEnity> shipmentTransferEnityList = shipmentInfoLocalService.getShipmentTransferInfo(mapList);
            return Result.success(shipmentTransferEnityList);
        } catch (Exception e) {
            return Result.failure(390, "本地接口异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/updateWayBillSignTime/{tracingId}/{wayBillId}", method = RequestMethod.GET)
    @ApiOperation(value = "确认签收运单物流")
    @ResponseBody
    public Result updateWayBillSignTime(@PathVariable("tracingId") String tracingId, @PathVariable("wayBillId") String wayBillId) {
        try {
            shipmentInfoLocalService.updateWaybillSignTime(tracingId, wayBillId);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(390, "确认签收接口异常", e.getMessage());
        }
    }
}
