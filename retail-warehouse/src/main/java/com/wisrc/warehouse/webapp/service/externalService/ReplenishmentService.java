package com.wisrc.warehouse.webapp.service.externalService;

import com.wisrc.warehouse.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-warehouse-replenishment-05", url = "http://localhost:8080")
public interface ReplenishmentService {
    @RequestMapping(value = "/replenishment/fba/{fbaReplenishmentId}", method = RequestMethod.GET)
    public Result getReplenishment(@PathVariable("fbaReplenishmentId") String fbaReplenishmentId);

    /**
     * 外部服务查询调拨单
     *
     * @param transferId
     * @return
     */
    @RequestMapping("/replenishment/transfer/{transferId}")
    Result getTransfer(@PathVariable("transferId") String transferId);
}
