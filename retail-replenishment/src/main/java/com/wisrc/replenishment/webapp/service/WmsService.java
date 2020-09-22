package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-replenishment-wms-28",url = "http://localhost:8080")
public interface WmsService {
    /**
     * FBA补货单信息同步到wms
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/wms/fbaReplenishmentOutBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result syncFbaReplenishBill(@RequestBody String entity);

    /**
     * FBA补货物流，报关资料同步
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/wms/logistics/declare/sync", method = RequestMethod.POST, consumes = "application/json")
    Result logisticsDeclareSync(@RequestBody String entity);

    /**
     * 调拨单报关资料同步
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/wms/transdoc/sync", method = RequestMethod.POST, consumes = "application/json")
    Result transferDocSync(@RequestBody String entity);
}

