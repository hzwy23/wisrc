package com.wisrc.order.webapp.service.externalService;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-order-rules", url = "http://localhost:8080")
public interface RuleService {
    /**
     * 获取所有异常规则
     *
     * @return
     */
    @RequestMapping(value = "/rules/abnormal", method = RequestMethod.GET)
    Result getExceptions();
}
