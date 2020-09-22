package com.wisrc.sys.webapp.service.proxy;

import com.wisrc.sys.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-operation", url = "http://localhost:8080")
public interface MskuService {
    @RequestMapping(value = "/operation/merchandise/msku/batch", method = RequestMethod.GET)
    Result findMskuInfoByIds(@RequestParam("ids") String[] ids);

    @RequestMapping(value = "/operation/merchandise/msku/msku/basis/info", method = RequestMethod.POST)
    Result searchMskuInfo(@RequestParam("pageNum") Integer pageNum,
                          @RequestParam("pageSize") Integer pageSize,
                          @RequestParam("platformName") String platformName,
                          @RequestParam("shopName") String shopName,
                          @RequestParam("mskuId") String mskuId,
                          @RequestParam("excludingMskuIds") String[] excludeMskuIds);

    @RequestMapping(value = "/operation/shop", method = RequestMethod.GET)
    Result getStoreFuzzy(@RequestParam("platformName") String platformName, @RequestParam("shopId") String shopId, @RequestParam("shopName") String shopName);

}
