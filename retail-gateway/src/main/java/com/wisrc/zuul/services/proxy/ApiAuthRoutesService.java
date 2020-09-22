package com.wisrc.zuul.services.proxy;


import com.wisrc.zuul.vo.ResultBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(value = "system", url = "http://localhost:8080")
public interface ApiAuthRoutesService {

    /**
     * 根据用户查询用户所有的角色信息
     */
    @RequestMapping(value = "/system/auth/role", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Map getApiAuthRoutes(@RequestParam(value = "username") String username);


    @RequestMapping(value = "/system/user/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    ResultBody register(@RequestParam(value = "username") String username,
                        @RequestParam(value = "mobilePhone") String mobilePhone);
}
