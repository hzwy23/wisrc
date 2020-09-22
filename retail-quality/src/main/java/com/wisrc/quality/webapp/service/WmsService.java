package com.wisrc.quality.webapp.service;

import com.wisrc.quality.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-quality-wms-11", url = "http://localhost:8080")
public interface WmsService {

    /**
     * 检验合格数同步
     *
     * @param inspectionItemCheckVO
     * @return
     */
    @RequestMapping(value = "/wms/arrivalQualityCheck/sync", method = RequestMethod.POST, consumes = "application/json")
    Result inspectionItemsCheckSync(@RequestBody String inspectionItemsCheckVO);
}
