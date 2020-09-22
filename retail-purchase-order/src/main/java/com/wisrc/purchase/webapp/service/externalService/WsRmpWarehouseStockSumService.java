package com.wisrc.purchase.webapp.service.externalService;

import com.wisrc.purchase.webapp.entity.WsRmpWarehouseStockSumEntity;
import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "retail-purchase-warehouse-21", url = "http://localhost:8080")
public interface WsRmpWarehouseStockSumService {

    @RequestMapping(value = "warehouse/goods/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    Result addStockSum(WsRmpWarehouseStockSumEntity wsRmpWarehouseStockSumEntity);
}
