package com.wisrc.order.webapp.service.externalService;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-order-sys", url = "http://localhost:8080")
public interface SysManageService {
    @RequestMapping(value = "/sys/user/current", method = RequestMethod.GET)
    Result getEmployeeId();

    @RequestMapping(value = "/sys/employee/batch", method = RequestMethod.POST, consumes = "application/json")
    Result batchEmployee(@RequestBody String employeeIds);
}
