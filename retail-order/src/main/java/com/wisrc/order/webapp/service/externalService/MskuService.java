package com.wisrc.order.webapp.service.externalService;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-order-operation", url = "http://localhost:8080")
public interface MskuService {

    @RequestMapping(value = "/operation/merchandise/msku", method = RequestMethod.GET)
    Result getMskuInfo(@RequestParam(value = "shopId") String shopId, @RequestParam(value = "mskuId") String mskuId);

    @RequestMapping(value = "/operation/merchandise/msku/batch", method = RequestMethod.GET)
    Result getProduct(@RequestParam(value = "ids") String[] ids);

    @RequestMapping(value = "/operation/merchandise/msku/search", method = RequestMethod.GET)
    Result getMskuByCond(@RequestParam(value = "mskuName") String mskuName);

    @RequestMapping(value = "/operation/merchandise/msku/fnsku/{fnsku}", method = RequestMethod.GET)
    Result getMskuInfoByFnsku(@PathVariable("fnsku") String fnsku);
}
