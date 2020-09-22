package com.wisrc.shipment.webapp.service.externalService;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-shipment-operation-14", url = "http://localhost:8080")
public interface MskuService {

    @RequestMapping(value = "/operation/merchandise/msku", method = RequestMethod.GET)
    Result getMskuInfo(@RequestParam(value = "shopId") String shopId, @RequestParam(value = "mskuId") String mskuId, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") String auth, @RequestParam(value = "doesDelete") Integer doesDelete);

    @RequestMapping(value = "/operation/merchandise/msku/batch", method = RequestMethod.GET)
    Result getProduct(@RequestParam(value = "ids") String[] ids);

    @RequestMapping(value = "/operation/merchandise/msku/search", method = RequestMethod.GET)
    Result getMskuByCond(@RequestParam(value = "asin") String asin, @RequestParam(value = "skuid") String skuid, @RequestParam(value = "productName") String productName);

    @RequestMapping(value = "/operation/merchandise/msku/fnsku/{fnsku}", method = RequestMethod.GET)
    Result getMskuInfoByFnsku(@PathVariable("fnsku") String fnsku);

    /**
     * 通过fnsku模糊查询商品
     *
     * @param fnsku
     * @return
     */
    @RequestMapping(value = "/operation/merchandise/msku/fnsku/like", method = RequestMethod.GET)
    Result getMskuInfoByFnskuLike(@RequestParam("fnsku") String fnsku);
}
