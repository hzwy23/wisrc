package com.wisrc.sys.webapp.service.proxy;

import com.wisrc.sys.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-warehouse", url = "http://localhost:8080")
public interface OutWarehouseService {

    @RequestMapping(value = "/warehouse/manage/info", method = RequestMethod.GET)
    Result getWarehouseFuzzy(@RequestParam("statusCd") int statusCd,
                             @RequestParam("typeCd") String typeCd,
                             @RequestParam("keyWord") String keyWord);

    @RequestMapping(value = "/warehouse/manage/info/idlist", method = RequestMethod.GET)
    Result getWarehouseDetails(@RequestParam("warehouseId") String[] warehouseId);

    @RequestMapping(value = "/warehouse/manage/type", method = RequestMethod.GET)
    Result getWarehouseType();
}


