package com.wisrc.purchase.webapp.service.externalService;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-purchase-sys-09", url = "http://localhost:8080")
public interface EmployeeService {
    @RequestMapping(value = "/sys/employee/batch", method = RequestMethod.GET)
    Result getEmployee(@RequestParam(value = "batchEmployeeId") String[] batchEmployeeId);

    // 批量获取人员中文名
    @RequestMapping(value = "/sys/employee/batch", method = RequestMethod.GET)
    Result getEmployeeNameBatch(@RequestParam("batchEmployeeId") List<String> batchEmployeeId) throws Exception;
}
