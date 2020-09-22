package com.wisrc.supplier.webapp.service.proxy;

import com.wisrc.supplier.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "supplier-retail-sys", url = "http://localhost:8080")
public interface SysService {

    @GetMapping("/sys/account/{userId}/get")
    Result getUserInfo(@RequestParam("userId") String userId);

}
