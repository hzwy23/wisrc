package com.wisrc.replenishment.webapp.service.externalService;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "retail-replenishment-operation-31", url = "http://localhost:8080")
public interface MskuService {

    @RequestMapping(value = "/operation/merchandise/msku", method = RequestMethod.GET)
    Result getMskuInfo(@RequestParam(value = "shopId") String shopId, @RequestParam(value = "mskuId") String mskuId, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "doesDelete") Integer doesDelete);

    @RequestMapping(value = "/operation/merchandise/msku/batch", method = RequestMethod.GET)
    Result getProduct(@RequestParam(value = "ids") String[] ids);

    @RequestMapping(value = "/operation/merchandise/msku/search", method = RequestMethod.GET)
    Result getMskuByCond(@RequestParam(value = "asin") String asin, @RequestParam(value = "skuid") String skuid, @RequestParam(value = "productName") String productName);

    @RequestMapping(value = "/operation/shop", method = RequestMethod.GET)
    Result getShop(@RequestParam("statusCd") String statusCd);

    @RequestMapping(value = "/operation/merchandise/updateMskuSale", method = RequestMethod.POST, consumes = "application/json")
    Result updateMskuSaleStock(@RequestBody String mskuSaleNumEnityList);

    @RequestMapping(value = "/operation/shop/{shopId}", method = RequestMethod.GET)
    Result getShopById(@PathVariable("shopId") String shopId);

    @RequestMapping(value = "/operation/merchandise/updateMskuStock", method = RequestMethod.POST, consumes = "application/json")
    Result updateMskuStock(@RequestBody String mskuStockList);

    @RequestMapping(value = "/operation/merchandise/unShelve", method = RequestMethod.GET)
    Result getUnShelve();

    @RequestMapping(value = "/operation/merchandise/updateShelveInfo", method = RequestMethod.POST, consumes = "application/json")
    Result updateShelveInfo(@RequestBody String shelveInfoList);

}
