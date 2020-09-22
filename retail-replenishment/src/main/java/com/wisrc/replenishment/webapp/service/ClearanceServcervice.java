package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-replenishment-operation-11", url = "http://localhost:8080")
public interface ClearanceServcervice {

    @RequestMapping(value = "/operation/merchandise/msku/batch", method = RequestMethod.GET)
    Result getProduct(@RequestParam(value = "ids") String[] ids);

}
