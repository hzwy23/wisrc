package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-purchase-sys-05", url = "http://localhost:8080")
public interface EmployeeOutsideService {
    // 批量获取人员中文名
    @RequestMapping(value = "/sys/employee/batch", method = RequestMethod.GET)
    Result getEmployeeNameBatch(@RequestParam("batchEmployeeId") String[] emps) throws Exception;

    // 人员选择框弹窗
    @RequestMapping(value = "/sys/employee", method = RequestMethod.GET)
    Result getEmployeeSelector(@RequestParam("employeeName") String employeeName, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestHeader("X-AUTH-ID") String userId) throws Exception;

    // 批量获取账号信息
    @RequestMapping(value = "/sys/user/employee/batch", method = RequestMethod.GET)
    Result getUserBatch(@RequestParam("useridList") List<String> useridList) throws Exception;
}
