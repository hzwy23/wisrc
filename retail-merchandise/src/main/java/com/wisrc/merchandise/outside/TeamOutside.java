package com.wisrc.merchandise.outside;

import com.wisrc.merchandise.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "retail-merchandise-sys", url = "http://localhost:8080")
public interface TeamOutside {

    @RequestMapping(value = "/sys/auth/dept/operation", method = RequestMethod.GET)
    Result findAllOperationDept();
}