package com.wisrc.wms.webapp.service.externalService;

import com.wisrc.wms.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "retail-wms-replenishment-01", url = "http://localhost:8080")
public interface ReplenishmentService {

    /**
     * 通过补货单id获取补货单详情信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/replenishment/fba/{fbaReplenishmentId}", method = RequestMethod.GET, consumes = "application/json")
    Result getFbaReplenishmentById(@PathVariable("fbaReplenishmentId") String id);

    @RequestMapping(value = "/replenishment/wms/transfer/packInfo/return", method = RequestMethod.POST, consumes = "application/json")
    Result transferPackageInfoReturn(@RequestBody String transferOrderPackBasicVO);

    @RequestMapping(value = "/replenishment/wms/transfer/out/return", method = RequestMethod.POST, consumes = "application/json")
    Result transferOutReturn(@RequestBody String transferOutBasicVO);

    @RequestMapping(value = "/replenishment/transfer/{transferId}", method = RequestMethod.GET)
    Result getTransferInfoById(@PathVariable("transferId") String transferId);
}
