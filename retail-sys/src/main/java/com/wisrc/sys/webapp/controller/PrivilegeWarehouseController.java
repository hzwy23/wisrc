package com.wisrc.sys.webapp.controller;

import com.wisrc.sys.webapp.vo.privilegeWarehouse.BatchPrivilegeWarehouseVO;
import com.wisrc.sys.webapp.entity.SysPrivilegeWarehouseEntity;
import com.wisrc.sys.webapp.service.PrivilegeWarehouseService;
import com.wisrc.sys.webapp.utils.Crypto;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import com.wisrc.sys.webapp.utils.Time;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "授权仓库信息")
@RequestMapping(value = "/sys")
public class PrivilegeWarehouseController {
    private final Logger logger = LoggerFactory.getLogger(PrivilegeWarehouseController.class);

    @Autowired
    private PrivilegeWarehouseService privilegeWarehouseService;

    @RequestMapping(value = "/auth/repository/batch", method = RequestMethod.POST)
    @ApiOperation(value = "批量仓库授权")
    public Result batchPrivilegeSupplier(@RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String loginId,
                                         @Valid BatchPrivilegeWarehouseVO vo,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(424, bindingResult.getFieldError().getDefaultMessage(), vo);
        }
        List<SysPrivilegeWarehouseEntity> list = new ArrayList<>();
        for (String repositoryId : vo.getRepositoryIdList()) {
            SysPrivilegeWarehouseEntity entity = new SysPrivilegeWarehouseEntity();
            entity.setUuid(Crypto.sha(repositoryId, vo.getAuthId()));
            entity.setCreateUser(loginId);
            entity.setCreateTime(Time.getCurrentTimestamp());
            entity.setPrivilegeCd(vo.getAuthId());
            entity.setWarehouseCd(repositoryId);
            list.add(entity);
        }
        return privilegeWarehouseService.insert(list);
    }

    @ApiOperation(value = "撤销仓库授权", notes = "撤销仓库授权")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "authId", value = "授权编码", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "repositoryId", value = "仓库商编码", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/auth/repository/{authId}/{repositoryId}", method = RequestMethod.DELETE)
    public Result deletePrivilegeSupplierId(@RequestHeader("X-AUTH-ID") String loginId, @PathVariable("authId") String authId, @PathVariable("repositoryId") String repositoryId) {
        if (authId == null || repositoryId.isEmpty()) {
            return Result.failure(ResultCode.UNKNOW_ERROR, "授权编码或者仓库编码为空，无法删除");
        }
        SysPrivilegeWarehouseEntity entity = new SysPrivilegeWarehouseEntity();
        entity.setPrivilegeCd(authId);
        entity.setWarehouseCd(repositoryId);
        return privilegeWarehouseService.deletePrivilegeWarehouse(entity);
    }


    @RequestMapping(value = "/auth/repository", method = RequestMethod.GET)
    @ApiOperation(value = "通过权限代码查找授权仓库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authId", value = "数据权限编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "type", value = "仓库状态", paramType = "query", dataType = "Integer", required = false),
            @ApiImplicitParam(name = "keyWord", value = "仓库关键字", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", required = true, dataType = "int", paramType = "query")
    })
    public Result getPrivilegeShop(@RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String loginId,
                                   @RequestParam("pageNum") int pageNum,
                                   @RequestParam("pageSize") int pageSize,
                                   @RequestParam("authId") String authId,
                                   @RequestParam(value = "type", required = false) String type,
                                   @RequestParam(value = "keyWord", required = false) String keyWord) {
        return Result.success(privilegeWarehouseService.getPrivilegeWarehouse(pageNum, pageSize, authId, type, keyWord));
    }

    @RequestMapping(value = "/auth/repository/unauth", method = RequestMethod.GET)
    @ApiOperation(value = "通过权限代码查找未授权仓库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authId", value = "数据权限编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "type", value = "仓库状态", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "keyWord", value = "仓库关键字", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", required = true, dataType = "int", paramType = "query")
    })
    public Result getPrivilegeShopUnauth(@RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String loginId,
                                         @RequestParam("pageNum") int pageNum,
                                         @RequestParam("pageSize") int pageSize,
                                         @RequestParam("authId") String authId,
                                         @RequestParam(value = "type", required = false) String type,
                                         @RequestParam(value = "keyWord", required = false) String keyWord) {
        return Result.success(privilegeWarehouseService.getPrivilegeWarehouseUnauth(pageNum, pageSize, authId, type, keyWord));
    }
}
