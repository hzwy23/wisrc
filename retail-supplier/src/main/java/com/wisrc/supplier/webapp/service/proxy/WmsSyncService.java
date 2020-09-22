package com.wisrc.supplier.webapp.service.proxy;

import com.wisrc.supplier.webapp.utils.Result;
import com.wisrc.supplier.webapp.vo.SupplierInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "supplier-retail-wms", url = "http://localhost:8080")
public interface WmsSyncService {
    @RequestMapping(value = "/wms/add/supplier", method = RequestMethod.POST, consumes = "application/json")
    Result addSupplierInfo(@RequestBody List<SupplierInfoVO> entityList);
}
