package com.wisrc.sales.webapp.service.externalService;

import com.wisrc.sales.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-sales-sys-02", url = "http://localhost:8080")
public interface SysManageService {
    @RequestMapping(value = "/sys/user/current", method = RequestMethod.GET)
    Result getEmployeeId();

    @RequestMapping(value = "/sys/employee/batch", method = RequestMethod.GET)
    Result batchEmployee(@RequestParam(value = "batchEmployeeId") String[] batchEmployeeId);

    /**
     * 获取类目主管
     *
     * @param employeeIdList
     * @return
     */
    @RequestMapping(value = "/sys/employee/structure", method = RequestMethod.GET)
    Result getStructuresByEmployeeIds(@RequestParam(value = "employeeIdList") String[] employeeIdList);

    /**
     * 批量获取账号相关信息
     *
     * @param userIdList
     * @return
     */
    @RequestMapping(value = "/sys/user/employee/batch", method = RequestMethod.GET)
    Result getUsersByEmployeeIds(@RequestParam(value = "useridList") String[] userIdList); //请求参数这里的确是useridList


    @RequestMapping(value = "/sys/commodity/privilege", method = RequestMethod.POST)
    Result getAccessMsku(@RequestHeader("X-AUTH-ID") String userId);
}
