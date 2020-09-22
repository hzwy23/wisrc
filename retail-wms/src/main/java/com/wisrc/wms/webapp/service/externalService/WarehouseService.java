package com.wisrc.wms.webapp.service.externalService;

import com.wisrc.wms.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "retail-wms-warehouse", url = "http://localhost:8080")
@Component
public interface WarehouseService {

    /**
     * 修改加工任务单状态
     *
     * @param processTaskId
     * @param status
     * @return
     */
    @RequestMapping(value = "/warehouse/process/status/{processTaskId}/{status}", method = RequestMethod.PUT)
    Result changeStatus(@PathVariable("processTaskId") String processTaskId,
                        @PathVariable("status") String status);

    @PostMapping(value = "/warehouse/sync/stock", consumes = "application/json")
    @Async
    Result syncWarehouseStock(@RequestBody String stockReturnVOS);

    @PostMapping(value = "/warehouse/sync/water", consumes = "application/json")
    @Async
    Result syncStockWater(@RequestBody String voList);


}
