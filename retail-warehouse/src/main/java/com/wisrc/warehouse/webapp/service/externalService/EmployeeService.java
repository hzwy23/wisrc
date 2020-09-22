package com.wisrc.warehouse.webapp.service.externalService;

import com.wisrc.warehouse.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-warehouse-sys-01", url = "http://localhost:8080")
public interface EmployeeService {
    /**
     * 批量查询员工
     *
     * @param batchEmployeeId
     * @return
     */
    @GetMapping("/sys/employee/batch")
    Result getEmployeesByIds(@RequestParam("batchEmployeeId") String[] batchEmployeeId);

    @GetMapping("/sys/user/employee/batch")
    Result getEmployee(@RequestParam("useridList") List<String> useridList);

    @GetMapping("/sys/user/current")
    Result getCurrentUser();

    @GetMapping("/sys/employee")
    Result getAllEmployee(@RequestHeader("X-AUTH-ID") String userId);
}
