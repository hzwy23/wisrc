package com.wisrc.purchase.webapp.service.externalService;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-purchase-supplier-18", url = "http://localhost:8080")
public interface SupplierService {
    /**
     * @param supplierId
     * @return
     */
    @RequestMapping(value = "/supplier/common/suppliers", method = RequestMethod.GET)
    Result getSupplierInfo(@RequestParam("supplierId") String supplierId);

    @RequestMapping(value = "/supplier/", method = RequestMethod.GET)
    Result getSupplierInfoByCond(@RequestParam("supplierName") String supplierName, @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize);
}
