package com.wisrc.quality.webapp.service;

import com.wisrc.quality.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 外部到货通知单接口
 */
@FeignClient(value = "retail-quality-purchase-08", url = "http://localhost:8080")
public interface OutOrderService {
    //模糊查询订单
    @RequestMapping(value = "/purchase/order/neet/two", method = RequestMethod.GET)
    Result getOrderFuzzy(@RequestParam("pageNum") Integer pageNum,
                         @RequestParam("pageSize") Integer pageSize,
                         @RequestParam("orderId") String orderId,
                         @RequestParam("skuId") String skuId,
                         @RequestParam("keyWords") String keyWords);

    @RequestMapping(value = "/purchase/arrival/{arrivalId}", method = RequestMethod.GET)
    Result getOrderById(@PathVariable("arrivalId") String arrival);

    @RequestMapping(value = "/purchase/arrival/wms/inspection", method = RequestMethod.GET)
    Result getArrivalIdByUuid(@RequestParam("randomValue") String randomValue);
}
