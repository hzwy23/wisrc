package com.wisrc.supplier.webapp.controller;

import com.wisrc.supplier.webapp.controller.swagger.SupplierAccountModel;
import com.wisrc.supplier.webapp.service.SupplierCommonService;
import com.wisrc.supplier.webapp.service.proxy.SysService;
import com.wisrc.supplier.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "供应商外部接口")
@RequestMapping(value = "/supplier")
public class SupplierCommonController {

    @Autowired
    private SysService sysService;
    @Autowired
    private SupplierCommonService supplierCommonService;

    @ApiOperation(value = "根据编号获取供应商列表")
    @GetMapping("common/suppliers")
    @ApiImplicitParam(name = "supplierId", value = "供应商编号,分隔 空值全部", paramType = "query")
    Result getSuppliersById(String[] supplierId) {
        if (supplierId == null) {
            supplierId = new String[]{};
        }
        return Result.success(supplierCommonService.getSuppliersById(supplierId));
    }

    @ApiOperation(value = "根据条件模糊查询供应商帐号", response = SupplierAccountModel.class)
    @GetMapping("common/account")
    @ApiImplicitParams({@ApiImplicitParam(name = "supplierId", value = "供应商编号", paramType = "query"),
            @ApiImplicitParam(name = "auditStatus", value = "审核状态：1未通过 2审核通过", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "类型：0对内 1对外", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "bank", value = "银行名称", paramType = "query"),
            @ApiImplicitParam(name = "payee", value = "收款人", paramType = "query")})
    Result getSupplierAccount(String supplierId, Integer auditStatus, Integer type, String bank, String payee) {
        return supplierCommonService.getSupplierAccount(supplierId, auditStatus, type, bank, payee);
    }

    @GetMapping("common/user")
    Result getUserInfo(String userId) {
        return sysService.getUserInfo(userId);
    }

}
