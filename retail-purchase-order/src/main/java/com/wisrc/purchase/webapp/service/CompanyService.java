package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "retail-purchase-basic-06", url = "http://localhost:8080")
public interface CompanyService {
    /**
     * 获取公司信息
     *
     * @return
     */
    @GetMapping("/basic/companyinfo/basic")
    Result getCompanyInfo();
}
