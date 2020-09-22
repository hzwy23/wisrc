package com.wisrc.sys.webapp.controller;


import com.wisrc.sys.webapp.service.PasswordService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import com.wisrc.sys.webapp.vo.ModifyPasswordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "密码管理")
@RequestMapping(value = "/sys")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @RequestMapping(value = "/password/modify", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public Result modifyPassword(@RequestBody ModifyPasswordVO modifyPasswordVO, HttpServletRequest request) {
        // 从请求头部获取用户连接信息
        String userId = request.getHeader("X-AUTH-ID");
        if (userId == null || userId.isEmpty()) {
            return Result.failure(ResultCode.UNKNOW_ERROR, "无法获取用户信息，请先登陆系统");
        }
        modifyPasswordVO.setUserId(userId);
        return passwordService.modifyPassword(modifyPasswordVO);
    }
}
