package com.wisrc.supplier.webapp.controller;

import com.wisrc.supplier.webapp.entity.Supplier;
import com.wisrc.supplier.webapp.service.SupplierManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "供应商列表")
@RequestMapping(value = "/supplier")
public class SupplierManageController {

    @Autowired
    private SupplierManageService supplierManageService;

    @GetMapping()
    @ApiOperation(value = "供应商列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "supplierId", value = "供应商编号", paramType = "query"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态：0失效 1正常", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataType = "int", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页条目数", paramType = "query", dataType = "int", required = true, defaultValue = "10")})
    Map<String, Object> getSuppliers(String supplierId, String supplierName, String startTime, String endTime,
                                     Integer status, Integer currentPage, Integer pageSize) {
        if (supplierId != null) {
            supplierId = "%" + supplierId + "%";
        }
        if (supplierName != null) {
            supplierName = "%" + supplierName + "%";
        }
        return supplierManageService.getSuppliers(supplierId, supplierName, startTime, endTime, status, currentPage, pageSize);
    }

    ;

    @GetMapping("/info")
    @ApiOperation(value = "供应商详情")
    @ApiImplicitParam(name = "supplierId", value = "供应商编号", paramType = "query", required = true)
    Map<String, Object> getSupplierInfo(String supplierId) {
        return supplierManageService.getSupplierInfo(supplierId);
    }


    @PostMapping()
    @ApiOperation(value = "供应商信息添加")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    Map<String, Object> addSupplierInfo(@RequestHeader(value = "X-AUTH-ID") String userId,
                                        @Valid @RequestBody Supplier supplier, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return map;
        }
        supplier.setMender(userId);
        return supplierManageService.addSupplierInfo(supplier);
    }

    @PutMapping()
    @ApiOperation(value = "供应商信息更新")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    Map<String, Object> setSupplierInfo(@RequestHeader(value = "X-AUTH-ID") String userId,
                                        @Valid @RequestBody Supplier supplier, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return map;
        }
        supplier.setMender(userId);
        return supplierManageService.setSupplierInfo(supplier);
    }

    @PutMapping("/status")
    @ApiOperation(value = "供应商状态更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态改为：0失效 1正常", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "supplierId", value = "供应商编号", paramType = "query", required = true)})
    public Map<String, Object> setSupplierStatus(Integer status, String[] supplierId) {
        if (status < 0 || status > 1) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", "状态只能为 0 或 1");
            return map;
        }
        return supplierManageService.setSupplierStatus(status, supplierId);
    }

}
