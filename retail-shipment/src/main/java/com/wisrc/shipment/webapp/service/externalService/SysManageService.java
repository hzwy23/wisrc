package com.wisrc.shipment.webapp.service.externalService;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "retail-shipment-sys", url = "http://localhost:8080")
public interface SysManageService {
    @RequestMapping(value = "/sys/user/current", method = RequestMethod.GET)
    Result getEmployeeId();

    @RequestMapping(value = "/sys/employee/batch", method = RequestMethod.POST, consumes = "application/json")
    Result batchEmployee(@RequestBody String employeeIds);

    @RequestMapping(value = "/sys/account/{userId}", method = RequestMethod.GET)
    Result getEmployeeByUserId(@PathVariable("userId") String userId, @RequestHeader("X-AUTH-ID") String authId);


    @RequestMapping(value = "/sys/user/employee/batch", method = RequestMethod.GET)
    Result getEmployeeBatch(@RequestParam(value = "useridList") String[] useridList);
}
