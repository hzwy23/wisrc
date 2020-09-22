package com.wisrc.sys.webapp.controller;

import com.wisrc.sys.webapp.vo.authInfo.AuthInfoStatuVO;
import com.wisrc.sys.webapp.vo.authInfo.AuthInfoVO;
import com.wisrc.sys.webapp.vo.authInfo.SetAuthInfoVO;
import com.wisrc.sys.webapp.entity.SysPrivilegesInfoEntity;
import com.wisrc.sys.webapp.service.AuthInfoInfoService;
import com.wisrc.sys.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sys")
@Api(tags = "数据权限信息")
public class AuthInfoInfoController {

    private final Logger logger = LoggerFactory.getLogger(AuthInfoInfoController.class);

    @Autowired
    private AuthInfoInfoService authInfoInfoService;

    @RequestMapping(value = "/authInfo", method = RequestMethod.GET)
    @ApiOperation(value = "模糊查找数据权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "authId", value = "数据权限编码", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "authName", value = "数据权限名称", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "privilegeTypeAttr", value = "权限类型(1:MSKU权限，2：供应商权限)", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（限制最大值为50）", required = true, dataType = "int", paramType = "query")
    })
    public Result authInfo(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize,
                           @Valid AuthInfoVO authInfoVO, BindingResult bindingResult,
                           @RequestHeader("X-AUTH-ID") String userId) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        return authInfoInfoService.getAuthInfo(pageNum, pageSize, authInfoVO);
    }

    @RequestMapping(value = "/authInfo", method = RequestMethod.POST)
    @ApiOperation(value = "新增数据权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authName", value = "数据权限名称", paramType = "query", dataType = "String", required = true),
    })
    public Result add(@Valid AuthInfoVO authInfoVO, BindingResult bindingResult,
                      @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        authInfoVO.setCreateUser(userId);
        if (bindingResult.hasErrors()) {
            return Result.failure(423, bindingResult.getAllErrors().get(0).getDefaultMessage(), authInfoVO);
        }
        return authInfoInfoService.add(authInfoVO);
    }

    @RequestMapping(value = "/authInfo", method = RequestMethod.PUT)
    @ApiOperation(value = "更新数据权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authId", value = "数据权限编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "authName", value = "数据权限名称", paramType = "query", dataType = "String", required = true),
    })
    public Result update(@Valid SetAuthInfoVO setAuthInfoVO, BindingResult bindingResult,
                         @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), setAuthInfoVO);
        }
        return authInfoInfoService.update(setAuthInfoVO, userId);
    }

    @RequestMapping(value = "/authInfo/{authId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除数据权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "authId", value = "数据权限编码", paramType = "path", dataType = "String", required = true),
    })
    public Result delete(@PathVariable("authId") String authId, @RequestHeader("X-AUTH-ID") String userId) {
        return authInfoInfoService.delete(authId);
    }

    @RequestMapping(value = "/authInfo/batch", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除数据权限")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "authIds", value = "数据权限编码数组,(使用逗号分隔)", paramType = "query", required = true)
    })
    public Result delete(@RequestHeader("X-AUTH-ID") String loginUser, String[] authIds) {
        for (String authId : authIds) {
            authInfoInfoService.delete(authId);
        }
        return Result.success();
    }

    @RequestMapping(value = "/authInfo/restrict", method = RequestMethod.PUT)
    @ApiOperation(value = "批量禁用数据权限")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "authId", value = "数据权限编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "statusCd", value = "状态", paramType = "query", dataType = "int", required = true)
    })
    public Result restrict(@RequestHeader("X-AUTH-ID") String loginUser, @Valid AuthInfoStatuVO authInfoStatu, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        SysPrivilegesInfoEntity entity = new SysPrivilegesInfoEntity();
        for (String privilegeCd : authInfoStatu.getAuthIdList()) {
            entity.setPrivilegeCd(privilegeCd);
            entity.setStatusCd(authInfoStatu.getStatusCd());
            authInfoInfoService.restrict(entity);
        }
        return Result.success();
    }

    @RequestMapping(value = "/authInfo/{authId}", method = RequestMethod.GET)
    @ApiOperation(value = "ID查询数据权限信息")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result getPrivilegesInfo(@PathVariable("authId") String authId, @RequestHeader("X-AUTH-ID") String userId) {
        return authInfoInfoService.getPrivilegesById(authId);
    }

    @RequestMapping(value = "/authInfo/findAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有数据权限信息")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result findAll(@RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId,
                          @RequestParam(value = "privilegeTypeAttr", required = false) String privilegeTypeAttr) {
        return authInfoInfoService.findAll(privilegeTypeAttr);
    }

    @Deprecated
    @RequestMapping(value = "/authInfo/privilegeCd/generator", method = RequestMethod.GET)
    @ApiOperation(value = "数据权限编号生成")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result getNewPrivilegeCd() {
        return Result.failure(423, "这个接口已经被注销", null);
    }

}
