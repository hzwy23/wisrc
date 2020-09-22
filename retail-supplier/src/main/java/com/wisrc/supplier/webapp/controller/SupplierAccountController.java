package com.wisrc.supplier.webapp.controller;

import com.wisrc.supplier.webapp.entity.SupplierAccount;
import com.wisrc.supplier.webapp.service.SupplierAccountService;
import com.wisrc.supplier.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api(tags = "供应商帐号")
@RequestMapping(value = "/supplier")
public class SupplierAccountController {

    @Autowired
    private SupplierAccountService supplierAccountService;

    @GetMapping("/account")
    @ApiOperation(value = "供应商帐号列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "supplierId", value = "供应商编号", paramType = "query"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query"),
            @ApiImplicitParam(name = "types", value = "类型 0未审核 1未通过 2通过", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataType = "int", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页条目数", paramType = "query", dataType = "int", required = true, defaultValue = "10")})
    Map<String, Object> getSupplierAccount(String supplierId, String supplierName, Integer types, Integer pageSize, Integer currentPage) {
        if (supplierId != null) {
            supplierId = "%" + supplierId + "%";
        }
        if (supplierName != null) {
            supplierName = "%" + supplierName + "%";
        }
        return supplierAccountService.getSupplierAccounts(supplierId, supplierName, types, pageSize, currentPage);
    }

    @PostMapping("/account")
    @ApiOperation(value = "供应商帐号添加")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    Result addSupplierAccount(@RequestHeader(value = "X-AUTH-ID") String userId, SupplierAccount account) {
        account.setMender(userId);
        if (account.getType() < 0 || account.getType() > 1) {
            return Result.failure(500, "帐号类型只能为0或1", null);
        }
        return Result.success(supplierAccountService.addSupplierAccount(account));
    }

    @PutMapping("/account")
    @ApiOperation(value = "供应商帐号更新")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    Result setSupplierAccount(@RequestHeader("X-AUTH-ID") String userId, SupplierAccount account) {
        account.setMender(userId);
        if (supplierAccountService.setSupplierAccount(account)) {
            return Result.success();
        }
        return Result.failure(500, "更新失败请检查id", null);
    }

    @PutMapping("/account/verify")
    @ApiOperation(value = "供应商帐号审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帐号主键", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "auditStatus", value = "状态：1未通过 2审核通过", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(name = "auditInfo", value = "审核内容", paramType = "query")})
    Result verifySupplierAccount(Integer id, Integer auditStatus, String auditInfo) {
        if (auditStatus < 1 || auditStatus > 2) {
            return Result.failure(500, "状态值只能为1或2", null);
        } else if (auditStatus == 1 && auditInfo == null) {
            return Result.failure(500, "审核未通过则备注必填", null);
        }
        return supplierAccountService.verifySupplierAccount(id, auditStatus, auditInfo);
    }

    @DeleteMapping("/account")
    @ApiOperation(value = "供应商帐号删除")
    @ApiImplicitParam(name = "id", value = "帐号主键", paramType = "query", required = true, dataType = "int")
    Map<String, Object> delSupplierAccount(Integer id) {
        return supplierAccountService.delSupplierAccount(id);
    }

}
