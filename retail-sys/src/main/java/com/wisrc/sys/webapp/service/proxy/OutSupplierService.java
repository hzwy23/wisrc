package com.wisrc.sys.webapp.service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-supplier", url = "http://localhost:8080")
public interface OutSupplierService {

    //模糊获取供应商信息列表
    @RequestMapping(value = "/supplier/", method = RequestMethod.GET)
    Object getSupplierFuzzy(@RequestParam("supplierId") String supplierId, @RequestParam("supplierName") String supplierName, @RequestParam("pageSize") String pageSize, @RequestParam("currentPage") String currentPage);

}


