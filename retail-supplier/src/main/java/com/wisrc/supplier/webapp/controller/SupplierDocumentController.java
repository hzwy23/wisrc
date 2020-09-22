package com.wisrc.supplier.webapp.controller;

import com.wisrc.supplier.webapp.service.SupplierDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Api(tags = "供应商文件")
@RequestMapping(value = "/supplier")
public class SupplierDocumentController {

    @Autowired
    private SupplierDocumentService supplierDocumentService;

    @PutMapping("/annex")
    @ApiOperation(value = "供应商文件名更改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文件主键", paramType = "query", required = true),
            @ApiImplicitParam(name = "name", value = "文件名称", paramType = "query", required = true)})
    public Map<String, Object> setSupplierAnnex(String name, String id) {
        return supplierDocumentService.setSupplierAnnex(name, Integer.parseInt(id));
    }

    @DeleteMapping("/annex")
    @ApiOperation(value = "供应商文件删除")
    @ApiImplicitParam(name = "id", value = "文件主键", paramType = "query", required = true)
    public Map<String, Object> delSupplierAnnex(String id) {
        return supplierDocumentService.delSupplierAnnex(Integer.parseInt(id));
    }

}
