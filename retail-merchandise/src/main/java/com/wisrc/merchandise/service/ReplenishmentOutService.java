package com.wisrc.merchandise.service;

import com.wisrc.merchandise.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-merchandise-replenishment", url = "http://localhost:8080")
public interface ReplenishmentOutService {
    // 获取FBA在途
    @RequestMapping(value = "/replenishment/fba/msku/underway", method = RequestMethod.POST)
    Result getFBAUnderWay(@RequestParam("commodityIdList") List<String> commodityIdList);
}
