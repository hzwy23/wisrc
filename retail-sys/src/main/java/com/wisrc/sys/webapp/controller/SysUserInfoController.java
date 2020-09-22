package com.wisrc.sys.webapp.controller;


import com.wisrc.sys.webapp.service.UserInfoService;
import com.wisrc.sys.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@Api(tags = "账号管理")
@RequestMapping(value = "/sys")
@Slf4j
public class SysUserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "获取当前登陆系统的用户详细信息", notes = "查询当前登陆系统的用户信息，用户登陆系统后，在cookies中会保存一串加密字符串，里边记录了用户的账号信息," +
            "当用户登陆系统后，用户请求这个API时，会将cookies中的加密字符串发送到内部服务网关，内部服务网关会解析这个加密字符串，然后读取出用户账号," +
            "内部网关接着会转发请求到系统管理模块，并且在请求头部添加X-AUTH-ID这个字段，字段值是用户的账号.<br/>" +
            "使用说明：<br/>" +
            "测试接口时，默认设置了X-AUTH-ID值为admin")
    @ApiImplicitParam(name = "X-AUTh-ID", value = "用户ID，在接口调用时，这个值会自动填充，不需要在调用时赋值", readOnly = true, dataType = "String", paramType = "header", defaultValue = "admin")
    @RequestMapping(value = "/user/current", method = RequestMethod.GET)
    public Result getUserDetails(@RequestHeader(value = "X-AUTH-ID") @ApiIgnore String userId) {
        return userInfoService.getUserDetails(userId);
    }

    @ApiImplicitParam()
    @RequestMapping(value = "/user/current/classify/manage", method = RequestMethod.GET)
    public Result getClassifyUser(@RequestHeader(value = "X-AUTH-ID") @ApiIgnore String userId) {
        // TODO 获取类目主管信息，首先获取当前用户的岗位，然后获取当前岗位的上级刚问，最后获取拥有这个岗位的用户，把这个用户当成类目主管
        return Result.success("待开发");
    }

    @RequestMapping(value = "/user/employee/batch", method = RequestMethod.GET)
    public Result getEmployeeName(String[] useridList) {
        return userInfoService.getUserEmployeeRelation(useridList);
    }

    @ApiOperation(value = "修改自己的名字", notes = "用户只有在登录之后，才能修改自己的账号昵称")
    @RequestMapping(value = "/user/current/name", method = RequestMethod.PUT)
    public Result updateUserName(@RequestHeader(value = "X-AUTH-ID") String userId,
                                 @RequestParam("userName") String userName) {
        userInfoService.updateName(userId, userName);
        return Result.success();
    }

    @ApiOperation(value = "修改用户预留手机", notes = "用户只有在登录之后，才能修改自己的手机号")
    @RequestMapping(value = "/user/current/phone", method = RequestMethod.PUT)
    public Result updateUserPhone(@RequestHeader(value = "X-AUTH-ID") String userId,
                                  @RequestParam("phoneNumber") String phoneNumber) {
        userInfoService.updatePhone(userId, phoneNumber);
        return Result.success();
    }

    @ApiOperation(value = "修改用户预留QQ号", notes = "用户只有在登录之后，才能修改自己的QQ号")
    @RequestMapping(value = "/user/current/qq", method = RequestMethod.PUT)
    public Result updateUserQQ(@RequestHeader(value = "X-AUTH-ID") String userId,
                               @RequestParam("qq") String qq) {
        userInfoService.updateQQ(userId, qq);
        return Result.success();
    }

    @ApiOperation(value = "修改用户预留微信号", notes = "用户只有在登录之后，才能修改自己的微信号")
    @RequestMapping(value = "/user/current/weixin", method = RequestMethod.PUT)
    public Result updateUserWeixin(@RequestHeader(value = "X-AUTH-ID") String userId,
                                   @RequestParam("weixin") String weixin) {
        userInfoService.updateWeixin(userId, weixin);
        return Result.success();
    }

    @ApiOperation(value = "修改用户预留微信号", notes = "用户只有在登录之后，才能修改自己的微信号")
    @RequestMapping(value = "/user/current/email", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Result updateUserEmail(@RequestHeader(value = "X-AUTH-ID") String userId,
                                  @RequestParam(name = "email") String email) {

        userInfoService.updateEmail(userId, email);
        return Result.success();
    }

    @ApiOperation(value = "修改用户预留座机号", notes = "用户只有在登录之后，才能修改自己的座机号")
    @RequestMapping(value = "/user/current/telephone", method = RequestMethod.PUT)
    public Result updateUserTelephone(@RequestHeader(value = "X-AUTH-ID") String userId,
                                      @RequestParam("telephone") String telephone) {
        userInfoService.updateTelephone(userId, telephone);
        return Result.success();
    }
}
