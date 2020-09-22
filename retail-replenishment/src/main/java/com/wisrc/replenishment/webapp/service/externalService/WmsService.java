package com.wisrc.replenishment.webapp.service.externalService;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-replenishment-wms-09", url = "http://localhost:8080")
public interface WmsService {

    /**
     * 取消单同步（包括销售出库取消 和 FBA补货出库取消）
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/wms/cancelReplen", method = RequestMethod.GET, consumes = "application/json")
    Result cancelReplen(@RequestBody String vo);

    @RequestMapping(value = "/wms/virtualOutBill/sync", method = RequestMethod.GET, consumes = "application/json")
    Result virtualOutSync(@RequestBody String vo);

    @RequestMapping(value = "/wms/transfer/sync", method = RequestMethod.POST, consumes = "application/json")
    Result transferOutSync(@RequestBody String vo);

    @RequestMapping(value = "/wms/transfer/virtual/enter", method = RequestMethod.POST, consumes = "application/json")
    Result transferVirtualEnterSync(@RequestBody String vo);
}
