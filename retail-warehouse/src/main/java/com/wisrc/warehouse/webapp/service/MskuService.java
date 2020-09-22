package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "retail-warehouse-operation-07", url = "http://localhost:8080")
public interface MskuService {

    @RequestMapping(value = "/operation/merchandise/msku", method = RequestMethod.GET)
    Result getMskuInfo(@RequestParam(value = "shopId") String shopId, @RequestParam(value = "mskuId") String mskuId);

    @RequestMapping(value = "/operation/merchandise/msku/batch", method = RequestMethod.GET)
    Result getProduct(@RequestParam(value = "ids") String[] ids);

    @RequestMapping(value = "/operation/merchandise/msku/search", method = RequestMethod.GET)
    Result getMskuByCond(@RequestParam(value = "asin") String asin, @RequestParam(value = "skuid") String skuid, @RequestParam(value = "productName") String productName);

    @RequestMapping(value = "/operation/shop", method = RequestMethod.GET)
    Result getShop(@RequestParam("statusCd") int statusCd);

    @RequestMapping(value = "/operation/merchandise/WarehouseIdAndFnsku", method = RequestMethod.POST, consumes = "application/json")
    Result getWarehouseAndFnsku(@RequestBody List<Map> mapList);

    /**
     * 根据fnsku编码查询出对应的商品信息
     *
     * @return
     */
    @RequestMapping("/operation/merchandise/msku/fnsku/{fnsku}")
    Result getMskuInfoByFnSkuId(@PathVariable("fnsku") String fnSku);
}
