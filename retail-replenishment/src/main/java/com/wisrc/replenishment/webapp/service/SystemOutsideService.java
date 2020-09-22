package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-replenishment-sys-24", url = "http://localhost:8080")
public interface SystemOutsideService {
    // 获取所有人员
    @RequestMapping(value = "/sys/employee", method = RequestMethod.GET)
    Result getEmployeePage(@RequestHeader("X-AUTH-ID") String userId, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) throws Exception;
}
