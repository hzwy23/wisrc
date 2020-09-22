package com.wisrc.product.webapp.service.proxy;

import com.wisrc.product.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-product-system", url = "http://localhost:8080")
public interface ProxySystemService {
    // 批量获取人员中文名
    @RequestMapping(value = "/sys/employee/batch", method = RequestMethod.GET)
    Result getEmployeeNameBatch(@RequestParam("batchEmployeeId") List<String> list) throws Exception;

    // 批量用户ID获取关联用户信息
    @RequestMapping(value = "/sys/user/employee/batch", method = RequestMethod.GET)
    Result getUserBatch(@RequestParam("useridList") List<String> useridList) throws Exception;
}
