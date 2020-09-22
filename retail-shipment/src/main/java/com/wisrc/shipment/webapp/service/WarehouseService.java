package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "retail-shipment-warehouse-23", url = "http://localhost:8080")
public interface WarehouseService {

    @GetMapping("/warehouse/manage/info")
    Result getMerchase(@RequestParam("pageNum") String pageNum, @RequestParam("pageSize") String pageSize,
                       @RequestParam("statusCd") Integer statusCd, @RequestParam("typeCd") Integer typeCd);

    @RequestMapping(value = "/warehouse/stock/skuIds", method = RequestMethod.GET)
    Result getWareHouserInfo(@RequestParam(value = "skuIds") String[] skuIds);

    @RequestMapping(value = "/warehouse/stock/cond", method = RequestMethod.POST, consumes = "application/json")
    Result getWareHouserInfoBatch(@RequestBody String map);

    @RequestMapping(value = "/warehouse/stock/fnSkuId/warehouseId", method = RequestMethod.GET)
    Result getWareHouserDetail(@RequestParam("fnSkuId") String fnSkuId, @RequestParam("warehouseId") String warehouseId);

    @RequestMapping(value = "/warehouse/manage/info/idlist", method = RequestMethod.GET)
    Result getWareHouseBatch(@RequestParam("warehouseId") String warehouseId);

    @RequestMapping(value = "/warehouse/stock/skuId/warehouseId", method = RequestMethod.GET)
    Result getWareHouser(@RequestParam("skuId") String skuId, @RequestParam("warehouseId") String warehouseId);

}
