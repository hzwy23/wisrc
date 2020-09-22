package com.wisrc.sys.webapp.controller;

import com.wisrc.sys.webapp.vo.privilegeSupplier.BatchPrivilegeSupplierVO;
import com.wisrc.sys.webapp.entity.SysPrivilegeSupplierEntity;
import com.wisrc.sys.webapp.service.PrivilegeSupplierService;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "授权供应商信息")
@RequestMapping(value = "/sys")
public class PrivilegeSupplierController {
    private final Logger logger = LoggerFactory.getLogger(PrivilegeSupplierController.class);

    @Autowired
    private PrivilegeSupplierService privilegeSupplierService;

    @RequestMapping(value = "/auth/supplier/batch", method = RequestMethod.POST)
    @ApiOperation(value = "批量供应商授权")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result batchPrivilegeSupplier(@RequestHeader("X-AUTH-ID") String loginId, @Valid BatchPrivilegeSupplierVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        List<SysPrivilegeSupplierEntity> list = new ArrayList<>();
        for (String supplierId : vo.getSupplierIdList()) {
            SysPrivilegeSupplierEntity entity = new SysPrivilegeSupplierEntity();
            entity.setPrivilegeCd(vo.getAuthId());
            entity.setCreateUser(loginId);
            entity.setCreateTime(Time.getCurrentTimestamp());
            entity.setSupplierCd(supplierId);
            entity.setUuid(Crypto.sha(vo.getAuthId(), supplierId));
            list.add(entity);
        }
        return privilegeSupplierService.insert(list);
    }

    @ApiOperation(value = "撤销供应商授权", notes = "撤销供应商授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authId", value = "授权编码", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "supplierId", value = "供应商编码", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/auth/supplier/{authId}/{supplierId}", method = RequestMethod.DELETE)
    public Result deletePrivilegeSupplierId(@PathVariable("authId") String authId, @PathVariable("supplierId") String supplierId) {
        if (authId == null || supplierId.isEmpty()) {
            return Result.failure(ResultCode.UNKNOW_ERROR, "授权编码或者供应商编码为空，无法删除");
        }
        SysPrivilegeSupplierEntity entity = new SysPrivilegeSupplierEntity();
        entity.setPrivilegeCd(authId);
        entity.setSupplierCd(supplierId);
        return privilegeSupplierService.deletePrivilegeSupplierId(entity);
    }


    @RequestMapping(value = "/auth/supplier", method = RequestMethod.GET)
    @ApiOperation(value = "通过权限代码查找授权供应商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authId", value = "数据权限编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "supplierId", value = "供应商编码", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", required = true, dataType = "int", paramType = "query")
    })
    public Result getPrivilegeShop(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam("authId") String authId, String supplierId, String supplierName) {
        return privilegeSupplierService.getPrivilegeSupplier(pageNum, pageSize, authId, supplierId, supplierName);
    }

    @RequestMapping(value = "/auth/supplier/unauth", method = RequestMethod.GET)
    @ApiOperation(value = "通过权限代码查找未授权供应商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authId", value = "数据权限编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "supplierId", value = "供应商编码", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", required = true, dataType = "int", paramType = "query")
    })
    public Result getPrivilegeShopUnauth(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam("authId") String authId, String supplierId, String supplierName) {
        return privilegeSupplierService.getPrivilegeShopUnauth(pageNum, pageSize, authId, supplierId, supplierName);
    }
}
