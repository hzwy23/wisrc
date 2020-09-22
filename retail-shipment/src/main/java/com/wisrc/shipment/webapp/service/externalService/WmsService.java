package com.wisrc.shipment.webapp.service.externalService;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-shipment-wms", url = "http://localhost:8080")
public interface WmsService {
    /**
     * 退仓单同步
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/wms/returnWare/sync", method = RequestMethod.POST, consumes = "application/json")
    Result returnWareSync(@RequestBody String vo);


    /**
     * 换标单同步
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/wms/changeLabel/sync", method = RequestMethod.POST, consumes = "application/json")
    Result changeLabelSync(@RequestBody String vo);


}
