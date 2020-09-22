package com.wisrc.merchandise.service;

import com.wisrc.merchandise.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-merchandise-crawler", url = "http://localhost:8080")
public interface CrawlerOutsideService {
    @RequestMapping(value = "/crawler/shipment/mskuPreSevenDaySaleNum/{mskuId}/{shopId}", method = RequestMethod.GET)
    Result getAvgSales(@PathVariable("mskuId") String mskuId, @PathVariable("shopId") String shopId);

    @RequestMapping(value = "/crawler/shipment/mskuInfo/{mskuId}/{sellerId}", method = RequestMethod.GET)
    Result getMskuCrewler(@PathVariable("mskuId") String mskuId, @PathVariable("sellerId") String sellerId);
}
