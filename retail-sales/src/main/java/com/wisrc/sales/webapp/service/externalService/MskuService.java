package com.wisrc.sales.webapp.service.externalService;

import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.vo.msku.GetMskuIdVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "retail-sales-operation-01", url = "http://localhost:8080")
public interface MskuService {

    @RequestMapping(value = "/operation/merchandise/msku", method = RequestMethod.GET)
    Result getMskuInfo(@RequestHeader("X-AUTH-ID") String userId, @RequestParam(value = "shopId") String shopId, @RequestParam(value = "mskuId") String mskuId, @RequestParam(value = "doesDelete") Integer doesDelete);

    @RequestMapping(value = "/operation/merchandise/msku/batch", method = RequestMethod.GET)
    Result getProduct(@RequestParam(value = "ids") String[] ids);

    @RequestMapping(value = "/operation/merchandise/msku/search", method = RequestMethod.GET)
    Result getMskuByCond(@RequestHeader("X-AUTH-ID") String userId, @RequestParam(value = "asin") String asin, @RequestParam(value = "skuid") String skuid, @RequestParam(value = "productName") String productName, @RequestParam(value = "salesStatus") Integer salesStatus);

    @RequestMapping(value = "/operation/merchandise/msku/id/batch", method = RequestMethod.POST, consumes = "application/json")
    Result shopIdAndMskuToId(@RequestHeader("X-AUTH-ID") String userId, @RequestBody GetMskuIdVO getMskuIdVo);
}
