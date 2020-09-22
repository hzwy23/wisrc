package com.wisrc.sys.webapp.controller;

import com.wisrc.sys.webapp.vo.account.AccountVO;
import com.wisrc.sys.webapp.vo.account.AddAccountVO;
import com.wisrc.sys.webapp.vo.account.SetAccountVO;
import com.wisrc.sys.webapp.vo.account.StatuVO;
import com.wisrc.sys.webapp.vo.role.RoleInfoVo;
import com.wisrc.sys.webapp.vo.user.AccountSaveVo;
import com.wisrc.sys.webapp.vo.user.AccountVo;
import com.wisrc.sys.webapp.vo.user.SecUserVo;
import com.wisrc.sys.webapp.entity.AccountEntity;
import com.wisrc.sys.webapp.service.AccountInfoService;
import com.wisrc.sys.webapp.service.AccountService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.Time;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sys/account")
@Api(tags = "账号管理")
public class SysAccountController {

    private final Logger logger = LoggerFactory.getLogger(SysAccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountInfoService accountInfoService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "新增账户")
    public Result add(@Valid AddAccountVO addAccountVO, BindingResult bindingResult,
                      @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            return new Result(423, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        String password = addAccountVO.getPassword();
        String confirmPassword = addAccountVO.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            return Result.failure(423, "两次输入密码不一致，请确认密码", null);
        }

        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(addAccountVO, accountEntity);
        accountEntity.setCreateUser(userId);
        accountEntity.setStatusCd(1);
        accountEntity.setCreateTime(Time.getCurrentTime());
        return accountInfoService.add(accountEntity);
    }


    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "编辑账户")
    public Result update(@RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId,
                         @Valid SetAccountVO setAccountVO,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        return accountInfoService.update(setAccountVO);
    }

    @RequestMapping(value = "/restrict", method = RequestMethod.PUT)
    @ApiOperation(value = "批量禁用启用账户")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result restrict(@RequestHeader("X-AUTH-ID") String loginUser, @Valid StatuVO statu, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        logger.info("登陆用户：" + loginUser);
        for (String userId : statu.getUserIdList()) {
            if (loginUser.equals(userId)) {
                return Result.failure(423, "当前用户不能禁用自己", loginUser);
            }
        }
        AccountEntity accountEntity = new AccountEntity();
        for (String UserId : statu.getUserIdList()) {
            accountEntity.setUserId(UserId);
            accountEntity.setStatusCd(statu.getStatusCd());
            accountInfoService.restrict(accountEntity);
        }
        return Result.success();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "模糊查找用户基础信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "账号id", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "userName", value = "账号名称", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "statusCd", value = "账号状态", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "organizeId", value = "部门id", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（限制最大值为50）", required = true, dataType = "int", paramType = "query")
    })
    public Result userInfo(@RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId,
                           @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,
                           @Valid AccountVO accountVO,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new Result(423, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        if (userId == null) {
            return Result.failure(423, "用户连接信息为空，请重新登录", accountVO);
        }
        return Result.success(accountInfoService.getUserInfo(pageNum, pageSize, accountVO));
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "ID查询账户信息")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result getEmployee(@RequestHeader("X-AUTH-ID") String loginUser, @PathVariable("userId") String userId) {
        return accountInfoService.getUserById(userId);
    }

//    @RequestMapping(method = RequestMethod.GET)
//    @ApiOperation(value = "查询账号信息", notes = "根据账号编码或名称分页查询账号信息")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(value = "账号编号/名称", name = "userFindKey", paramType = "query", dataType = "String", required = false),
//            @ApiImplicitParam(value = "页码", name = "currentPage", paramType = "query", dataType = "Integer", required = true, defaultValue = "1"),
//            @ApiImplicitParam(value = "页面显示数量", name = "pageSize", paramType = "query", dataType = "Integer", required = true, defaultValue = "10")
//    })
//    public Result userList(String userFindKey, Integer currentPage, Integer pageSize) {
//        return accountService.getUsers(userFindKey, currentPage, pageSize);
//    }

    @RequestMapping(value = "/{userId}/get", method = RequestMethod.GET)
    @ApiOperation(value = "获取账号内容", response = RoleInfoVo.class)
    @ApiImplicitParam(value = "账号ID", name = "userId", paramType = "path", dataType = "String", required = true)
    public Result getUser(@PathVariable("userId") String userId) {
        return accountService.getUserById(userId);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存账号信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "用户ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
    })
    public Result saveUser(@Valid @RequestBody AccountSaveVo userInfoVo, @RequestHeader("X-AUTH-ID") String accountId) {
        return accountService.saveUser(userInfoVo, accountId);
    }

    @RequestMapping(value = "/{userId}/edit", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑账号信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "账号Id", name = "userId", paramType = "path", dataType = "String", required = true),
            @ApiImplicitParam(value = "用户ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    })
    public Result editRole(@PathVariable("userId") String userId, @Valid @RequestBody AccountVo accountVo, @RequestHeader("X-AUTH-ID") String accountId) {
        return accountService.editUser(accountVo, userId, accountId);
    }

    @RequestMapping(value = "/{userId}/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除账号信息")
    @ApiImplicitParam(value = "账号Id", name = "userId", paramType = "path", dataType = "String", required = true)
    public Result deleteUser(@RequestHeader("X-AUTH-ID") String loginUserId, @PathVariable("userId") String userId) {
        if (loginUserId.equals(userId)) {
            return Result.failure(423, "当前用户不能将自己删除", loginUserId);
        }
        return accountService.deleteUser(userId);
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ApiOperation(value = "检查账号是否已被使用")
    @ApiImplicitParam(value = "账号Id", name = "userId", paramType = "query", dataType = "String", required = false)
    public Result checkUser(String userId) {
        return accountService.checkUser(userId);
    }

    @RequestMapping(value = "/{userId}/password/change", method = RequestMethod.POST)
    @ApiOperation(value = "重置密码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "账号Id", name = "userId", paramType = "path", dataType = "String", required = true),
            @ApiImplicitParam(value = "密码", name = "secUserVo", paramType = "body", dataType = "SecUserVo", required = true)
    })
    public Result changePassword(@PathVariable("userId") String userId, @RequestBody SecUserVo secUserVo) {
        return accountService.changePassword(userId, secUserVo);
    }
}
