package com.wisrc.basic.service.proxy;

import com.wisrc.basic.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-sys", url = "http://localhost:8080")
public interface ExternalEmployeeService {
    // 批量员工id获取员工中文名
    @RequestMapping(value = "/sys/employee/batch", method = RequestMethod.GET)
    Result getEmployeeNameBatch(@RequestParam("batchEmployeeId") List<String> batchEmployeeId) throws Exception;

    @RequestMapping(value = "/sys/user/employee/batch", method = RequestMethod.GET)
    Result getUserBatch(@RequestParam("useridList") List<String> useridList) throws Exception;
}
