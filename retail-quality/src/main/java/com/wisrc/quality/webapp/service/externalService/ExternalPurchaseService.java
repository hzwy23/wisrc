package com.wisrc.quality.webapp.service.externalService;

import com.wisrc.quality.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "retail-quality-purchase-01", url = "http://localhost:8080")
public interface ExternalPurchaseService {
    //更新到货通知单来源
    @RequestMapping(value = "purchase/invoking/arrival/product", method = RequestMethod.PUT, consumes = "application/json")
    Result getArrivalProduct(@RequestBody Map arrivalProductEditVo);

    //通过订单号查询采购订单所需参数
    @RequestMapping(value = "purchase/invoking/order/quantity", method = RequestMethod.GET)
    Result getQuality(@RequestParam("orderId") String orderId, @RequestParam("skuId") List<String> skuId);
}
