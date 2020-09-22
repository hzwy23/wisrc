package com.wisrc.quality.webapp.service;


import com.wisrc.quality.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "retail-quality-supplier-10", url = "http://localhost:8080")
public interface SupplierOutsideService {
    // 关联供应商信息
    @RequestMapping(value = "/supplier/common/suppliers", method = RequestMethod.GET)
    Result getSupplierBatch(@RequestParam("supplierId") String supplierId) throws Exception;

    // 根据名字查询供应商信息
    @RequestMapping(value = "/supplier/", method = RequestMethod.GET)
    Map getSupplierList(@RequestParam("supplierName") String supplierName, @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) throws Exception;

    // 根据选择供应商获取详细信息
    @RequestMapping(value = "/supplier/info", method = RequestMethod.GET)
    Map getSupplierInfo(@RequestParam("supplierId") String supplierId) throws Exception;
}
