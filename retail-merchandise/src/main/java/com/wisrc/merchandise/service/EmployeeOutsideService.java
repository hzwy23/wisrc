package com.wisrc.merchandise.service;

import com.wisrc.merchandise.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-merchandise-sys-001", url = "http://localhost:8080")
public interface EmployeeOutsideService {
    // 批量获取人员中文名
    @RequestMapping(value = "/sys/employee/batch", method = RequestMethod.GET)
    Result getEmployeeNameBatch(@RequestParam("batchEmployeeId") List<String> batchEmployeeId);

    // 人员选择框弹窗
    @RequestMapping(value = "/sys/employee/operation", method = RequestMethod.GET)
    Result getEmployeeSelector(@RequestParam("deptCd") String deptCd);

    @RequestMapping(value = "/sys/employee/operation", method = RequestMethod.GET)
    Result getEmployeeAll(@RequestParam("employeeIdList") String[] employeeIdList);

    @RequestMapping(value = "/sys/commodity/privilege", method = RequestMethod.POST)
    Result getPrivilege(@RequestHeader("X-AUTH-ID") String userId);
}
